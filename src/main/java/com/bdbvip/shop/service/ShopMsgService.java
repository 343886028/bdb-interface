package com.bdbvip.shop.service;

import java.io.Serializable;

import com.bdbvip.entity.TMemMsg;
import com.bdbvip.entity.TOrdCompain;
import com.bdbvip.utils.common.exception.ServiceException;


public interface ShopMsgService {

	/**
	 * 站内消息发送
	 * @param msg
	 * @return
	 */
	public Serializable saveTMemMsg(TMemMsg msg) throws ServiceException;
	
	/**
	 * 投诉建议回复
	 * @param msg
	 * @param compain
	 * @throws ServiceException
	 */
	public void doComplainReply(TMemMsg msg,TOrdCompain compain) throws ServiceException;
}
