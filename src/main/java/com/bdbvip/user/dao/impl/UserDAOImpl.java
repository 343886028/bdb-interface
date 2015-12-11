package com.bdbvip.user.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.bdbvip.entity.TMemAccount;
import com.bdbvip.entity.TMemAddress;
import com.bdbvip.entity.TMemBase;
import com.bdbvip.entity.TMemBinduser;
import com.bdbvip.entity.TMemConsult;
import com.bdbvip.entity.TMemMsg;
import com.bdbvip.entity.TMemSaleConfig;
import com.bdbvip.entity.TMemScore;
import com.bdbvip.entity.TMemShop;
import com.bdbvip.entity.TMemSm;
import com.bdbvip.entity.TMemSubscribe;
import com.bdbvip.entity.TOrdCompain;
import com.bdbvip.user.dao.UserDAO;
import com.bdbvip.utils.common.dao.entity.HibernateEntityDao;
import com.bdbvip.utils.common.dao.support.Page;

@Repository("userDao")
public class UserDAOImpl extends HibernateEntityDao<Integer> implements UserDAO {

	public TMemBase findUserById(String userId) {
		// return (TMemBase)getSession().get(TMemBase.class, userId);
		return super.get(TMemBase.class, Integer.parseInt(userId));
	}

	public void updateUser(TMemBase t) {
		super.update(t);
	}

	public TMemBinduser findBindByUserId(int userid) {
		List<TMemBinduser> binduserList = super.createCriteria(TMemBinduser.class).add(Restrictions.eq("userid", userid)).add(Restrictions.eq("flag", "1")).list();
		return ((binduserList!=null&&binduserList.size()>0)?binduserList.get(0):null) ;
	}

	/**
	 * Flag: 绑定的第三方账号 1为生效 0为未生效 ，即解绑
	 */
	public int doUnbind(int userId) {
		TMemBinduser bindUser = super.findUniqueBy(TMemBinduser.class, "userid", userId);
		bindUser.setFlag("0");
		return super.update(bindUser);
	}

	public int updateAttation(TMemSubscribe subscribe) {
		return super.saveOrUpdate(subscribe);
	}

	public TMemSubscribe findSubByTouserAndUser(int touserid, int userid) {
		return (TMemSubscribe) super.createCriteria(TMemSubscribe.class).add((Restrictions.eq("fromuserid", userid))).add((Restrictions.eq("touser", touserid))).list().get(0);
	}

	public int updatePhoneNO(TMemBase base) {
		return super.update(base);
	}

	public Serializable saveSaleConfig(TMemSaleConfig config) {
		return super.save(config);
	}

	public Serializable insertComplainSuggest(TOrdCompain complain) {
		return super.save(complain);
	}

	public TMemBase getUserByName(String uname) {
		return super.findUniqueBy(TMemBase.class, "username", uname);
	}

	public TMemAccount getTMemAccoutById(Integer id) {
		return super.get(TMemAccount.class, id);
	}

	public TMemScore getTMemScoreById(Integer id) {
		return super.get(TMemScore.class, id);
	}

	public TMemShop findTMemShopByName(String shopname) {
		return super.findUniqueBy(TMemShop.class, "shopname", shopname);
	}

	public TMemBinduser findTMemBindUserByBindId(String bindid, String type) {
		Criteria criteria = super.createCriteria(TMemBinduser.class);
		criteria.add((Restrictions.eq("bindid", bindid))).add((Restrictions.eq("type", type)));
		Page page = super.pagedQuery(criteria, 0, 1);
		if (null != page && !page.getResult().isEmpty()) {
			return (TMemBinduser) page.getResult().get(0);
		}
		return null;
	}

	public Serializable saveUserAddress(TMemAddress address) {
		return super.save(address);
	}

	public int deleteUserAddressByAddressID(int addressID) {
		return super.removeById(TMemAddress.class, addressID);
	}

	public int setDefaultUserAddress(int addressID, String flag) {
		TMemAddress address = (TMemAddress) super.get(TMemAddress.class, addressID);
		address.setFlag(flag);
		return super.update(address);
	}

	public void saveUserAddress(List<TMemAddress> addressList) {
		super.updateAll(addressList);
	}

	public Page listUserAddress(int userId, Page page) {
		Criteria criteria = super.createCriteria(TMemAddress.class).add(Restrictions.eq("userid", userId));
		return super.pagedQuery(criteria, 1, 100);
	}

	public TMemSaleConfig findHostingBidByParams(int userID, int touserID, String productCode) {
		return (TMemSaleConfig) super.createCriteria(TMemSaleConfig.class).add(Restrictions.eq("userid", userID)).add(Restrictions.eq("touserid", touserID))
				.add(Restrictions.eq("productcode", productCode)).list().get(0);
	}

	// 获取该用户最后一条短信
	public TMemSm geTMemSmsByUserId(int touserid) {
		Criteria criteria = super.createCriteria(TMemSm.class).add(Restrictions.eq("touserid", touserid)).addOrder(Order.desc("createtime"));
		Page page = super.pagedQuery(criteria, 1, 1);
		if (null != page && !page.getResult().isEmpty()) {
			return (TMemSm) page.getResult().get(0);
		}
		return null;
	}
	
	// 根据电话号码，获取用户最后一条短信
	public TMemSm geTMemSmsByTelephone(String telphone) {
		Criteria criteria = super.createCriteria(TMemSm.class).add(Restrictions.eq("telphone", telphone)).addOrder(Order.desc("createtime"));
		Page page = super.pagedQuery(criteria, 1, 1);
		if (null != page && !page.getResult().isEmpty()) {
			return (TMemSm) page.getResult().get(0);
		}
		return null;
	}

	public Serializable updateTemSms(TMemSm tMemSm) {
		return super.update(tMemSm);
	}

	public Page findMsgByStatus(int userId, String status, int pageNo, int pageSize) {
		Page page = null;
		if (status.equals("0")) {// 未读站内信息
			page = super.pagedQuery(super.createCriteria(TMemMsg.class).add(Restrictions.eq("0", status)).add(Restrictions.eq("userid", userId)), pageNo, pageSize);
		} else if (status.equals("1")) {// 已读站内
			page = super.pagedQuery(super.createCriteria(TMemMsg.class).add(Restrictions.eq("1", status)).add(Restrictions.eq("userid", userId)), pageNo, pageSize);
		} else {// 查询所有站内信息
			page = super.pagedQuery(super.createCriteria(TMemMsg.class).add(Restrictions.eq("userid", userId)), pageNo, pageSize);
		}
		return page;
	}

	public Page findMsgById(int msgid) {
		return super.pagedQuery(super.createCriteria(TMemMsg.class).add(Restrictions.eq("msgid", msgid)), 1, 20);
	}
	
	public int saveConsult(TMemConsult consult) {
		return super.create(consult);
	}
	
	public Page listSuggest(Map<String,String> map) {
		StringBuffer sb = new StringBuffer("SELECT complainid,orderno,orderitemid,comment,imgurl2,imgurl3,flag,endflag FROM t_ord_compain");
		//后续可以对各种条件进行筛选if
		return super.pagedQuerySql(sb.toString(),Integer.valueOf(map.get("page")), Integer.valueOf(map.get("pagesize")));
	}
}
