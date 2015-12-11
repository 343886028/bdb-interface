package com.bdbvip.order.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.bdbvip.entity.TMemMsg;
import com.bdbvip.entity.TOrdBase;
import com.bdbvip.entity.TOrdCompain;
import com.bdbvip.entity.TOrdItem;
import com.bdbvip.utils.common.dao.support.Page;

public interface OrderService {

	/**
	 * 根据订单的查询条件，查询出所有符合的记录
	 * 
	 * @param orderBase
	 * @param pageNO
	 * @param pageSize
	 * @return
	 */
	Page getOrderListByCondition(TOrdBase orderBase, int pageNO, int pageSize);

	/**
	 * 根据订单号，查询
	 * 
	 * @return
	 */
	TOrdBase getOrderByCode(String ordeno);

	/**
	 * 根据订单号，查询所有订单下的物品详情 orderItem
	 * 
	 * @param orderNO
	 * @param pageNO
	 * @param pageSize
	 * @return
	 */
	Page getOrderItemListByOrderNO(String orderNO, int pageNO, int pageSize);
	
	List<TOrdItem> getOrderItemListByOrderNO(String orderNO);

	/**
	 * 延时收货
	 * 
	 * @param orderNO
	 *            订单号
	 * @param days
	 *            延迟天数
	 * @return
	 */
	int doOrderDelay(String orderNO, int days);

	/**
	 * 确认收货
	 * 
	 * @param orderNO
	 * @return
	 */
	int doOrderRecieve(String orderNO,String receiveFlag);

	/**
	 * 订单发货
	 * 
	 * @param orderno
	 * @param sendno
	 * @param sendname
	 * @param sendmoney
	 * @param sendflag
	 * @return
	 */
	int doSendGoods(String orderno, String sendno, String sendname, BigDecimal sendmoney, String sendflag);

	/**
	 * 修改订单价格
	 * 
	 * @param orderno
	 * @param oldordermoney
	 * @return
	 */
	int updateOrderPrice(String orderno, String oldordermoney);

	/**
	 * 根据订单编号，取消订单
	 * 
	 * @param orderno
	 * @return
	 */
	int doCancelOrder(String orderno);

	/**
	 * 
	 * @param userid
	 *            投诉人/建议人
	 * @param touserid
	 *            被投诉人/被建议人
	 * @param orderno
	 *            订单号
	 * @param orderitemid
	 *            订单物品号
	 * @param type
	 *            投诉/建议 0投诉 1建议
	 * @param comment
	 *            投诉/建议 内容 *
	 * @param imgurl
	 * @param imgurl2
	 * @param imgurl3
	 * @return
	 */
	Serializable doOrderComplain(int userid, int touserid, String orderno, int orderitemid, String type, String comment, String imgurl, String imgurl2, String imgurl3);

	/**
	 * 提醒发货
	 * 
	 * @param newMsg
	 * @return
	 */
	Serializable doRemindSendGoods(TMemMsg newMsg);

	/**
	 * 根据订单号与用户ID，查找订单
	 * 
	 * @param userid
	 * @param orderno
	 * @return
	 */
	TOrdBase findOrderByOrderCodeAndUserID(String userid, String orderno);
	
	/**
	 * 通过id查询订单投诉建议
	 * @param complainid
	 * @return
	 */
	public TOrdCompain getCompainById(int complainid);

}
