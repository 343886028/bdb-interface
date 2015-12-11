package com.bdbvip.user.dao.impl;

import java.io.Serializable;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.bdbvip.entity.TMemLog;
import com.bdbvip.user.dao.UserOpsLogDAO;
import com.bdbvip.utils.common.dao.generic.HibernateGenericDao;
import com.bdbvip.utils.common.dao.support.Page;

@Repository("userOpsLogDao")
public class UserOpsLogDaoImpl extends HibernateGenericDao implements UserOpsLogDAO {

	/*
	 * 保存操作日志
	 */
	public Serializable saveOpsLog(TMemLog tLog) {
		return super.save(tLog);
	}

	/**
	 * 获取用户操作记录
	 */
	public Page findLogByuseridAndLogType(Integer userid, String logType) {
		System.out.println("------------------");
		Criteria criteria = super.createCriteria(TMemLog.class).add(Restrictions.eq("userid", userid))
				.add(Restrictions.like("logtype", logType,MatchMode.START)).addOrder(Order.desc("createtime"));
		
		System.out.println("=================");
		Page page = super.pagedQuery(criteria, 1, 3);

		return page;
	}

}
