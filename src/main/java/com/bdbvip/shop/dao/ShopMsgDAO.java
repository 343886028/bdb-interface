package com.bdbvip.shop.dao;

import java.io.Serializable;

import com.bdbvip.entity.TMemMsg;
import com.bdbvip.utils.common.dao.IEntityDao;

public interface ShopMsgDAO extends IEntityDao<Integer>{

	Serializable saveTMemMsg(TMemMsg msg);
}
