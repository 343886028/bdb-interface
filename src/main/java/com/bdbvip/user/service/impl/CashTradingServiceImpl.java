package com.bdbvip.user.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdbvip.entity.TMemAccount;
import com.bdbvip.entity.TMemBankinfo;
import com.bdbvip.entity.TMemCashTradeRecord;
import com.bdbvip.entity.TMemDrawRecord;
import com.bdbvip.entity.TMemFrozenRecord;
import com.bdbvip.entity.TMemLog;
import com.bdbvip.entity.TMemPayRecord;
import com.bdbvip.pojo.CashAccountVo;
import com.bdbvip.pojo.CashTradeVo;
import com.bdbvip.user.dao.CashBankDAO;
import com.bdbvip.user.dao.CashDAO;
import com.bdbvip.user.dao.UserDAO;
import com.bdbvip.user.service.CashTradingService;
import com.bdbvip.user.service.UserOpsLogService;
import com.bdbvip.utils.Constants;
import com.bdbvip.utils.TradUtil;
import com.bdbvip.utils.common.exception.ServiceException;

@Service("cashTradingService")
public class CashTradingServiceImpl implements CashTradingService {
	
	private final static Logger logger=Logger.getLogger(CashServiceImpl.class);

	@Autowired
	CashDAO cashDao;
	
	@Autowired
	CashBankDAO cashBankDao;
	
	@Autowired
	UserOpsLogService userOpsLogService;
	
	@Autowired
	UserDAO userDAO;
	
	
	/**
	 * 保存用户交易流水
	 */
	public Map<String, Object> saveTMemCashTradeRecord(TMemCashTradeRecord cashTradeRecord) {
		String tradNo=TradUtil.getTradingNo(cashTradeRecord.getTradetype());
		cashTradeRecord.setTradeno(tradNo);		
		return null;
	}


