package com.bdbvip.shop.dao;

import java.io.Serializable;

import com.bdbvip.entity.TProdActive;
import com.bdbvip.entity.TProdJoin;
import com.bdbvip.pojo.ActivityVo;
import com.bdbvip.utils.common.dao.IEntityDao;
import com.bdbvip.utils.common.dao.support.Page;

public interface ActivityDAO extends IEntityDao<Integer>{

	Page listActivity(ActivityVo vo);
	
	Page listShopActivity(ActivityVo vo);
	
	TProdActive getActiveById(int activityId);
	
	int updateTProdActive(TProdActive active);
	
	Serializable saveTProdActive(TProdActive active);
	
	TProdJoin getJoinById(int activityId,int userid);
}
