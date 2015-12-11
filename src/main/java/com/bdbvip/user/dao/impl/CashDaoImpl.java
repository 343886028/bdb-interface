package com.bdbvip.user.dao.impl;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.bdbvip.entity.TMemAccount;
import com.bdbvip.entity.TMemCashTradeRecord;
import com.bdbvip.entity.TMemDrawRecord;
import com.bdbvip.entity.TMemFrozenRecord;
import com.bdbvip.entity.TMemPass;
import com.bdbvip.entity.TMemPayRecord;
import com.bdbvip.pojo.CashTradeVo;
import com.bdbvip.user.dao.CashDAO;
import com.bdbvip.utils.common.dao.entity.HibernateEntityDao;
import com.bdbvip.utils.common.dao.support.Page;




@Repository("cashDao")
public class CashDaoImpl extends HibernateEntityDao<Integer> implements CashDAO {
	

	/**
	 * 保存密码
	 */
	public Serializable savePayPwd(TMemPass tPass) {
		return super.save(tPass);
	}

	/**
	 * 根据用户id获取用户密码信息
	 */
	public TMemPass findTMempassByUserId(Integer userId) {
		return super.get(TMemPass.class, userId);
	}

	/**
	 * 修改支付密码
	 */
	public Serializable updatePayPwd(TMemPass tMemPass) {
		return super.update(tMemPass);
	}

	/**
	 * 账户余额查询
	 */
	public TMemAccount findAccountByUserId(Integer userId) {

		return super.get(TMemAccount.class, userId);
	}
	
	/**
	 * 修改用户余额
	 */
	public Serializable updateAccountByUserId(TMemAccount account) {
		return super.update(account);
	}

	/**
	 * 添加用户现金账户
	 */
	public Serializable saveAccount(TMemAccount account) {
		return super.save(account);
	}
	
	/**
	 * 保存充值记录
	 */
	public Serializable savePayRecore(TMemPayRecord payRecord) {
		return super.save(payRecord);
	}
	
	

	/**
	 * 保存提现记录
	 */
	public Serializable saveDrawRecore(TMemDrawRecord drawRecord) {
		return super.save(drawRecord);
	}

	/**
	 * 保存交易流水
	 */
	public Serializable saveCashTradeRecord(TMemCashTradeRecord cashTradeRecord) {
		return super.save(cashTradeRecord);
	}

	/**
	 * 获取交易流水
	 */
	public Page findTradRecordList(CashTradeVo cTradeVo, Page page) {

		StringBuffer hql = new StringBuffer(
				"select r ,b.username from  TMemCashTradeRecord as r,TMemBase as b where r.fromuser=b.id  ");
	    List<Object> paralist = new LinkedList<Object>();
		if(cTradeVo !=null ){
			if(cTradeVo.getUserid()>0){
				hql.append(" and r.userid = ? ");
				paralist.add(cTradeVo.getUserid());
			}
			if (cTradeVo.getEndTime()!=null) {
				hql.append(" and r.createtime <= ? ");
				paralist.add(cTradeVo.getEndTime());
			}
			if (cTradeVo.getStartTime()!=null) {
				hql.append(" and r.createtime >=?");
				paralist.add( cTradeVo.getStartTime());
			}
			if (StringUtils.isNotBlank(cTradeVo.getTradeno())) {
				hql.append(" and r.tradeno =?");
				paralist.add( cTradeVo.getTradeno());
			}
			if (StringUtils.isNotBlank(cTradeVo.getTradetype())) {
				hql.append(" and r.tradetype =?");
				paralist.add( cTradeVo.getTradetype());
			}
		}
		return super.pagedQuery(hql.toString(), page.getCurrentPageNo(), page.getPageSize(), paralist.toArray());
		
	}

	//保存资金冻结记录
	public Serializable saveTMemFrozenRecord(TMemFrozenRecord frozenRecord) {
		return super.save(frozenRecord);
	}
	

	/**
	 * 根据orderNO 获取交易流水
	 */
	public TMemCashTradeRecord findTradeByOrderNO(String orderno) {
		return super.findUniqueBy(TMemCashTradeRecord.class, "orderno", orderno);
	}
}
