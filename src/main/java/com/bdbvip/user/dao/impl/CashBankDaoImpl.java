package com.bdbvip.user.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.bdbvip.entity.TMemBankinfo;
import com.bdbvip.entity.TMemCashTradeRecord;
import com.bdbvip.entity.TMemDrawRecord;
import com.bdbvip.entity.TMemPayRecord;
import com.bdbvip.user.dao.CashBankDAO;
import com.bdbvip.utils.common.dao.entity.HibernateEntityDao;
import com.bdbvip.utils.common.dao.support.Page;

@Repository("cashBankDao")
public class CashBankDaoImpl extends HibernateEntityDao<Integer> implements CashBankDAO {

	//保存银行卡信息
	public Serializable saveBankInfo(TMemBankinfo tBankinfo) {		
		return super.save(tBankinfo);
	}

	//修改银行卡信息
	public Serializable updateBank(TMemBankinfo tBankinfo) {
		return super.update(tBankinfo);
	}

	//删除银行卡信息
	public void deleteBank(TMemBankinfo tBankinfo){ 
		 super.getSession().delete(tBankinfo);
	}

	//获取银行卡信息
	public Page findBankList(Integer userId, Page page) {
		Criteria criteria=super.createCriteria(TMemBankinfo.class).add(Restrictions.eq("userid", userId)).addOrder(Order.desc("flag"));
		Page  p=super.pagedQuery(criteria, page.getCurrentPageNo(),page.getPageSize());
		return p;
	}

	// 用户id和银行卡号获取银行卡信息
	public TMemBankinfo findBankByUseridAndBankno(Integer userId, String bankNo) {
		Criteria criteria = super.createCriteria(TMemBankinfo.class).add(Restrictions.eq("userid", userId))
				.add(Restrictions.eq("bankno", bankNo));
		Page page = super.pagedQuery(criteria, 1, 1);
		if (page.getResult().size() > 0) {
			return (TMemBankinfo) page.getResult().get(0);
		}
		return null;
	}

	//修改多个银行卡
	public void updateBankAll(List<TMemBankinfo> tBankinfos) {
		 super.updateAll(tBankinfos);;
	}

	//用户id获取所有的银行卡信息
	@SuppressWarnings("unchecked")
	public List<TMemBankinfo> findBanKAllByUserId(Integer userId) {
		return super.createCriteria(TMemBankinfo.class).add(Restrictions.eq("userid", userId))
				.addOrder(Order.desc("id")).list();
	}

	// 使用用户id 获取默认银行卡信息
	public TMemBankinfo findBankByUseridAndDefault(Integer userId) {
		Criteria criteria = super.createCriteria(TMemBankinfo.class).add(Restrictions.eq("userid", userId))
				.add(Restrictions.eq("flag", "1"));

		Page page = super.pagedQuery(criteria, 1, 1);
		if (page.getResult().size() > 0) {
			return (TMemBankinfo) page.getResult().get(0);
		}
		return null;
	}

	// 获取该用户的银行卡个数
	public int findBankCountByUserId(Integer userId) {
		Criteria criteria = super.createCriteria(TMemBankinfo.class).add(Restrictions.eq("userid", userId));
		// super.getSession().createCriteria(TMemBankinfo.class).add(Restrictions.eq("userid",
		// userId))
		return super.getCount(criteria);
	}

	public int findBankCountByBankNo(String bankNo) {
		Criteria criteria = super.createCriteria(TMemBankinfo.class).add(Restrictions.eq("bankno", bankNo));
		// super.getSession().createCriteria(TMemBankinfo.class).add(Restrictions.eq("bankno",
		// bankNo))
		return super.getCount(criteria);
	}
		
}
