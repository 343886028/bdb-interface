package com.bdbvip.product.dao;

import java.io.Serializable;
import java.util.Map;

import com.bdbvip.entity.TProdJoin;
import com.bdbvip.entity.TProdList;
import com.bdbvip.entity.TProdSaleList;
import com.bdbvip.entity.TProdSaleSubscribe;
import com.bdbvip.pojo.SaleSubscribeVo;
import com.bdbvip.product.interfaces.vo.ProductVo;
import com.bdbvip.product.interfaces.vo.SaleTProdSaleListVo;
import com.bdbvip.utils.common.dao.support.Page;

/**
 * 拍卖品相关的dao接口
  * @ClassName: ProductDAO
  * @Description: TODO
  * @date 2015年12月4日 下午5:30:02
 */
public interface ProductDAO {
				
	
	Page listShopProduct(Map<String, String> params);
	
	ProductVo getProductByCode(String procode);
	
	public Serializable saveProduct(TProdList tProdList);
}
