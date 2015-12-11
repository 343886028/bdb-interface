package com.bdbvip.sys.service;

import java.util.Map;

import com.bdbvip.entity.TMemSm;
import com.bdbvip.entity.TSysPartner;
import com.bdbvip.pojo.MembaseVo;
import com.bdbvip.utils.common.dao.support.Page;
import com.bdbvip.utils.common.exception.ServiceException;

public interface SysService {

	public Map<String,TSysPartner> findAll() throws ServiceException;
	
	public Page findTMemSms(TMemSm sms,Page page)throws ServiceException;
	
	/**
	 * 发送短信前，校验，十分钟内，同一手机号最多只能发送三条短信。
	 * 同一天，同一手机号只能发送5条短信。
	 * @param sm
	 * @return
	 * @throws ServiceException
	 */
	public MembaseVo createTMemSm(MembaseVo  sm) throws ServiceException;
	
	/**
	 * 同一条短信的最后一条记录，重新发送。
	 * @param vo
	 * @return
	 * @throws ServiceException
	 */
	public MembaseVo create2thTMemSm(MembaseVo vo)throws ServiceException;
}
