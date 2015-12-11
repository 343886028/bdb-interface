package com.bdbvip.product.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.bdbvip.entity.TProdSaleList;
import com.bdbvip.entity.TProdSaleSubscribe;
import com.bdbvip.pojo.SaleSubscribeVo;
import com.bdbvip.product.dao.SellingDAO;
import com.bdbvip.product.interfaces.vo.SaleTProdSaleListVo;
import com.bdbvip.utils.common.dao.generic.HibernateGenericDao;
import com.bdbvip.utils.common.dao.support.Page;

@Repository("sellingDao")
public class SellingDAOImpl extends HibernateGenericDao implements SellingDAO {
	
	
	public TProdSaleList findProdListByProdSaleID(Integer prodSaleID) {
		return super.get(TProdSaleList.class, prodSaleID);
	}

	/**
	 * 拍品详细信息
	 */
	
	public SaleTProdSaleListVo findSaleProdListByProcode(String procode) {
		if (procode == null) {
			return null;
		}
		StringBuffer sql = new StringBuffer(
				"SELECT  sale.`prosaleid` as id ,sale.`procode` as `code`, sale.`price`,sale.`addprice`,sale.`bound`,sale.`createtime`,"
						+ "sale.`salestarttime`,sale.`saleendtime`,sale.`maxprice`,sale.`lasttime`,"
						+ "sale.`lastuserid`,base.`username` AS lastusername,sale.`saleflag`,sale.`descs`,sale.`activeid`,sale.`activename`,"
						+ "shop.`shopname`,sale.`aduitflag`,sale.`browers`,"
						+ "sale.`imgurl`,sale.`imgurl2`,sale.`imgurl3`" + "FROM t_prod_sale_list AS  sale "
						+ "LEFT JOIN t_mem_base AS base ON base.`id`=sale.`lastuserid` "
						+ "LEFT  JOIN t_mem_shop AS shop ON sale.`userid`=shop.`userid` "
						+ "WHERE sale.`procode`=?");
		List<Object> params=new ArrayList<Object>();
		params.add(procode);
		Page page = super.pagedQuerySql(sql.toString(), 1, 1,SaleTProdSaleListVo.class, params.toArray());
		if (page.getResult().size()>0) {
			return  (SaleTProdSaleListVo)page.getResult().get(0);
		}
		return null;
		
	}
	
	public Page listTProdSaleList(SaleTProdSaleListVo saleListVo) {
		if (saleListVo == null) {
			return null;
		}
		int currentPage = 1;
		int pagesize = 10;
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer(
				"SELECT  sale.`prosaleid` as id ,sale.`procode`, sale.`price`,sale.`addprice`,sale.`bound`,sale.`createtime`,"
						+ "sale.`salestarttime`,sale.`saleendtime`,sale.`maxprice`,sale.`lasttime`,"
						+ "sale.`lastuserid`,base.`username` AS lastusername,sale.`saleflag`,sale.`descs`,sale.`activeid`,sale.`activename`,"
						+ "shop.`shopname`,sale.`aduitflag`,sale.`browers`,"
						+ "sale.`imgurl`,sale.`imgurl2`,sale.`imgurl3`" + "FROM t_prod_sale_list AS  sale "
						+ "LEFT JOIN t_mem_base AS base ON base.`id`=sale.`lastuserid` "
						+ "LEFT JOIN t_mem_shop AS shop ON sale.`userid`=shop.`userid` ");

		if (saleListVo.getActiveid() != null) {
			sql.append(" where sale.activeid=? ");
			params.add(saleListVo.getActiveid());
		}
		if (StringUtils.isNotBlank(saleListVo.getProcode())) {
			if (sql.toString().contains("where")) {
				sql.append(" and sale.procode=? ");
			} else {
				sql.append(" where sale.procode=? ");
			}
			params.add(saleListVo.getProcode());
		}
		if (StringUtils.isNotBlank(saleListVo.getShopname())) {
			if (sql.toString().contains("where")) {
				sql.append(" and shop.shopname like ? ");
			} else {
				sql.append(" where shop.shopname like ? ");
			}
			params.add("%"+saleListVo.getShopname()+"%");
		}
		if (saleListVo.getSaleflag() != null) {
			if (sql.toString().contains("where")) {
				sql.append(" and sale.saleflag=? ");
			} else {
				sql.append(" where sale.saleflag=? ");
			}
			params.add(saleListVo.getSaleflag());
		}

		if (saleListVo.getPage() != null) {
			currentPage = saleListVo.getPage();
		}
		if (saleListVo.getPagesize() != null) {
			pagesize = saleListVo.getPagesize();
		}
		Page page = super.pagedQuerySql(sql.toString(), currentPage, pagesize, SaleTProdSaleListVo.class,
				params.toArray());
		return page;
	}
	
	// 获取单个 拍品所有信息
	public TProdSaleList findSimpleSaleProdByProcode(String procode) {

		@SuppressWarnings("unchecked")
		List<TProdSaleList> list = super.createQuery("select t from TProdSaleList as t where t.procode=:procode")
				.setString("procode", procode).list();
		if (list.size() > 0) {
			return (TProdSaleList)list.get(0);
		}
		return null;
	}

	/**
	 * 修改拍品
	 */
	public Serializable updateProdList(TProdSaleList tSaleList) {
		return super.update(tSaleList);
	}

	/**
	 * 保存订阅
	 */
	public Serializable saveSubcribe(TProdSaleSubscribe subscribe) {
		return super.save(subscribe);
	}

	/**
	 * 修改订阅
	 */
	public Serializable updateSubcribe(TProdSaleSubscribe subscribe) {
		return super.update(subscribe);
	}

	public int getSubcribeCount(SaleSubscribeVo saleSubscribe) {
		Criteria criteria=super.createCriteria(TProdSaleSubscribe.class);
		if (saleSubscribe.getProcode()!=null) {
			criteria.add(Restrictions.eq("procode", saleSubscribe.getProcode()));
		}
		if (saleSubscribe.getUserid()>0) {
			criteria.add(Restrictions.eq("userid", saleSubscribe.getUserid()));
		}
		return super.getCount(criteria);
	}

	public TProdSaleSubscribe findTProdSaleSubscribe(SaleSubscribeVo saleSubscribe) {
		if (saleSubscribe.getId() > 0) {
			return super.get(TProdSaleSubscribe.class, saleSubscribe.getId());
		}
		Criteria criteria = super.createCriteria(TProdSaleSubscribe.class);
		if (saleSubscribe.getProcode() != null) {
			criteria.add(Restrictions.eq("procode", saleSubscribe.getProcode()));
		}
		if (saleSubscribe.getUserid() > 0) {
			criteria.add(Restrictions.eq("userid", saleSubscribe.getUserid()));
		}

		Page page = super.pagedQuery(criteria, 1, 1);
		if (page.getResult().size() > 0) {
			return (TProdSaleSubscribe) page.getResult().get(0);
		}

		return null;
	}
}
