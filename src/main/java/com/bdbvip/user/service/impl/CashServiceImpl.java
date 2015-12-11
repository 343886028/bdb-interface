package com.bdbvip.user.service.impl;




import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.bdbvip.entity.TMemAccount;
import com.bdbvip.entity.TMemCashTradeRecord;
import com.bdbvip.entity.TMemLog;
import com.bdbvip.entity.TMemPass;
import com.bdbvip.pojo.CashAccountVo;
import com.bdbvip.pojo.CashTradeVo;
import com.bdbvip.user.dao.CashDAO;
import com.bdbvip.user.service.CashService;
import com.bdbvip.user.service.UserOpsLogService;
import com.bdbvip.utils.Constants;
import com.bdbvip.utils.common.dao.support.Page;
import com.bdbvip.utils.common.exception.ServiceException;

@Service("cashService")
public class CashServiceImpl implements CashService {
	private final static Logger logger = Logger.getLogger(CashServiceImpl.class);	

	@Autowired
	CashDAO cashDao;
	
	@Autowired
	UserOpsLogService userOpsLogService;

	// 密码是否初始化
	public Map<String, Object> doPwdisExist(String userId) {
		Map<String, Object> result = new TreeMap<String, Object>();
		TMemPass tPass = null;
		try {
			// 判断userid是否合法
			if (userId.matches("\\d+")) {
				tPass = cashDao.findTMempassByUserId(Integer.parseInt(userId));
				// tpass 为空 用户没有初始化密码
				result.put("status", "0");
				result.put("msg", "交易成功");
				if (tPass != null) {
					result.put("recomplete", "1");
					return result;
				}
				result.put("recomplete", "0");
				return result;
			}
			result.put("status", "9009");
			result.put("msg", Constants.getParamterkey("common.param.tokenInvalid"));
			return result;

		} catch (Exception e) {
			result.put("status", Constants.getParamterkey("common.syserror"));
			logger.error("[Cash.Service.doPwdisExist]====>" + Constants.getParamterkey("common.syserror"));
			e.printStackTrace();
		}
		return result;
	}
	
	// 创建支付密码
	public Map<String, Object> createPayPwd(TMemPass tMemPass) throws ServiceException {
		Map<String, Object> result = new TreeMap<String, Object>();
		try {
			
			TMemPass tPass = cashDao.findTMempassByUserId(tMemPass.getUserid());
			
			if (tPass != null) {
				result.put("status", "60014");
				result.put("msg", Constants.getParamterkey("user.cash.pass.init"));
				return result;
			}
			
			//保存支付密码
			cashDao.savePayPwd(tMemPass);
			
			//添加操作日志
			TMemLog tLog=new TMemLog();
			tLog.setCreatetime(new Date());
			tLog.setLogtype(CashAccountVo.CASH_ACCOUNT_INIT);
			tLog.setUserid(tMemPass.getUserid());
			tLog.setLogmsg(CashAccountVo.CASH_ACCOUNT_SUCCESS);
			tLog.setCreatetime(new Date());
			userOpsLogService.insertUserLog(tLog);
			
			result.put("status", "0");
			result.put("msg", "交易成功");
			return result;

		} catch (Exception e) {
			result.put("status", "9999");
			result.put("msg", Constants.getParamterkey("common.syserror"));
			logger.info("[CashFace.Service.createPayPwd] ====>" + Constants.getParamterkey("common.syserror"));
			e.printStackTrace();
		}

		return result;
	}

