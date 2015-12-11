package com.bdbvip.product.service;

import java.util.Map;

import com.bdbvip.product.interfaces.vo.SaleAuctionRegistVo;
import com.bdbvip.product.interfaces.vo.SaleTMemBiddingVo;
import com.bdbvip.utils.common.exception.ServiceException;

/**
 * 竞拍,竞价记录,自动竞价,拍卖纪录相关业务逻辑接口
  * @ClassName: AuctionService
  * @Description: TODO
  * @date 2015年12月4日 下午5:05:38
 */
public interface AuctionService {
	
	
	 
	 /**
	  *  用户竞价记录查询
	  * @param sBiddingVo
	  * @return
	  * @throws ServiceException
	  */
	public Map<String, Object> listSaleBidding(SaleTMemBiddingVo sBiddingVo) throws ServiceException;
	
	
	/**
	 *   拍卖出价 
	  * @Title: saveSaleBidding
	  * @param  sBiddingVo
	  * @param  ServiceException 
	  * @return Map<String,Object> 
	  * @Description 
	  * @throws
	 */
	public Map<String, Object> doAuctionSaleBidding(SaleTMemBiddingVo sBiddingVo) throws ServiceException;
	
	
	
	/**
	 * 报名参加拍卖活动
	 * @param registVo
	 * @return
	 */
	public Map<String, Object> doAuctionRegist(SaleAuctionRegistVo registVo) throws ServiceException;
	
	
	
	
	

}
