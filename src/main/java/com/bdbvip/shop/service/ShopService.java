package com.bdbvip.shop.service;

import java.util.List;
import java.util.Map;

import com.bdbvip.entity.TMemShop;
import com.bdbvip.entity.TMemShopKind;
import com.bdbvip.shop.interfaces.vo.SubscribeProVo;
import com.bdbvip.shop.interfaces.vo.SubscribeShopVo;
import com.bdbvip.utils.common.dao.support.Page;
import com.bdbvip.utils.common.exception.ServiceException;

public interface ShopService {

	/**
	 * 根据登录USERID,获取用户的商铺。
	 * @param userid
	 * @return
	 * @throws ServiceException
	 */
	public TMemShop findTMemshopByUserid(int userid) throws ServiceException;
	
	/**
	 * 受关注店铺查询
	 * @param vo
	 * @return
	 */
	public Page listAttionShop(SubscribeShopVo vo);
	
	/**
	 * 受关注商品查询
	 * @param vo
	 * @return
	 */
	public Page listAttionPro(SubscribeProVo vo);
	
	/**
	 * 店铺模板修改
	 * @param vo
	 * @return
	 */
	public void updateTemplet(int userid,int showpara) throws ServiceException;
	
	/**
	 * 店铺经营类别修改
	 * @param vo
	 * @return
	 */
	public void updateCategory(TMemShopKind tMemShopKind) throws ServiceException;
	
	public List<TMemShopKind> listTMemShopKind(int userid);
	
	public Page ListSaleAmount(Map<String,String> map);
}
