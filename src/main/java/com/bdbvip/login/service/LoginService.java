package com.bdbvip.login.service;

import java.io.Serializable;

import com.bdbvip.entity.TSysConfig;
import com.bdbvip.pojo.MembaseVo;
import com.bdbvip.utils.common.dao.support.Page;
import com.bdbvip.utils.common.exception.ServiceException;

public interface LoginService {
	
	/**
	 * 查询相关系统应用参数配置
	 * @param config
	 * @param page
	 * @return
	 * @throws ServiceException
	 */
	public Page findAllByStatus(TSysConfig config,Page page) throws ServiceException;

	/**
	 * 保存系统应用参数
	 * @param entity
	 * @return
	 * @throws ServiceException
	 */
	public Serializable saveEntity(Serializable entity) throws ServiceException;
	
	/**
	 * 找回登录密码前置校验，
	 * 校验通过，返回0#md5str(cardno+code+uname+signstr)
	 * 否则返回  xx#错误信息。
	 * @param vo
	 * @return
	 */
	public MembaseVo validLoginPwd(MembaseVo vo);
	
	/**
	 * 修改登录密码。
	 * @param vo
	 * @return
	 */
	public MembaseVo updateLoginPwd(MembaseVo vo);
	
	
}