	/**
	 * 保存充值记录
	 */
	public Map<String, Object> savePayRecords(TMemPayRecord tPayRecord) {
		Map<String, Object> result = new TreeMap<String, Object>();
		try {

			TMemAccount tAccount = cashDao.findAccountByUserId(tPayRecord.getUserid());
			tPayRecord.setPayorderno(TradUtil.getTradingNo(CashTradeVo.TRADING_TYPE_PY));

			// 首次充值 添加用户金融账号
			if (tAccount == null) {
				TMemAccount account = new TMemAccount();
				account.setUserid(tPayRecord.getUserid());
				account.setAccountflag(CashAccountVo.CASH_ACCOUNT_UNLOCK);
				account.setCashmoney(BigDecimal.valueOf(0));
				account.setFrozenmoney(BigDecimal.valueOf(0));
				// 保存充值账号
				cashDao.saveAccount(account);

				cashDao.savePayRecore(tPayRecord);
				result.put("status", "0");
				result.put("msg", "交易成功");
				return result;
			}

			cashDao.savePayRecore(tPayRecord);
			result.put("status", "0");
			result.put("msg", "交易成功");
			return result;

		} catch (Exception e) {
			result.put("status", "9999");
			result.put("msg", Constants.getParamterkey("common.syserror"));
			logger.error("[Cash.cashTrading.savePayRecords]====>" + Constants.getParamterkey("common.syserror"));
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 用户提现
	 */
	public Map<String, Object> saveDrawRecords(TMemDrawRecord tDrawRecord) {
		Map<String, Object> result = new TreeMap<String, Object>();
		try {
			
			TMemAccount tAccount = cashDao.findAccountByUserId(tDrawRecord.getUserid());
			//用户没有充值
			if (tAccount == null) {
				result.put("status", "");
				result.put("msg", Constants.getParamterkey("user.cash.bank.pay.norecharge"));
				return result;
			}
			
			//余额不足
			if (tAccount.getCashmoney().compareTo(tDrawRecord.getDrawmoney()) < 0) {
				result.put("status", "60041");
				result.put("msg", Constants.getParamterkey("user.cash.account.money.little"));
				return result;
			}
			// 金融账户是否锁定
			if (CashAccountVo.CASH_ACCOUNT_LOCK.equals(tAccount.getAccountflag())) {
				result.put("status", "600401");
				result.put("msg", Constants.getParamterkey("user.cah.account.lock.draw"));
				return result;
			}

			// 用户钱包模块操作是否连续错误三次
			if (userOpsLogService.doUserthreeOps(tAccount.getUserid(), CashAccountVo.CASH_ACCOUNT_LOGTYPE)) {
				result.put("status", "60042");
				result.put("msg", Constants.getParamterkey("user.cash.account.three.error"));
				return result;
			}
             
			TMemBankinfo tBankinfo=cashBankDao.findBankByUseridAndBankno(tDrawRecord.getUserid(), tDrawRecord.getBankno());
			//判断是否有该银行卡
			if (tBankinfo==null) {
				result.put("status", "60029");
				result.put("msg", Constants.getParamterkey("user.cash.bank.params.error"));
				return result;
			}
			
			//发送短信业务逻辑
			if ( CashTradeVo.TRADING_SENDFLAG_TRUE.equals(tDrawRecord.getSendflag())) {
				
				//执行发送短信业务逻辑
				
			}
			
			tDrawRecord.setBankcode(tBankinfo.getBankcode());
			tDrawRecord.setBankname(tBankinfo.getBankname());
			tDrawRecord.setRealname(tBankinfo.getRealname());
			//添加提现记录
			tDrawRecord.setDrawflag(CashTradeVo.TRADING_DRAW_PROCEING);
			cashDao.saveDrawRecore(tDrawRecord);
			
			//添加提现流水号
			this.mngDraw(tAccount, tDrawRecord);
			
			//添加提现日志
			TMemLog tlLog=new TMemLog();
			tlLog.setCreatetime(tDrawRecord.getCreatetime());
			tlLog.setUserid(tDrawRecord.getUserid());
			tlLog.setLogtype(CashAccountVo.CASH_ACCOUNT_DRAW);
			tlLog.setLogmsg(CashAccountVo.CASH_ACCOUNT_SUCCESS);
			userOpsLogService.insertUserLog(tlLog);
			
			result.put("status", "0");
			result.put("msg", "交易成功");
			return result;

		} catch (Exception e) {
			result.put("status", "9999");
			result.put("msg", Constants.getParamterkey("common.syserror"));
			logger.error("[Cash.cashTrading.saveDrawRecords]====>" + Constants.getParamterkey("common.syserror"));
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * 处理提现流水
	  * @Title: mngDraw
	  * @param  account  现金账户
	  * @param  drawRecord  提现记录
	  * @return void    
	  * @throws
	 */
	private void mngDraw(TMemAccount account,TMemDrawRecord drawRecord){
		BigDecimal frozMoney=BigDecimal.valueOf(0);
		TMemCashTradeRecord tradeRecord=new TMemCashTradeRecord();
		tradeRecord.setTradeno(TradUtil.getTradingNo(CashTradeVo.TRADING_TYPE_DW));
		tradeRecord.setUserid(drawRecord.getUserid());
		tradeRecord.setTradetype(CashTradeVo.TRADING_TYPE_DW_OUT);
		tradeRecord.setTradecashmoney(drawRecord.getDrawmoney());
		tradeRecord.setTradefrozenmoney(frozMoney);
		tradeRecord.setTradetotalmoney(drawRecord.getDrawmoney().add(frozMoney));
		tradeRecord.setCreatetime(new Date());
		
		
		/**
		 * 交易前现金和非现金余额  从账户余额中获取
		 */
		tradeRecord.setAccountcashmoney(account.getCashmoney());
		tradeRecord.setAccountfrzoenmoney(account.getFrozenmoney());
		
		/**
		 * 交易后现金和非现金 余额
		 */
		tradeRecord.setCashmoney(account.getCashmoney().subtract(drawRecord.getDrawmoney()));
		tradeRecord.setFrzoenmoney(account.getCashmoney().subtract(frozMoney));
		tradeRecord.setInorout(CashTradeVo.TRADING_OUT);
		
		/**
		 * 修改账户余额
		 */
		account.setCashmoney(account.getCashmoney().subtract(drawRecord.getDrawmoney()));
		account.setFrozenmoney(account.getCashmoney().subtract(frozMoney));
		cashDao.updateAccountByUserId(account);
		
		
		cashDao.saveCashTradeRecord(tradeRecord);
		
	}

	//提交保证金 处理
	public boolean doSubmitDeposit(BigDecimal money, Integer useridOut, Integer useridIn) {
		
		try {
			//保证金支出用户
			
			TMemAccount outAccount = cashDao.findAccountByUserId(useridOut);
			
			//保证金收入用户
			TMemAccount inAccount = cashDao.findAccountByUserId(useridIn);		
			
			
			Date date=new Date();
			//添加资金冻结记录
			TMemFrozenRecord frozenRecord=new TMemFrozenRecord();
			frozenRecord.setCashmoney(money);
			frozenRecord.setFrzoenmoney(new BigDecimal("0"));
			frozenRecord.setCreatetime(date);
			frozenRecord.setFrzoenorderno(TradUtil.getTradingNo(CashTradeVo.TRADING_TYPE_BD));
			frozenRecord.setInuserid(useridIn);
			frozenRecord.setOutuserid(useridOut);
			frozenRecord.setStatus("0");
			
			
			//添加交易流水
			TMemCashTradeRecord tradeRecord=new TMemCashTradeRecord();
			tradeRecord.setTradeno(TradUtil.getTradingNo(CashTradeVo.TRADING_TYPE_BD));
			tradeRecord.setUserid(useridOut);
			tradeRecord.setTradetype(CashTradeVo.TRADING_TYPE_BD_OUT);
			tradeRecord.setTradecashmoney(money);
			tradeRecord.setTradefrozenmoney(BigDecimal.valueOf(0));
			tradeRecord.setTradetotalmoney(money);
			tradeRecord.setCreatetime(date);
			
			
			/**
			 * 交易前现金和非现金余额  从账户余额中获取
			 */
			tradeRecord.setAccountcashmoney(outAccount.getCashmoney());
			tradeRecord.setAccountfrzoenmoney(outAccount.getFrozenmoney());
			
			
			//交易后用户现金余额
			BigDecimal outmoney=outAccount.getCashmoney().subtract(money);
			outAccount.setCashmoney(outmoney);
			
			//收入保证金
			BigDecimal inmoney=inAccount.getCashmoney().add(money);
			inAccount.setCashmoney(inmoney);
			
			//修改保证金支出账户信息
			cashDao.updateAccountByUserId(outAccount);
			
			//添加冻结记录
			cashDao.saveTMemFrozenRecord(frozenRecord);
			
			//修改保证金收入账户信息
			cashDao.updateAccountByUserId(inAccount);
			
			
			
			// 添加支出保证金用户 日志
			this.saveAuctionRegist(useridOut);

			// 添加收入保证金用户 日志
			this.saveAuctionRegist(useridIn);
			
			//交易后现金和非现金 余额
			tradeRecord.setCashmoney(outmoney);
			tradeRecord.setFrzoenmoney(outAccount.getFrozenmoney().subtract(new BigDecimal(0)));
			tradeRecord.setInorout(CashTradeVo.TRADING_OUT);
			tradeRecord.setFromuser(useridIn);
			cashDao.saveCashTradeRecord(tradeRecord);
			return true;
		} catch (Exception e) {
			logger.error("CashTradingService.doSubmitDeposit=========>异常！！！");
			e.printStackTrace();
		}
		return false;
	}
	
	//添加账户日志
	private void saveAuctionRegist(Integer userid) throws ServiceException{
		TMemLog auctionLog=new TMemLog();
		auctionLog.setCreatetime(new Date());
		auctionLog.setUserid(userid);
		auctionLog.setLogtype(CashAccountVo.CASH_ACCOUNT_BOUND);
		auctionLog.setLogmsg(CashAccountVo.CASH_ACCOUNT_SUCCESS);
		userOpsLogService.insertUserLog(auctionLog);
	}

	
	
}
