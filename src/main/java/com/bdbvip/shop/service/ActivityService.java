package com.bdbvip.shop.service;

import java.io.Serializable;
import java.util.Map;

import com.bdbvip.entity.TProdActive;
import com.bdbvip.entity.TProdJoin;
import com.bdbvip.pojo.ActivityVo;
import com.bdbvip.utils.common.dao.support.Page;
import com.bdbvip.utils.common.exception.ServiceException;

public interface ActivityService {

	/**
	 * 平台活动查询
	 * @param vo
	 * @return
	 */
	public Page listActivity(ActivityVo vo);
	
	/**
	 * 店铺活动查询
	 * @param vo
	 * @return
	 */
	public Page listShopActivity(ActivityVo vo);
	
	/**
	 * 根据活动id查询活动信息
	 * @param activityId
	 * @return
	 */
	public TProdActive getActiveById(int activityId);
	
	/**
	 * 参与活动
	 * @param active
	 * @return
	 * @throws ServiceException
	 */
	public Serializable saveTProdJoin(TProdJoin join) throws ServiceException;
	
	/**
	 * 店铺发起活动
	 * @param active
	 * @return
	 */
	public Serializable saveTProdActive(TProdActive active) throws ServiceException;
	
	/**
	 * 店铺活动修改
	 * @param active
	 * @return
	 */
	public void updateTProdActive(Map<String, String> params) throws ServiceException;
	
	public TProdJoin getJoinById(int activityId,int userid);
}
