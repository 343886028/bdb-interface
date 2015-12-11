package com.bdbvip.shop.service.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdbvip.entity.TProdActive;
import com.bdbvip.entity.TProdJoin;
import com.bdbvip.pojo.ActivityVo;
import com.bdbvip.shop.dao.ActivityDAO;
import com.bdbvip.shop.service.ActivityService;
import com.bdbvip.utils.common.dao.support.Page;
import com.bdbvip.utils.common.date.DateUtil;
import com.bdbvip.utils.common.exception.ServiceException;


@Service("activityService")
public class ActivityServiceImpl implements ActivityService {

	@Autowired
	ActivityDAO activityDAO;

	public Page listActivity(ActivityVo vo) {
		return activityDAO.listActivity(vo);
	}
	
	public Page listShopActivity(ActivityVo vo) {
		return activityDAO.listShopActivity(vo);
	}

	public TProdActive getActiveById(int activityId) {
		return activityDAO.getActiveById(activityId);
	}

	public Serializable saveTProdJoin(TProdJoin join)
			throws ServiceException {
		activityDAO.save(join);
		return join;
	}

	public Serializable saveTProdActive(TProdActive active)
			throws ServiceException {
		activityDAO.saveTProdActive(active);
		return active;
	}

	public void updateTProdActive(Map<String, String> params)
			throws ServiceException {
		TProdActive active = activityDAO.getActiveById(Integer.valueOf(params.get("id")));
		if(active!=null){
			boolean isedit = false;
			if(StringUtils.isNotBlank(params.get("activename"))){
				active.setActivename(params.get("activename"));
				isedit = true;
			}
			if(StringUtils.isNotBlank(params.get("begtime"))){
				active.setStarttime(DateUtil.string2Date(params.get("begtime"),DateUtil.simpleDateFormat5));
				isedit = true;
			}
			if(StringUtils.isNotBlank(params.get("endtime"))){
				active.setEndtime(DateUtil.string2Date(params.get("endtime"),DateUtil.simpleDateFormat5));
				isedit = true;
			}
			if(StringUtils.isNotBlank(params.get("desc"))){
				active.setDescs(params.get("desc"));
				isedit = true;
			}
			if(StringUtils.isNotBlank(params.get("joinmoney"))){
				BigDecimal bd = new BigDecimal(params.get("joinmoney"));
				active.setJoinmoney(bd);
				isedit = true;
			}
			if(StringUtils.isNotBlank(params.get("type"))){
				active.setType(params.get("type"));
				isedit = true;
			}
			if(StringUtils.isNotBlank(params.get("frouser"))){
				active.setFrouser(Integer.valueOf(params.get("frouser")));
				isedit = true;
			}
			if(StringUtils.isNotBlank(params.get("addprice"))){
				active.setAddprice(new BigDecimal(params.get("addprice")));
				isedit = true;
			}
			if(StringUtils.isNotBlank(params.get("status"))){
				active.setStatus(params.get("status"));
				isedit = true;
			}
			if(StringUtils.isNotBlank(params.get("price"))){
				active.setPrice(new BigDecimal(params.get("price")));
				isedit = true;
			}
			if(StringUtils.isNotBlank(params.get("frousermoney"))){
				active.setFrousermoney(new BigDecimal(params.get("frousermoney")));
				isedit = true;
			}
			if(StringUtils.isNotBlank(params.get("joinmoney"))){
				active.setJoinmoney(new BigDecimal(params.get("joinmoney")));
				isedit = true;
			}
			if(StringUtils.isNotBlank(params.get("optuser"))){
				active.setOptuser(Integer.valueOf(params.get("optuser")));
				isedit = true;
			}
			if(StringUtils.isNotBlank(params.get("remark"))){
				active.setRemark(params.get("remark"));
				isedit = true;
			}
			active.setType("1");//默认0平台 1店铺
			active.setAduitflag("0");//默认0 未审 1 已通过 2未通过
			active.setStatus("1");//默认1有效 0无效 
			if(isedit){
				activityDAO.updateTProdActive(active);
			}
		}
	}
	public TProdJoin getJoinById(int activityId,int userid) {
		return activityDAO.getJoinById(activityId,userid);
	}
}
