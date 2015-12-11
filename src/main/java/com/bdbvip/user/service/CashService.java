package com.bdbvip.user.service;


import java.util.Map;

import com.bdbvip.entity.TMemCashTradeRecord;
import com.bdbvip.entity.TMemPass;
import com.bdbvip.pojo.CashTradeVo;
import com.bdbvip.utils.common.dao.support.Page;
import com.bdbvip.utils.common.exception.ServiceException;


/**
 * 用户的金融账户密码相关操作
 * 余额相关操作
  * @ClassName: CashService
  * @Description: TODO
  * @date 2015年12月1日 下午4:53:26
 */
public interface CashService {
	
	
	/**
	 * 用户是否初始化密码
	  * @Title: doPwdisExist
	  * @param  token
	  * @return Map<String, String>   初始化返回1  没有初始化返回0
	  * @throws ServiceException
	 */
	public Map<String, Object> doPwdisExist(String userId) throws ServiceException;
	
	
	/**
	 * 设置用户初始化密码
	  * @Title: createPayPwd
	  * @param  tMemPass  设置密码对象
	  * @return Map<String, String>    
	  * @throws ServiceException 
	 */
	public Map<String, Object> createPayPwd(TMemPass tMemPass) throws ServiceException;
	
	/**
	 * 
	  * @Title: updatePwd
	  * @param  oldPwd 原始密码
	  * @param  tMemPass 新密码对象
	  * @return Map<String, String>    成功返回1  失败返回0
	  * @throws ServiceException 
	 */
	public Map<String, Object> updatePwd(String oldPwd,TMemPass tMemPass) throws ServiceException;
	
	
	/**
	 * 
	  * @Title: dovalidPwd
	  * @param  tMemPass 验证密码参数对象
	  * @return Map<String, String>    成功返回1  失败返回0
	  * @throws ServiceException 
	 */
	public Map<String, Object> dovalidPwd(TMemPass tMemPass) throws ServiceException;
	
	/**
	 * 账户余额查询
	  * @Title: findAccountByUserId
	  * @param  userId  用户id
	  * @return Map<String,Object>      
	  * @throws  ServiceException 
	 */
	public Map<String, Object> findAccountByUserId(String userId) throws ServiceException;
	
	
	
	/**
	 * 查询交易记录
	  * @Title: findTradRecord
	  * @param @throws ServiceException 
	  * @return Map<String,String>    
	  * @throws
	 */
	public Map<String, Object> findTradRecord(CashTradeVo cTradeVo,Page page) throws ServiceException;
	
	/**
	 * 用户钱包是否已经锁定  
	  * @Title: validCashAccountByUserid
	  * @param  userid  用户id
	  * @return boolean    
	  *         true  锁定状态
	  *         false 非锁定状态
	  * @throws
	 */
	public boolean validCashAccountByUserid(Integer userid);
	
	public TMemCashTradeRecord findTradeByOrderNO(String orderno);

}
