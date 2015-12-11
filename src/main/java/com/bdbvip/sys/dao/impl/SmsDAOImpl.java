package com.bdbvip.sys.dao.impl;


import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.bdbvip.entity.TMemSm;
import com.bdbvip.sys.dao.SmsDAO;
import com.bdbvip.utils.Constants;
import com.bdbvip.utils.common.dao.entity.HibernateEntityDao;
import com.bdbvip.utils.common.dao.support.Page;
import com.bdbvip.utils.common.date.DateUtil;

@Repository("smsDao")
public class SmsDAOImpl extends HibernateEntityDao<Integer> implements SmsDAO{

	public Page findBySms(TMemSm sm,Page page) {
		Criteria criteria = super.createCriteria(TMemSm.class);
		if(sm!=null && sm.getTouserid()>0){
			criteria.add(Restrictions.eq("touserid", sm.getTouserid()));
		}
		if(sm!=null && sm.getCreatetime()!=null){
			criteria.add(Restrictions.gt("createtime", sm.getCreatetime()));
		}else{
			criteria.add(Restrictions.gt("createtime",DateUtil.addMinutes(new Date(),-Constants.SMS_MAX_VALID)));
		}
		if(sm!=null && sm.getValidtime()!=null){
			criteria.add(Restrictions.gt("validtime", new Date()));
		}
		if(sm!=null && StringUtils.isNotBlank(sm.getTelphone())){
			criteria.add(Restrictions.eq("telphone", sm.getTelphone()));
		}
		criteria.addOrder(Order.desc("createtime"));
		return super.pagedQuery(criteria, page.getCurrentPageNo(), page.getPageSize());
	}
}
