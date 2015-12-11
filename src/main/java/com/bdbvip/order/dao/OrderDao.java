package com.bdbvip.order.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.bdbvip.entity.TMemMsg;
import com.bdbvip.entity.TOrdBase;
import com.bdbvip.entity.TOrdCompain;
import com.bdbvip.entity.TOrdItem;
import com.bdbvip.utils.common.dao.IEntityDao;
import com.bdbvip.utils.common.dao.support.Page;

public interface OrderDao extends IEntityDao<Integer> {
	Page getOrderListByCondition(TOrdBase orderBase,int pageNO,int pageSize);
	Page getOrderItemListByOrderNO(String orderNO, int pageNO, int pageSize);
	int doOrderDelay(String orderNO, int days);
	int doOrderRecieve(String orderNO,String receiveflag);
	int doSendGoods(String orderno, String sendno, String sendname, BigDecimal sendmoney, String sendflag);
	int updateOrderPrice(String orderno, BigDecimal oldordermoney);
	int doCancelOrder(String orderno);
	Serializable insertComplain(TOrdCompain complain);
	TOrdBase getOrderByCode(String ordeno);
	Serializable doRemindSendGoods(TMemMsg newMsg);
	TOrdBase findOrderByOrderCodeAndUserID(Integer userid, String orderno);
	List<TOrdItem> getOrderItemListByOrderNO(String orderNO);
	
	TOrdCompain getCompainById(int compainid);
}
