package com.bdbvip.order.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdbvip.entity.TMemMsg;
import com.bdbvip.entity.TOrdBase;
import com.bdbvip.entity.TOrdCompain;
import com.bdbvip.entity.TOrdItem;
import com.bdbvip.order.dao.OrderDao;
import com.bdbvip.order.service.OrderService;
import com.bdbvip.utils.common.dao.support.Page;
@Service("orderService")
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	OrderDao orderDao;
	public Page getOrderListByCondition(TOrdBase orderBase,int pageNO,int pageSize) {
		return orderDao.getOrderListByCondition(orderBase, pageNO, pageSize);
	}
	public Page getOrderItemListByOrderNO(String orderNO, int pageNO, int pageSize) {
		return orderDao.getOrderItemListByOrderNO(orderNO, pageNO, pageSize);
	}
	public int doOrderDelay(String orderNO, int days) {
		return orderDao.doOrderDelay(orderNO,days);
	}
	public int doOrderRecieve(String orderNO,String receiveFlag) {
		return orderDao.doOrderRecieve(orderNO,receiveFlag);
	}
	public int doSendGoods(String orderno, String sendno, String sendname, BigDecimal sendmoney, String sendflag) {
		return orderDao.doSendGoods(orderno, sendno, sendname, sendmoney, sendflag);
	}
	@Override
	public List<TOrdItem> getOrderItemListByOrderNO(String orderNO) {
		return orderDao.getOrderItemListByOrderNO(orderNO);
	}
	public int updateOrderPrice(String orderno, String oldordermoney) {
		return orderDao.updateOrderPrice(orderno, new BigDecimal(oldordermoney));
	}
	public TOrdBase findOrderByOrderCodeAndUserID(String userid, String orderno) {
		return orderDao.findOrderByOrderCodeAndUserID(Integer.valueOf(userid), orderno);
	}
	public int doCancelOrder(String orderno) {
		return orderDao.doCancelOrder(orderno);
	}
	public Serializable doOrderComplain(int userid,int touserid,String orderno,int orderitemid,String type,String comment,String imgurl,String imgurl2,String imgurl3) {
		TOrdCompain complain = new TOrdCompain();
		complain.setFromuserid(userid);
		complain.setTouserid(touserid);
		complain.setOrderitemid(orderitemid);
		complain.setOrderno(orderno);
		complain.setType(type);
		complain.setComment(comment);
		complain.setCreatetime(new Date());
		complain.setImgurl(imgurl);
		complain.setImgurl2(imgurl2);
		complain.setImgurl3(imgurl3);
		return orderDao.insertComplain(complain);
	}
	public Serializable doRemindSendGoods(TMemMsg newMsg) {
		return orderDao.doRemindSendGoods(newMsg);
	}
	public TOrdBase getOrderByCode(String orderno) {		
		return orderDao.getOrderByCode(orderno);
	}
	@Override
	public TOrdCompain getCompainById(int complainid) {
		return orderDao.getCompainById(complainid);
	}

}
