package com.bdbvip.shop.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdbvip.entity.TMemShop;
import com.bdbvip.entity.TMemShopKind;
import com.bdbvip.shop.dao.ShopDAO;
import com.bdbvip.shop.interfaces.vo.SubscribeProVo;
import com.bdbvip.shop.interfaces.vo.SubscribeShopVo;
import com.bdbvip.shop.service.ShopService;
import com.bdbvip.utils.common.dao.support.Page;
import com.bdbvip.utils.common.exception.ServiceException;


@Service("shopService")
public class ShopServiceImpl implements ShopService {

	@Autowired
	ShopDAO shopDao; 
	
	public TMemShop findTMemshopByUserid(int userid) throws ServiceException {
		 return shopDao.findTMemShopByUserid(userid);
	}

	public Page listAttionShop(SubscribeShopVo vo) {
		return shopDao.listAttionShop(vo);
	}

	public Page listAttionPro(SubscribeProVo vo) {
		return shopDao.listAttionPro(vo);
	}

	public void updateTemplet(int userid,int showpara) throws ServiceException {
		shopDao.updateTemplet(userid,showpara);
	}

	public void updateCategory(TMemShopKind tMemShopKind)
			throws ServiceException {
		shopDao.deleteCategory(tMemShopKind.getUserid());
		shopDao.addCategory(tMemShopKind);
	}

	public List<TMemShopKind> listTMemShopKind(int userid) {
		return shopDao.listTMemShopKind(userid);
	}

	public Page ListSaleAmount(Map<String, String> map) {
		return shopDao.ListSaleAmount(map);
	}
}
