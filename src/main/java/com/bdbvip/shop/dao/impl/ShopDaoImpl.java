package com.bdbvip.shop.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.bdbvip.entity.TMemShop;
import com.bdbvip.entity.TMemShopKind;
import com.bdbvip.shop.dao.ShopDAO;
import com.bdbvip.shop.interfaces.vo.SubscribeProVo;
import com.bdbvip.shop.interfaces.vo.SubscribeShopVo;
import com.bdbvip.utils.common.dao.entity.HibernateEntityDao;
import com.bdbvip.utils.common.dao.support.Page;

@Repository("shopDao")
public class ShopDaoImpl extends HibernateEntityDao<Integer> implements ShopDAO{

	/* (non-Javadoc)
	 * @see com.bdbvip.shop.dao.ShopDAO#findTMemShopByUserid(int)
	 */
	public TMemShop findTMemShopByUserid(int userid) {
//		Criteria criteria = super.createCriteria(TMemShop.class);
//		criteria.add(Restrictions.eq("userid", userid));
		return super.findUniqueBy(TMemShop.class, "userid",userid);
	}

	public Page listAttionShop(SubscribeShopVo vo) {
		String sql = "SELECT id,createtime,fromuserid,touser FROM t_mem_subscribe where 1=1";
		return super.pagedQuerySql(sql, vo.getPage(), vo.getPagesize());
	}

	public Page listAttionPro(SubscribeProVo vo) {
		StringBuffer sb = new StringBuffer("SELECT userid,touserid,shopname,procode,createtime,prostatus,id,updatetime FROM t_prod_sale_subscribe where 1=1");
		Map<String,Object> map = new HashMap<String,Object>();
		if(vo.getTouserid()!=null){
			sb.append(" and touserid=:touserid");
			map.put("touserid", vo.getTouserid());
		}
		return super.pagedQuery(sb.toString(), vo.getPage(), vo.getPagesize(),map);
	}

	public void updateTemplet(int userid,int showpara) {
		String hql = "update TMemShop set showpara=? where userid=?";
		super.createQuery(hql, new Object[]{userid,showpara});
	}

	public void addCategory(TMemShopKind tMemShopKind) {
		super.save(tMemShopKind);
	}
	
	public void deleteCategory(int userid) {
		String hql = "delete TMemShopKind where userid=?";
		super.createQuery(hql,userid).executeUpdate();
	}

	public List<TMemShopKind> listTMemShopKind(int userid) {
		return super.createCriteria(TMemShopKind.class).add(Restrictions.eq("userid", userid)).list();
	}

	public Page ListSaleAmount(Map<String, String> map) {
		return null;
	}
}
