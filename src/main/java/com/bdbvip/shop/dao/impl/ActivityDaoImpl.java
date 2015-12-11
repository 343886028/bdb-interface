package com.bdbvip.shop.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.bdbvip.entity.TProdActive;
import com.bdbvip.entity.TProdJoin;
import com.bdbvip.pojo.ActivityVo;
import com.bdbvip.shop.dao.ActivityDAO;
import com.bdbvip.utils.common.dao.entity.HibernateEntityDao;
import com.bdbvip.utils.common.dao.support.Page;

@Repository("activityDao")
public class ActivityDaoImpl extends HibernateEntityDao<Integer> implements ActivityDAO{

	public Page listActivity(ActivityVo vo) {
		StringBuffer sb = new StringBuffer("select id,activename,starttime,endtime,joinmoney,descs from t_prod_active where frouser=0");
		Map<String,Object> map = new HashMap<String,Object>();
		//开始时间
		if(vo.getStarttime()!=null){
			sb.append("and starttime>=:starttime");
			map.put("starttime", vo.getStarttime());
		}
		//截止时间
		if(vo.getEndtime()!=null){
			sb.append("and endtime<=:endtime");
			map.put("endtime", vo.getEndtime());
		}
		//活动名称
		if(!StringUtils.isBlank(vo.getActivename())){
			sb.append("and activename like ?");
			map.put("activename", "%"+vo.getActivename()+"%");
		}
		//活动状态
		if(!StringUtils.isBlank(vo.getStatus())){//0 未开始 1已开始 2 已结束
			sb.append("and status=:status");
			map.put("", vo.getStatus());
		}
		return super.pagedQuerySql(sb.toString(), vo.getPage(), vo.getPagesize(), map);
	}
	
	public Page listShopActivity(ActivityVo vo) {
		StringBuffer sb = new StringBuffer("select id,activename,starttime,endtime,joinmoney,descs,remark from t_prod_active where 1=1");
		Map<String,Object> map = new HashMap<String,Object>();
		//发起人id，若传此参数则查询店铺发起的活动
		if(vo.getFrouser()!=null){
			sb.append(" and frouser=:frouser");
			map.put("frouser", vo.getFrouser());
		}
		//开始时间
		if(vo.getStarttime()!=null){
			sb.append("and starttime>=:starttime");
			map.put("starttime", vo.getStarttime());
		}
		//截止时间
		if(vo.getEndtime()!=null){
			sb.append("and endtime<=:endtime");
			map.put("endtime", vo.getEndtime());
		}
		//活动名称
		if(!StringUtils.isBlank(vo.getActivename())){
			sb.append("and activename like ?");
			map.put("activename", "%"+vo.getActivename()+"%");
		}
		//活动状态
		if(!StringUtils.isBlank(vo.getStatus())){//0 未开始 1已开始 2 已结束
			sb.append("and status=:status");
			map.put("status", vo.getStatus());
		}
		return super.pagedQuerySql(sb.toString(), vo.getPage(), vo.getPagesize(), map);
	}

	public TProdActive getActiveById(int activityId) {
		return super.get(TProdActive.class, activityId);
	}
	
	public TProdJoin getJoinById(int activityId,int userid) {
		return (TProdJoin)super.createCriteria(TProdJoin.class).add(Restrictions.eq("activeid", activityId)).add(Restrictions.eq("userid", userid)).uniqueResult();
	}

	public int updateTProdActive(TProdActive active) {
		return super.update(active);
	}

	public Serializable saveTProdActive(TProdActive active) {
		return super.save(active);
	}
}
