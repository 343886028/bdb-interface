package com.bdbvip.user.dao;

import java.io.Serializable;

import com.bdbvip.entity.TMemLog;
import com.bdbvip.utils.common.dao.support.Page;

public interface UserOpsLogDAO {
	
	/**
	 * 保存日志对象
	  * @Title: saveOpsLog
	  * @param   tLog 日志对象
	  * @return Serializable    
	  * @throws
	 */
	public Serializable saveOpsLog(TMemLog tLog);
	
	
	/**
	 * 根据用户 获取用户最后三次操作记录
	 * @param  Integer userid  用户id
	 * @param  String  logType日志类型
	 * @return Page  用户在该操作下最后三次操作
	 */
	public Page findLogByuseridAndLogType(Integer userid,String logType);

}
