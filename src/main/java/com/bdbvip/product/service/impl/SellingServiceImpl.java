package com.bdbvip.product.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdbvip.entity.TMemShop;
import com.bdbvip.entity.TProdSaleList;
import com.bdbvip.entity.TProdSaleSubscribe;
import com.bdbvip.pojo.SaleSubscribeVo;
import com.bdbvip.product.dao.SellingDAO;
import com.bdbvip.product.interfaces.vo.SaleTProdSaleListVo;
import com.bdbvip.product.service.SellingService;
import com.bdbvip.shop.dao.ShopDAO;
import com.bdbvip.utils.common.dao.support.Page;
import com.bdbvip.utils.common.exception.ServiceException;

@Service("sellingService")
public class SellingServiceImpl implements SellingService {
	@Autowired
	SellingDAO sellingDAO;

	@Autowired
	ShopDAO shopDao;

	public TProdSaleList findProdListByProdSaleID(String prodSaleID) throws ServiceException {

		return sellingDAO.findProdListByProdSaleID(Integer.parseInt(prodSaleID));
	}

	
	// 单个拍品详情
	public Map<String, Object> dofindSaleProdListByProcode(String procode) throws ServiceException {
		Map<String, Object> result = new TreeMap<String, Object>();
		SaleTProdSaleListVo saleListVo = sellingDAO.findSaleProdListByProcode(procode);
		result.put("status", "0");
		result.put("msg", "交易成功");
		if (saleListVo != null) {
			result.put("data", saleListVo);
		} else {
			result.put("data", "");
		}
		
		//获取详情  商品浏览数量加 1
		TProdSaleList tProdSaleList=sellingDAO.findSimpleSaleProdByProcode(procode);
		int browers=tProdSaleList.getBrowers()+1;
		tProdSaleList.setBrowers(browers);
		updateProdListByProcode(tProdSaleList);
		
		return result;
	}

	//修改拍品
	public Serializable updateProdListByProcode(TProdSaleList tSaleList) {
		return sellingDAO.updateProdList(tSaleList);
	}

	//  关注,取消关注
	public Map<String, Object> doSubscribe(SaleSubscribeVo subscribe) throws ServiceException {
		Map<String, Object> result = new HashMap<String, Object>();
		int count = sellingDAO.getSubcribeCount(subscribe);
		//TProdSaleList saleList=productDao.findSimpleSaleProdByProcode(subscribe.getProcode());

		TMemShop shop = shopDao.findTMemShopByUserid(subscribe.getUserid());
		TProdSaleSubscribe tSubscribe = null;
		// 如果没有关注过 添加一条关注记录
		if (count == 0&&SaleSubscribeVo.SUBSCRIBE_TRUE.equals(subscribe.getSubflag())) {
			tSubscribe = new TProdSaleSubscribe();
			tSubscribe.setCreatetime(new Date());
			tSubscribe.setProcode(subscribe.getProcode());
			tSubscribe.setShopname(shop.getShopname());
			tSubscribe.setProstatus(SaleSubscribeVo.SUBSCRIBE_TRUE);
			tSubscribe.setShopid(shop.getId());
			tSubscribe.setTouserid(shop.getUserid());
			tSubscribe.setUserid(subscribe.getUserid());
			sellingDAO.saveSubcribe(tSubscribe);
			result.put("status", "0");
			result.put("msg", "交易成功");
			return result;
		}else if (SaleSubscribeVo.SUBSCRIBE_TRUE.equals(subscribe.getSubflag())) {
			tSubscribe = sellingDAO.findTProdSaleSubscribe(subscribe);
			tSubscribe.setProcode(SaleSubscribeVo.SUBSCRIBE_TRUE);
			tSubscribe.setUpdatetime(new Date());
			sellingDAO.updateSubcribe(tSubscribe);
			result.put("status","0");
			result.put("msg", "交易成功");
			return result;
		} else {
			tSubscribe = sellingDAO.findTProdSaleSubscribe(subscribe);
			tSubscribe.setProstatus(SaleSubscribeVo.SUBSCRIBE_FALSE);
			tSubscribe.setUpdatetime(new Date());
			sellingDAO.updateSubcribe(tSubscribe);
			result.put("status", "0");
			result.put("msg", "交易成功");
			return result;
		}

	}
	/**
	 * 获取拍品列表
	 */
	public Map<String, Object> listTProdSaleList(SaleTProdSaleListVo saleprod) {
		Map<String, Object> result = new HashMap<String, Object>();
		if (saleprod == null) {
			result.put("status", "");
			result.put("msg", "");
			return result;
		}
		Page page = sellingDAO.listTProdSaleList(saleprod);
		result.put("status", "0");
		result.put("msg", "交易成功");
		result.put("data", page.getResult());
		result.put("totle", page.getTotalCount());
		result.put("page", page.getCurrentPageNo());
		result.put("pagesize", page.getPageSize());

		return result;
	}


}
