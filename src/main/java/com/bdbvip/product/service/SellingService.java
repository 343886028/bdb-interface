package com.bdbvip.product.service;

import java.io.Serializable;
import java.util.Map;

import com.bdbvip.entity.TProdSaleList;
import com.bdbvip.pojo.SaleSubscribeVo;
import com.bdbvip.product.interfaces.vo.SaleTProdSaleListVo;
import com.bdbvip.utils.common.exception.ServiceException;

/**
 * 拍品相关 service接口
 * @author Administrator
 *
 */
public interface SellingService {
	
	/**
	 * 根据拍品ID 查出拍卖的商品信息
	 * @param prodSaleID
	 * @return
	 */
	TProdSaleList findProdListByProdSaleID(String prodSaleID) throws ServiceException;
	
	
	/**
	 * 根据拍品编号获取拍品信息
	  * @Title: findProdListByProcode
	  * @param  procode
	  *         拍品编号
	  * @return  SaleProductVo
	  * @throws 
	 */
	
	Map<String, Object> dofindSaleProdListByProcode(String procode)throws ServiceException;
	
	
	/**
	 * 修改拍品信息
	  * @Title: updateProdListByProcode
	  * @param   tSaleList
	  * @return Serializable    
	  * @throws
	 */
	Serializable  updateProdListByProcode(TProdSaleList tSaleList)throws ServiceException;
	
	
	/**
	 *  订阅/取消订阅操作
	  * @Title: doSubscribe
	  * @param  subscribe
	  * @return Map<String,Object>    
	  * @throws ServiceException 
	 */
	Map<String,Object> doSubscribe(SaleSubscribeVo subscribe)throws ServiceException;
	
	
	/**
	 * 获取拍品列表
	 * @param saleprod
	 * @return
	 */
	Map<String, Object> listTProdSaleList(SaleTProdSaleListVo saleprod);
}
