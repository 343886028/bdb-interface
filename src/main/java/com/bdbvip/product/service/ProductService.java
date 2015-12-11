package com.bdbvip.product.service;

import java.io.Serializable;
import java.util.Map;

import com.bdbvip.entity.TProdList;
import com.bdbvip.entity.TProdSaleList;
import com.bdbvip.pojo.SaleJoinActivityVo;
import com.bdbvip.pojo.SaleSubscribeVo;
import com.bdbvip.product.interfaces.vo.ProductVo;
import com.bdbvip.product.interfaces.vo.SaleTProdSaleListVo;
import com.bdbvip.utils.common.dao.support.Page;
import com.bdbvip.utils.common.exception.ServiceException;

public interface ProductService {
	
	/**
	 * 商品查询
	 * @param pageNo
	 * @param pagesize
	 * @return
	 */
	public Page listShopProduct(Map<String, String> params);
	
	/**
	 * 通过商品编号查询商品
	 * @param procode
	 * @return
	 */
	public ProductVo getProductById(String procode);
	
	/**
	 * 商品上传
	 * @param product
	 * @return
	 * @throws ServiceException
	 */
	public Serializable saveProduct(TProdList tProdList) throws ServiceException; 
}
