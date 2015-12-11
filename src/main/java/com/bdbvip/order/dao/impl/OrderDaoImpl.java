package com.bdbvip.order.dao.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.bdbvip.entity.TMemMsg;
import com.bdbvip.entity.TOrdBase;
import com.bdbvip.entity.TOrdCompain;
import com.bdbvip.entity.TOrdItem;
import com.bdbvip.order.dao.OrderDao;
import com.bdbvip.utils.common.dao.entity.HibernateEntityDao;
import com.bdbvip.utils.common.dao.support.Page;

@Repository("orderDao")
public class OrderDaoImpl extends HibernateEntityDao<Integer> implements OrderDao {
	public Page getOrderListByCondition(TOrdBase orderBase,int pageNO,int pageSize) {
		Criteria orderCriteria = super.createCriteria(TOrdBase.class);
		orderCriteria.add(Restrictions.eq("userid", orderBase.getUserid()));
		if(orderBase.getCreatetime()!=null) {
			orderCriteria.add(Restrictions.eq("userid", orderBase));
		}
		if(StringUtils.isNotBlank(orderBase.getOrderno()) && orderBase.getOrderno()!=null) {
			orderCriteria.add(Restrictions.eq("orderno", orderBase.getOrderno()));
		}
		if(StringUtils.isNotBlank(orderBase.getOrderstatus())) {
			orderCriteria.add(Restrictions.eq("orderstatus", orderBase.getOrderstatus()));
		}
		if(StringUtils.isNotBlank(String.valueOf(orderBase.getTouserid()))){
			orderCriteria.add(Restrictions.eq("touserid", orderBase.getTouserid()));
		}
		return super.pagedQuery(orderCriteria, pageNO, pageSize);
	}

	public Serializable insertComplain(TOrdCompain complain) {
		super.save(complain);
		return complain.getComplainid();
	}

	public Page getOrderItemListByOrderNO(String orderNO, int pageNO, int pageSize) {
		
		return null;
	}
	
	public List<TOrdItem> getOrderItemListByOrderNO(String orderNO) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * RECEIVEFLAG 默认为0,未收货   1 部分收货  2 全收货
	 * 当ITEM全部收货时，此值才会改为2，否则为1
	 * 
	 */
	public int doOrderDelay(String orderNO, int days) {
		return super.createQuery("update TOrdBase t set t.delayreceiveflag = '1' and t.delaydays=:delaydays where t.orderno=" + orderNO).setParameter("delaydays", days).executeUpdate();
	}
	
	public int doOrderRecieve(String orderNO,String receiveflag) {
		return super.createQuery("update TOrdBase t set t.receiveflag = :receiveflag where t.orderno=" + orderNO).setParameter("receiveflag", receiveflag).executeUpdate();
	}
	
	public TOrdBase findOrderByOrderCodeAndUserID(Integer userid, String orderno) {
		return (TOrdBase)super.createCriteria(TOrdBase.class).add(Restrictions.eq("userid", userid)).add(Restrictions.eq("orderno", orderno)).uniqueResult();
	}

	/**
	 * 只更新部分字段，避免全字段更新，影响性能
	 * @param orderno 订单号
	 * @param sendno 快递单号
	 * @param sendname 快递公司
	 * @return
	 */
	public int doSendGoods(String orderno, String sendno, String sendname, BigDecimal sendmoney, String sendflag) {
		return super.createQuery("update TOrdBase t set t.sendno = :sendno and t.sendname = :sendname and t.sendmoney=:sendmoney and t.sendflag=:sendflag where t.orderno=" + orderno)
				.setParameter("sendno", sendno).setParameter("sendname", sendname).executeUpdate();
	}
	
	public Serializable doRemindSendGoods(TMemMsg newMsg) {
		return super.save(newMsg);
	}

	public int updateOrderPrice(String orderno, BigDecimal oldordermoney) {
		return super.createQuery("update TOrdBase t set t.oldordermoney = :oldordermoney where t.orderno=" + orderno).setParameter("oldordermoney", oldordermoney).executeUpdate();
	}
	/**
	 * 根据订单编号，取消订单
	 * orderStatus : 默认0,处理中，1已完成，2 终止交易
	 */
	public int doCancelOrder(String orderno) {
		return super.createQuery("update TOrdBase t set t.orderstatus = :orderstatus where t.orderno=" + orderno).setParameter("orderstatus", 2).executeUpdate();
	}
	
	public TOrdBase getOrderByCode(String ordeno) {
		return (TOrdBase)super.createCriteria(TOrdBase.class).add(Restrictions.eq("orderno", ordeno)).uniqueResult();
	}

	@Override
	public TOrdCompain getCompainById(int compainid) {
		return null;
	}
}
