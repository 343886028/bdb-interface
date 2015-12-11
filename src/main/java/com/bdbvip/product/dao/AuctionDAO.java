package com.bdbvip.product.dao;

import java.io.Serializable;
import java.util.List;

import com.bdbvip.entity.TMemBidding;
import com.bdbvip.entity.TMemSaleConfig;
import com.bdbvip.entity.TMemSaleRegist;
import com.bdbvip.product.interfaces.vo.SaleTMemBiddingVo;
import com.bdbvip.utils.common.dao.support.Page;

/**
 * 竞价相关业务dao接口
  * @ClassName: AuctionDAO
  * @Description: TODO
  * @date 2015年12月4日 下午5:30:35
 */
public interface AuctionDAO {
	
	/**
	 * 保存用户竞价记录
	  * @Title: saveTMembidding
	  * @param  bidding
	  * @return Serializable    
	  * @throws
	 */
	public Serializable saveTMembidding(TMemBidding bidding);
	
	
	/**
	 * 获取用户竞价记录
	  * @Title: listTMembidding
	  * @param  saleBiddingVo
	  * @return Page    
	  * @throws
	 */
	public Page listTMembidding(SaleTMemBiddingVo saleBiddingVo);
	
	

     /**
      * 获取用户设置的自动竞拍
      * @param sale
      * @return
      */
	public List<TMemSaleConfig> listAllTMemSaleConfig(SaleTMemBiddingVo sale);
	
	
	/**
	 * 保存用户参加拍卖纪录
	 * @param regist
	 * @return
	 */
	public Serializable saveTMemSaleRegist(TMemSaleRegist regist);

}
