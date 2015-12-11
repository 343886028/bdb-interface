package com.bdbvip.product.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdbvip.entity.TMemBidding;
import com.bdbvip.entity.TMemPass;
import com.bdbvip.entity.TMemSaleConfig;
import com.bdbvip.entity.TMemSaleRegist;
import com.bdbvip.entity.TProdActive;
import com.bdbvip.entity.TProdSaleList;
import com.bdbvip.product.dao.AuctionDAO;
import com.bdbvip.product.dao.ProductDAO;
import com.bdbvip.product.dao.SellingDAO;
import com.bdbvip.product.interfaces.vo.SaleAuctionRegistVo;
import com.bdbvip.product.interfaces.vo.SaleTMemBiddingVo;
import com.bdbvip.product.service.AuctionService;
import com.bdbvip.shop.service.ActivityService;
import com.bdbvip.user.service.CashService;
import com.bdbvip.user.service.CashTradingService;
import com.bdbvip.utils.Constants;
import com.bdbvip.utils.common.dao.support.Page;
import com.bdbvip.utils.common.exception.ServiceException;

@Service("auctionService")
public class AuctionServiceImpl implements AuctionService {
	private final Logger logger=Logger.getLogger(AuctionServiceImpl.class);

	@Autowired
	AuctionDAO auctionDao;

	@Autowired
	SellingDAO sellingDao;

	@Autowired
	ActivityService activityService;

	@Autowired
	CashService cashService;

	@Autowired
	CashTradingService cashTradingService;

	/**
	 * 竞拍记录查询
	 */
	public Map<String, Object> listSaleBidding(SaleTMemBiddingVo sBiddingVo) throws ServiceException {
		Map<String, Object> result = new HashMap<String, Object>();
		if (sBiddingVo == null) {
			result.put("status", "");
			result.put("msg", "");
			return result;
		}
		
		Page page = auctionDao.listTMembidding(sBiddingVo);
		
			
		result.put("status", "0");
		result.put("msg", "交易成功");
		result.put("data", page.getResult());
		result.put("totle", page.getTotalCount());
		result.put("page", page.getCurrentPageNo());
		result.put("pagesize", page.getPageSize());
		return result;
	}

	/**
	 * 保存拍卖出价
	 */
	public Map<String, Object> doAuctionSaleBidding(SaleTMemBiddingVo sBiddingVo) throws ServiceException {
		
		Map<String, Object> result = new TreeMap<String, Object>();
		if (sBiddingVo != null) {
			TMemBidding tMemBidding = new TMemBidding();
			tMemBidding.setActiveid(sBiddingVo.getActiveid());
			tMemBidding.setActivename(sBiddingVo.getActivename());
			tMemBidding.setAmount(sBiddingVo.getAmount());
			tMemBidding.setConfigid(sBiddingVo.getConfigid());
			tMemBidding.setCreatetime(sBiddingVo.getCreatetimeFormat());
			tMemBidding.setProcode(sBiddingVo.getProcode());
			tMemBidding.setProductid(sBiddingVo.getProductid());
			tMemBidding.setProductname(sBiddingVo.getProductname());
			tMemBidding.setType(SaleTMemBiddingVo.BIDDING_PERSION);
			tMemBidding.setStatus(sBiddingVo.getStatus());
			tMemBidding.setUserid(sBiddingVo.getUserid());
			auctionDao.saveTMembidding(tMemBidding);
			
			//修改拍品最后出价和最后出价人业务逻辑
			TProdSaleList tsale = sellingDao.findSimpleSaleProdByProcode(sBiddingVo.getProcode());
			tsale.setLasttime(new Date());
			tsale.setLastuserid(sBiddingVo.getUserid()); 
			tsale.setMaxprice(sBiddingVo.getAmount());
			sellingDao.updateProdList(tsale);
			
			List<TMemSaleConfig> tList=auctionDao.listAllTMemSaleConfig(sBiddingVo);
			if (tList.size()>0) {
				doAuction(tsale, tList);
			}
			
			result.put("status", "0");
			result.put("msg", "交易成功");
			return result;
		}

		return null;
	}

	
	// 自动竞价设置
	private void doAuction(TProdSaleList tsale, List<TMemSaleConfig> tList) {
		List<TMemSaleConfig> eachList = new ArrayList<TMemSaleConfig>();
		int size = tList.size();
		if (size > 1) {
			for (int i = 0; i < size; i++) {
				if (tList.get(i).getMaxvalues().compareTo(tsale.getMaxprice()) > 0) {
					BigDecimal init = tsale.getMaxprice().add(tList.get(i).getPrice());
					if (init.compareTo(tList.get(i).getMaxvalues()) > 0) {
						continue;
					}
					eachList.add(tList.get(i));
					// 最后竞价用户
					if (tsale.getLastuserid() == tList.get(i).getUserid()) {
						logger.info("Product.Service.doAuction==============>自动竞价结束！！！！！！！");
						break;
					}
					// 添加自动进价记录
					saveSaleListAndTMemBidding(tsale, tList.get(i));
				}
			}
			doAuction(tsale, eachList);
		}
	}
	
