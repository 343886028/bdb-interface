package com.bdbvip.login.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.bdbvip.entity.TSysConfig;
import com.bdbvip.login.dao.LoginDAO;
import com.bdbvip.utils.common.dao.entity.HibernateEntityDao;
import com.bdbvip.utils.common.dao.support.Page;
import com.bdbvip.utils.common.exception.DaoException;

@Repository("loginDao")
public class LoginDAOImpl extends HibernateEntityDao<Integer> implements LoginDAO {

	public Page findAllByStatus(TSysConfig config,Page page) throws DaoException {
	 
		Criteria criteria = super.createCriteria(TSysConfig.class);
		criteria.add(Restrictions.eq("status",config.getStatus()));
		return super.pagedQuery(criteria, page.getCurrentPageNo(), page.getPageSize());
	}

	 

}
