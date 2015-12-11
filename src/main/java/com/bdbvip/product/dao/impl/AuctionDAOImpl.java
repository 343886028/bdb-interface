package com.bdbvip.product.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.bdbvip.entity.TMemBidding;
import com.bdbvip.entity.TMemSaleConfig;
import com.bdbvip.entity.TMemSaleRegist;
import com.bdbvip.product.dao.AuctionDAO;
import com.bdbvip.product.interfaces.vo.SaleBidding;
import com.bdbvip.product.interfaces.vo.SaleTMemBiddingVo;
import com.bdbvip.utils.common.dao.generic.HibernateGenericDao;
import com.bdbvip.utils.common.dao.support.Page;

@Repository("auctionDao")
public class AuctionDAOImpl extends HibernateGenericDao implements AuctionDAO {

	public Serializable saveTMembidding(TMemBidding bidding) {
		return super.save(bidding);
	}

	// 获取拍卖纪录
	public Page listTMembidding(SaleTMemBiddingVo saleBiddingVo) {
		if (saleBiddingVo == null) {
			return null;
		}
		int currentpage = 1;
		int pagesize = 10;
		StringBuffer sql = new StringBuffer("SELECT b.`id`,b.`procode`,b.`userid`,m.`username`,b.`amount`,"
				+ "b.`createtime`,b.`type` FROM t_mem_bidding AS b LEFT JOIN "
				+ "t_mem_base AS m ON b.`userid`=m.`id` WHERE b.`procode`=?");
		List<Object> paramap = new ArrayList<Object>();
		paramap.add(saleBiddingVo.getProcode());
		if (saleBiddingVo.getPage() != null && saleBiddingVo.getPage() > 1) {
			currentpage = saleBiddingVo.getPage();
		}
		if (saleBiddingVo.getPagesize() != null && saleBiddingVo.getPagesize() > 0) {
			pagesize = saleBiddingVo.getPage();
		}

		Page page = super.pagedQuerySql(sql.toString(), currentpage, pagesize, SaleBidding.class, paramap.toArray());
		return page;
	}

	//获取用户自动竞拍设置
	public List<TMemSaleConfig> listAllTMemSaleConfig(SaleTMemBiddingVo sale) {
		List<Object> params = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer(
				"select s from TMemSaleConfig as s where s.productcode=? and s.maxvalues >? ");
		if (StringUtils.isNotBlank(sale.getProcode())) {
			params.add(sale.getProcode());
			params.add(sale.getAmount());
		}
		return  super.createQuery(hql.toString(), params.toArray()).list();
	}

	//保存用户添加拍品纪录
	public Serializable saveTMemSaleRegist(TMemSaleRegist regist) {
		return super.save(regist);
	}

}