	// 修改支付密码
	public Map<String, Object> updatePwd(String oldPwd, TMemPass tMemPass) throws ServiceException {
		Map<String, Object> result = new TreeMap<String, Object>();
		try {

			// 获取修改账户原始支付密码信息
			TMemPass t = cashDao.findTMempassByUserId(tMemPass.getUserid());

			if (t == null) {
				result.put("status", "9009");
				result.put("msg", Constants.getParamterkey("common.param.tokenInvalid"));
				return result;
			}

			// 获取金融账户信息
			TMemAccount tAccount = cashDao.findAccountByUserId(tMemPass.getUserid());

			// 金融账户是否锁定
			if (tAccount!=null&&CashAccountVo.CASH_ACCOUNT_LOCK.equals(tAccount.getAccountflag())) {
				result.put("status", "600112");
				result.put("msg", Constants.getParamterkey("user.cash.updatepwd.lock"));
				return result;
			}

			// 连续三次操作验证
			boolean flag = userOpsLogService.doUserthreeOps(tMemPass.getUserid(), CashAccountVo.CASH_ACCOUNT_LOGTYPE);
			if (flag) {
				result.put("status", "60042");
				result.put("msg", Constants.getParamterkey("user.cash.account.three.error"));
				return result;
			}

			// 添加操作日志
			TMemLog tLog = new TMemLog();
			tLog.setCreatetime(new Date());
			tLog.setLogtype(CashAccountVo.CASH_ACCOUNT_UPDATE);
			tLog.setUserid(tMemPass.getUserid());
			tLog.setCreatetime(new Date());

			// 判断旧的密码是否相等
			if (t.getPaypass().equals(oldPwd)) {

				t.setClientpass(tMemPass.getClientpass());
				t.setPaypass(tMemPass.getPaypass());
				t.setDrawpass(tMemPass.getDrawpass());

				// 修改支付密码
				cashDao.updatePayPwd(t);

				// 修改成功添加日志
				tLog.setLogmsg(CashAccountVo.CASH_ACCOUNT_SUCCESS);
				userOpsLogService.insertUserLog(tLog);

				result.put("status", "0");
				result.put("msg", "");
				return result;

			}
			// 修改失败添加日志
			tLog.setLogmsg(CashAccountVo.CASD_ACCOUNT_FAIL);
			userOpsLogService.insertUserLog(tLog);

			result.put("status", "60015");
			result.put("msg", Constants.getParamterkey("user.cash.oldpass.error"));
			return result;

		} catch (Exception e) {
			result.put("status", "9999");
			result.put("msg", Constants.getParamterkey("common.syserror"));
			logger.error("[Cash.Service.updatePwd]====>" + Constants.getParamterkey("common.syserror"));
			e.printStackTrace();
		}
		return result;
	}

	// 验证密码
	public Map<String, Object> dovalidPwd(TMemPass tMemPass) throws ServiceException {
		Map<String, Object> result = new TreeMap<String, Object>();
		TMemPass tPass = null;
		try {

			// 获取账户密码验证信息
			tPass = cashDao.findTMempassByUserId(tMemPass.getUserid());

			if (tPass == null) {
				result.put("status", "9009");
				result.put("msg", Constants.getParamterkey("common.param.tokenInvalid"));
				return result;
			}

			// 获取金融账户信息
			TMemAccount tAccount = cashDao.findAccountByUserId(tMemPass.getUserid());

			// 金融账户是否锁定
			if (CashAccountVo.CASH_ACCOUNT_LOCK.equals(tAccount.getAccountflag())) {
				result.put("status", "60040");
				result.put("msg", Constants.getParamterkey("user.cash.account.lock"));
				return result;
			}

			// 连续三次操作验证
			boolean flag = userOpsLogService.doUserthreeOps(tMemPass.getUserid(), CashAccountVo.CASH_ACCOUNT_LOGTYPE);
			if (flag) {
				result.put("status", "60042");
				result.put("msg", Constants.getParamterkey("user.cash.account.three.error"));
				return result;
			}

			// 添加操作日志
			TMemLog tLog = new TMemLog();
			tLog.setCreatetime(new Date());
			tLog.setLogtype(CashAccountVo.CASH_ACCOUNT_VAILD);
			tLog.setUserid(tMemPass.getUserid());
			tLog.setCreatetime(new Date());

			if (tPass.getPaypass().equals(tMemPass.getPaypass())) {

				// 插入成功操作日志
				tLog.setLogmsg(CashAccountVo.CASH_ACCOUNT_SUCCESS);
				userOpsLogService.insertUserLog(tLog);

				result.put("status", "0");
				result.put("msg", "");
				return result;
			}

			// 插入失败操作日志
			tLog.setLogmsg(CashAccountVo.CASD_ACCOUNT_FAIL);
			userOpsLogService.insertUserLog(tLog);

			result.put("status", "60012");
			result.put("msg", Constants.getParamterkey("user.cash.valid.pass.fail"));
			return result;

		} catch (Exception e) {
			result.put("status", "9999");
			result.put("msg", Constants.getParamterkey("common.syserror"));
			logger.error("[Cash.Service.validPwd]====>" + Constants.getParamterkey("common.syserror"));
			e.printStackTrace();
		}
		return result;
	}
	
