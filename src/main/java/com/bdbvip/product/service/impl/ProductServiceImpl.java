package com.bdbvip.product.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdbvip.entity.TMemShop;
import com.bdbvip.entity.TProdJoin;
import com.bdbvip.entity.TProdList;
import com.bdbvip.entity.TProdSaleList;
import com.bdbvip.entity.TProdSaleSubscribe;
import com.bdbvip.pojo.SaleJoinActivityVo;
import com.bdbvip.pojo.SaleSubscribeVo;
import com.bdbvip.product.dao.ProductDAO;
import com.bdbvip.product.interfaces.vo.ProductVo;
import com.bdbvip.product.interfaces.vo.SaleTProdSaleListVo;
import com.bdbvip.product.service.ProductService;
import com.bdbvip.shop.dao.ShopDAO;
import com.bdbvip.utils.common.dao.support.Page;
import com.bdbvip.utils.common.exception.ServiceException;

@Service("productService")
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductDAO productDao;
	
	/**
	 * 商品查询
	 * @param pageNo
	 * @param pagesize
	 * @return
	 */
	public Page listShopProduct(Map<String, String> params) {
		return productDao.listShopProduct(params);
	}

	/**
	 * 通过商品编号查询商品
	 * @param procode
	 * @return
	 */
	public ProductVo getProductById(String procode) {
		return productDao.getProductByCode(procode);
	}

	public Serializable saveProduct(TProdList tProdList) throws ServiceException {
		return productDao.saveProduct(tProdList);
	}

	

}