	/**
	 * 自动竞拍 保存自动报价记录
	 * @param tsale
	 * @param tSaleConfig
	 */
	private void saveSaleListAndTMemBidding(TProdSaleList tsale, TMemSaleConfig tSaleConfig) {
		// 添加自动进价记录
		TMemBidding tMemBidding = new TMemBidding();
		tMemBidding.setActiveid(tsale.getActiveid());
		tMemBidding.setActivename(tsale.getActivename());
		tMemBidding.setAmount(tsale.getMaxprice().add(tSaleConfig.getPrice()));
		tMemBidding.setCreatetime(new Date());
		tMemBidding.setProcode(tsale.getProcode());
		tMemBidding.setProductid(tsale.getProdid());
		tMemBidding.setProductname(tsale.getProname());
		tMemBidding.setType(SaleTMemBiddingVo.BIDDING_SYSTEM);
		tMemBidding.setUserid(tSaleConfig.getUserid());
		auctionDao.saveTMembidding(tMemBidding);

		//// 修改拍品最后出价和最后出价人
		tsale.setLasttime(new Date());
		tsale.setLastuserid(tSaleConfig.getUserid());
		tsale.setMaxprice(tsale.getMaxprice().add(tSaleConfig.getPrice()));
		sellingDao.updateProdList(tsale);

	}

	// 报名拍卖
	public Map<String, Object> doAuctionRegist(SaleAuctionRegistVo registVo) throws ServiceException {
		if (registVo == null) {
			return null;
		}

		Map<String, Object> result = new TreeMap<String, Object>();
		TMemSaleRegist regist = null;
		BigDecimal zero = new BigDecimal("0");

		TProdSaleList tsSale = sellingDao.findSimpleSaleProdByProcode(registVo.getProcode());
		if (tsSale == null) {
			result.put("status", "7002");
			result.put("msg", Constants.getParamterkey("sale.procode.error"));
			return result;
		}

		// 获取拍品所在的活动
		TProdActive tActive = activityService.getActiveById(tsSale.getActiveid());
		if (tActive == null) {
			result.put("status", "7003");
			result.put("msg", Constants.getParamterkey("sale.active.error"));
			return result;
		}

		// 活动是否结束判断
		if (tActive.getEndtime().compareTo(new Date()) <= 0) {
			result.put("status", "70031");
			result.put("msg", Constants.getParamterkey("sale.active.end"));
			return result;
		}

		// 不需要交保证金
		if ((tActive.getSalemoney().compareTo(zero) == 0) && (tsSale.getBound().compareTo(zero) == 0)) {
			regist = new TMemSaleRegist();
			regist.setCreatetime(new Date());
			regist.setIsflagmoney(0);
			regist.setMoney(new BigDecimal("0"));
			regist.setRegisterid(registVo.getUserid());
			regist.setOwnid(tsSale.getUserid());
			regist.setProid(tsSale.getProdid());
			regist.setProcode(regist.getProcode());
			regist.setShopid(tActive.getId());
			auctionDao.saveTMemSaleRegist(regist);
			result.put("status", "0");
			result.put("msg", Constants.getParamterkey(""));
			return result;
		}

		// 活动需要提交保证金 保证金 为 活动 保 证金
		if (tActive.getSalemoney().compareTo(zero) > 0) {
			if (!(tActive.getSalemoney().compareTo(registVo.getMoney()) == 0)) {
				// 保证金额度错误处理
				result.put("status", "7001");
				result.put("msg", Constants.getParamterkey("sale.money.notsame"));
				return result;
			}
		}

		// 活动不需要提交保证金 保证金 为 拍品保证金
		if (tActive.getSalemoney().compareTo(zero) == 0) {
			if (!(tsSale.getBound().compareTo(registVo.getMoney()) == 0)) {
				// 保证金额度错误处理
				result.put("status", "7001");
				result.put("msg", Constants.getParamterkey("sale.money.notsame"));
				return result;
			}
		}

		// 密码是否通过验证
		TMemPass tMemPass = new TMemPass();
		tMemPass.setUserid(registVo.getUserid());
		tMemPass.setPaypass(registVo.getPaypwd());
		Map<String, Object> doPwd = cashService.dovalidPwd(tMemPass);
		if (!"0".equals(doPwd.get("status"))) {
			result.putAll(doPwd);
			return result;
		}

		// 账户余额是否正常
		Map<String, Object> balance = cashService.findAccountByUserId(String.valueOf(registVo.getUserid()));
		if (!"0".equals(balance.get("status"))) {
			result.putAll(balance);
			return result;
		}

		// 提交保证金和余额比较
		BigDecimal cashmoney = BigDecimal.valueOf(Double.parseDouble(balance.get("cashmoney").toString()));
		if (cashmoney.compareTo(registVo.getMoney()) < 0) {
			result.put("status", "60041");
			result.put("msg", Constants.getParamterkey("user.cash.account.money.little"));
			return result;
		}

		Integer useridIn = 1;
		// 提交保证金处理 提交不成功 返回false
		if (!cashTradingService.doSubmitDeposit(registVo.getMoney(), registVo.getUserid(), useridIn)) {
			result.put("status", "70011");
			result.put("msg", Constants.getParamterkey("sale.money.submit.error"));
			return result;
		}
		regist = new TMemSaleRegist();
		regist.setCreatetime(new Date());
		regist.setIsflagmoney(0);
		regist.setMoney(registVo.getMoney());
		regist.setRegisterid(registVo.getUserid());
		regist.setOwnid(tsSale.getUserid());
		regist.setProid(tsSale.getProdid());
		regist.setProcode(registVo.getProcode());
		regist.setShopid(tActive.getId());
		auctionDao.saveTMemSaleRegist(regist);
		result.put("status", "0");
		result.put("msg", "报名成功");
		return result;
	}

}