	// 余额查询方法
	public Map<String, Object> findAccountByUserId(String userId) throws ServiceException {
		Map<String, Object> result = new TreeMap<String, Object>();
		TMemAccount tAccount = null;
		try {
			if (!"".equals(userId) && userId.matches("\\d+")) {
				tAccount = cashDao.findAccountByUserId(Integer.parseInt(userId));

				if (tAccount == null) {
					result.put("cashmoney", "0");
					result.put("frozemoney", "0");
					result.put("status", "0");
					result.put("msg", Constants.getParamterkey("user.cash.bank.pay.norecharge"));
					return result;
				}
				
				// 金融账户是否锁定
				if (CashAccountVo.CASH_ACCOUNT_LOCK.equals(tAccount.getAccountflag())) {
					result.put("status", "60040");
					result.put("msg", Constants.getParamterkey("user.cash.account.lock"));
					return result;
				}

				// 连续三次操作验证
				boolean flag = userOpsLogService.doUserthreeOps(tAccount.getUserid(), CashAccountVo.CASH_ACCOUNT_LOGTYPE);
				if (flag) {
					result.put("status", "60042");
					result.put("msg", Constants.getParamterkey("user.cash.account.three.error"));
					return result;
				}
				
				
				result.put("cashmoney", tAccount.getCashmoney().toString());
				result.put("frozemoney", tAccount.getFrozenmoney().toString());
				result.put("status", "0");
				result.put("msg", "交易成功");
				return result;
			}
			result.put("status", "9009");
			result.put("msg", Constants.getParamterkey("common.param.tokenInvalid"));
			logger.error("[Cash.Service.findAccountByUserId]====>" + Constants.getParamterkey("common.param.tokenInvalid"));
			return result;

		} catch (Exception e) {
			result.put("msg", "9999");
			result.put("status", Constants.getParamterkey("common.syserror"));
			logger.error("[Cash.Service.findAccountByUserId]====>" + Constants.getParamterkey("common.syserror"));
			e.printStackTrace();
		}
		return result;
	}

	//交易历史查询
	public Map<String, Object> findTradRecord(CashTradeVo cTradeVo, Page page) throws ServiceException {
		Map<String, Object> result = new TreeMap<String, Object>();
		try {
			Page p = cashDao.findTradRecordList(cTradeVo, page);
			result.put("total", String.valueOf(p.getTotalCount()));
			result.put("page",  String.valueOf(p.getCurrentPageNo()));
			result.put("pagesize", String.valueOf(p.getPageSize() ));
			//list 格式化为json
			result.put("list", JSON.toJSONString(p.getResult()));
			result.put("status", "0");
			result.put("msg", "");
			return result;
		} catch (Exception e) {
			result.put("msg", "9999");
			result.put("status", Constants.getParamterkey("common.syserror"));
			logger.error("[Cash.Service.findTradRecord]====>" + Constants.getParamterkey("common.syserror"));
			e.printStackTrace();
		}
		return result;
	}

	// 钱包是否锁定
	public boolean validCashAccountByUserid(Integer userid) {

		TMemAccount tAccount = cashDao.findAccountByUserId(userid);
		if (null == tAccount) {
			return false;
		} else if (CashAccountVo.CASH_ACCOUNT_LOCK.equals(tAccount.getAccountflag())) {
			return true;
		}
		return false;
	}


	public TMemCashTradeRecord findTradeByOrderNO(String orderno) {
		return cashDao.findTradeByOrderNO(orderno);
	}
}
