package com.bdbvip.product.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.bdbvip.entity.TProdJoin;
import com.bdbvip.entity.TProdList;
import com.bdbvip.entity.TProdSaleList;
import com.bdbvip.entity.TProdSaleSubscribe;
import com.bdbvip.pojo.SaleSubscribeVo;
import com.bdbvip.product.dao.ProductDAO;
import com.bdbvip.product.interfaces.vo.ProductVo;
import com.bdbvip.product.interfaces.vo.SaleTProdSaleListVo;
import com.bdbvip.utils.common.dao.generic.HibernateGenericDao;
import com.bdbvip.utils.common.dao.support.Page;

@Repository("productDao")
public class ProductDAOImpl extends HibernateGenericDao implements ProductDAO {

	public Page listShopProduct(Map<String, String> params) {
		String hql = "SELECT id,proname,procode,title,type,mprice,offprice,imgurl,imgurl2,imgurl3,imgurl4,imgurl9,"
				+ "imgurl8,imgurl7,imgurl6,imgurl5,certiedimg,certiedimg2,protype,certiednon,issend,count,saletitle,"
				+ "userid,saleflag,auditflag,revokeflag,descs,browes,createtime,aduittime,saletime FROM TProdList where 1=1";
		StringBuffer sb = new StringBuffer(hql);
		List<Object> obj = new ArrayList<Object>();
		if(StringUtils.isNotBlank(params.get("userid"))){
			sb.append(" and userid=?");
			obj.add(params.get("userid"));
		}
		return super.pagedQuery(sb.toString(), Integer.valueOf(params.get("page")), Integer.valueOf(params.get("pagesize")), obj.toArray());
	}

	public ProductVo getProductByCode(String procode) {
		String hql = "SELECT id,proname,procode,title,type,mprice,offprice,imgurl,imgurl2,imgurl3,imgurl4,imgurl9,"
				+ "imgurl8,imgurl7,imgurl6,imgurl5,certiedimg,certiedimg2,protype,certiednon,issend,count,saletitle,"
				+ "userid,saleflag,auditflag,revokeflag,descs,browes,createtime,aduittime,saletime FROM TProdList where 1=1";
		StringBuffer sb = new StringBuffer(hql);
		List<Object> obj = new ArrayList<Object>();
		if(StringUtils.isNotBlank(procode)){
			sb.append(" procode=?");
			obj.add(procode);
		}
		ProductVo vo = null;
		List<ProductVo> list = super.createQuery(hql.toString(),obj.toArray()).list();
		if(list!=null&&list.size()>0){
			vo = list.get(0);
		}
		return vo;
	}

	public Serializable saveProduct(TProdList tProdList) {
		return super.save(tProdList);
	}



}
