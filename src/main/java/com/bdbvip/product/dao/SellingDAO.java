package com.bdbvip.product.dao;

import java.io.Serializable;

import com.bdbvip.entity.TProdSaleList;
import com.bdbvip.entity.TProdSaleSubscribe;
import com.bdbvip.pojo.SaleSubscribeVo;
import com.bdbvip.product.interfaces.vo.SaleTProdSaleListVo;
import com.bdbvip.utils.common.dao.support.Page;

public interface SellingDAO {
	/**
	 * 获取拍卖品详情
	  * @Title: findProdListByProdSaleID
	  * @param prodSaleID 拍卖品id 
	  * @return TProdSaleList    
	  * @throws
	 */
	public TProdSaleList findProdListByProdSaleID(Integer prodSaleID);
	
	
	
	/**  单个拍品查询
	 *  SaleTProdSaleListVo
	 * @param procode 拍品编号
	 * @return
	 */
	public SaleTProdSaleListVo findSaleProdListByProcode(String procode);
	
	
	/**
	 * 拍品查询
	 * @param saleProductVo
	 * @return
	 */
	public Page listTProdSaleList(SaleTProdSaleListVo saleListVo);
	
	
	/**
	 * 
	 * @param procode 
	 *    拍品标号
	 * @return
	 */
	public TProdSaleList findSimpleSaleProdByProcode(String procode);
	
	/**
	 * 修改拍品信息
	  * @Title: updateProdList
	  * @param  tSaleList  封装修改的对象
	  * @return Serializable    
	  * @throws
	 */
	public Serializable updateProdList(TProdSaleList tSaleList);
	
	
	/**
	 * 保存订阅
	  * @Title: saveSubcribe
	  * @param subscribe
	  * @return Serializable    
	  * @throws
	 */
	public Serializable saveSubcribe(TProdSaleSubscribe subscribe);
	
	/**
	 * 修改订阅
	  * @Title: saveSubcribe
	  * @param subscribe
	  * @return Serializable    
	  * @throws
	 */
	public Serializable updateSubcribe(TProdSaleSubscribe subscribe);
	
	
	/**
	 * 获取给定条件影响条数
	  * @Title: getSubcribeCount
	  * @param  saleSubscribe
	  * @return int    
	  * @throws
	 */
	public int getSubcribeCount(SaleSubscribeVo saleSubscribe);
	
	
	/**
	 * 获取关注信息
	 * @param saleSubscribe
	 * @return
	 */
	public TProdSaleSubscribe findTProdSaleSubscribe(SaleSubscribeVo saleSubscribe);
}
