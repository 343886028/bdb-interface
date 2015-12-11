package com.bdbvip.shop.dao.impl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.bdbvip.entity.TMemMsg;
import com.bdbvip.shop.dao.ShopMsgDAO;
import com.bdbvip.utils.common.dao.entity.HibernateEntityDao;

@Repository("shopMsgDAO")
public class ShopMsgDaoImpl extends HibernateEntityDao<Integer> implements ShopMsgDAO{

	public Serializable saveTMemMsg(TMemMsg msg) {
		return super.save(msg);
	}
}
