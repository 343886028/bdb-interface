package com.bdbvip.user.service;

import com.bdbvip.entity.TMemLog;
import com.bdbvip.utils.common.exception.ServiceException;

public interface UserOpsLogService {
	
	
	/**
	 * 插入用户操作日志
	  * @Title: insertUserLog
	  * @param  tLog  日志对象
	  * @return void    
	  * @throws
	 */
	public void insertUserLog(TMemLog tLog) throws ServiceException;
	
	
	/**
	 * 用户的最后三次操作行为  如果连续三次操作失败 锁定账户
	  * @Title: validUserthreeOps
	  * @param  Integer userid  用户id
	  * @param  String  logType 模块类型
	  * @return boolean 
	  *         true   相同模块操作连续失败三次
	  *         false  
	  * @throws
	 */
	public boolean doUserthreeOps(Integer userid,String logType);

}
