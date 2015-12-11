package com.bdbvip.shop.service.impl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdbvip.entity.TMemMsg;
import com.bdbvip.entity.TOrdCompain;
import com.bdbvip.shop.dao.ShopMsgDAO;
import com.bdbvip.shop.service.ShopMsgService;
import com.bdbvip.utils.common.exception.ServiceException;


@Service("shopMsgService")
public class ShopMsgServiceImpl implements ShopMsgService {

	@Autowired
	ShopMsgDAO shopMsgDAO;
	
	public Serializable saveTMemMsg(TMemMsg msg) throws ServiceException {
		return shopMsgDAO.saveTMemMsg(msg);
	}

	@Override
	public void doComplainReply(TMemMsg msg, TOrdCompain compain)
			throws ServiceException {
		shopMsgDAO.save(msg);
		shopMsgDAO.save(compain);
	}
}
