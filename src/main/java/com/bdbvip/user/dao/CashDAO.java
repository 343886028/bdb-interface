package com.bdbvip.user.dao;

import java.io.Serializable;

import com.bdbvip.entity.TMemAccount;
import com.bdbvip.entity.TMemCashTradeRecord;
import com.bdbvip.entity.TMemDrawRecord;
import com.bdbvip.entity.TMemFrozenRecord;
import com.bdbvip.entity.TMemPass;
import com.bdbvip.entity.TMemPayRecord;
import com.bdbvip.pojo.CashTradeVo;
import com.bdbvip.utils.common.dao.IEntityDao;
import com.bdbvip.utils.common.dao.support.Page;

public interface CashDAO extends IEntityDao<Integer> {
	
	/**
	 * 
	  * @Title: savePayPwd
	  * @param  tPass 封装对象
	  * @return Serializable    
	  * @throws
	 */
	public Serializable savePayPwd(TMemPass tPass);
	
	/**
	 * 根据用户id获取用户密码信息
	  * @Title: findTMempassByUserId
	  * @param  userId
	  * @return TMemPass    
	  * @throws
	 */
	public TMemPass findTMempassByUserId(Integer userId);
	
	/**
	 * 修改支付密码
	  * @Title: updatePayPwd
	  * @param  tMemPass
	  * @return Serializable    
	  * @throws
	 */
	public Serializable updatePayPwd(TMemPass tMemPass);
	
	
	/**
	 *  根据用户userid获取账号余额
	  * @Title: findAccountByUserId
	  * @param  userId
	  * @return TMemAccount    
	  * @throws
	 */
	public TMemAccount findAccountByUserId(Integer userId);
	
	/**
	 * 修改用户余额
	  * @Title: updateAccountByUserId
	  * @param  userId
	  * @return Serializable    
	  * @throws
	 */
	public Serializable updateAccountByUserId(TMemAccount account);
	
	
	
	/**
	 * 添加用户现金账户
	  * @Title: updateAccountByUserId
	  * @param  userId
	  * @return Serializable    
	  * @throws
	 */
	public Serializable saveAccount(TMemAccount account);
		
	
	
	
	/**
	 * 添加充值记录
	  * @Title: savePayRecore
	  * @param  payRecord
	  * @return Serializable    
	  * @throws
	 */
	public Serializable savePayRecore(TMemPayRecord payRecord);
	
	/**
	 * 添加提现记录
	  * @Title: saveDrawRecore
	  * @param  drawRecord
	  * @return Serializable    
	  * @throws
	 */
	public Serializable saveDrawRecore(TMemDrawRecord drawRecord);
	
	/**
	 * 交易流水号
	  * @Title: saveCashTradeRecord
	  * @param  cashTradeRecord
	  * @return Serializable    
	  * @throws
	 */
	public Serializable saveCashTradeRecord(TMemCashTradeRecord cashTradeRecord);
	

	/**
	 * 根据用户获取用户交易记录
	  * @Title: findTradRecordList
	  * @param  page   页面分装对象
	  * @param  cTradeVo  查询条件封装
	  * @return Page   
	  * @throws
	 */
	
	public Page findTradRecordList(CashTradeVo cTradeVo,Page page);	
	
	/**
	 * 根据orderNO 获取交易流水
	 */
	public TMemCashTradeRecord findTradeByOrderNO(String orderno);
	
	
	/**
	 * 保存资金冻结交易记录
	 * @param frozenRecord
	 * @return
	 */
	public Serializable saveTMemFrozenRecord(TMemFrozenRecord frozenRecord);
	

}
