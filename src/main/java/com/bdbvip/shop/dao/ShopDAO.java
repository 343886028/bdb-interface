package com.bdbvip.shop.dao;

import java.util.List;
import java.util.Map;

import com.bdbvip.entity.TMemShop;
import com.bdbvip.entity.TMemShopKind;
import com.bdbvip.shop.interfaces.vo.SubscribeProVo;
import com.bdbvip.shop.interfaces.vo.SubscribeShopVo;
import com.bdbvip.utils.common.dao.IEntityDao;
import com.bdbvip.utils.common.dao.support.Page;

public interface ShopDAO extends IEntityDao<Integer>{

	/**
	 * 根据用户ID获取已经通过且有效的店铺。
	 * ***不排除以后会，一个登录用户同时开多家店铺的可能，目前只能是一个。
	 * @param userid
	 * @return
	 */
	public TMemShop findTMemShopByUserid(int userid);
	
	Page listAttionShop(SubscribeShopVo vo);
	
	Page listAttionPro(SubscribeProVo vo);
	
	void updateTemplet(int userid,int showpara);
	
	void addCategory(TMemShopKind tMemShopKind);
	
	void deleteCategory(int userid);
	
	List<TMemShopKind> listTMemShopKind(int userid);
	
	Page ListSaleAmount(Map<String,String> map);
}
