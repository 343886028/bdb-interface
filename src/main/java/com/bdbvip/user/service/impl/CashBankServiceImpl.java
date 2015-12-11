package com.bdbvip.user.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.bdbvip.entity.TMemBankinfo;
import com.bdbvip.user.dao.CashBankDAO;
import com.bdbvip.user.service.CashBankService;
import com.bdbvip.utils.Constants;
import com.bdbvip.utils.common.dao.support.Page;
import com.bdbvip.utils.common.exception.ServiceException;

@Service("cashBankService")
public class CashBankServiceImpl implements CashBankService {

	private final Logger logger=Logger.getLogger(CashBankServiceImpl.class);
	
	
	@Autowired
	CashBankDAO cashBankDao;
	
	// 获取用户银行列表
	public Map<String, Object> findBankList(String userId, Page page) throws ServiceException {
		Map<String, Object> result = new TreeMap<String, Object>();
		try {
			Page p = cashBankDao.findBankList(Integer.parseInt(userId), page);
			result.put("total", String.valueOf(p.getTotalCount()));
			result.put("page", String.valueOf(p.getCurrentPageNo()));
			result.put("pagesize", String.valueOf(p.getPageSize()));
			result.put("list", JSON.toJSONString(p.getResult()));
			 //result.put("list", p.getResult().toString());
			// System.out.println(p.getResult().toString());
			result.put("status", "0");
			result.put("msg", "");
		} catch (Exception e) {
			result.put("errorcaode", "9999");
			result.put("msg", Constants.getParamterkey("common.syserror"));
			e.printStackTrace();
			logger.info("[Cash.Service.findBankList]=====>" + "添加银行卡失败");
		}

		return result;
	}

	// 重置默认银行卡信息
	public Map<String, Object> doBankDefaultOrNo(String userId, String bankId) throws ServiceException {
		Map<String,Object> result = new TreeMap<String, Object>();
		try {
			if (userId.matches("\\d+") && bankId.matches("\\d+")) {

				int bankCount = cashBankDao.findBankCountByUserId(Integer.parseInt(userId));
				if (bankCount == 1) {
					result.put("status", "60025");
					result.put("msg", Constants.getParamterkey("user.cash.bank.one.error"));
					logger.error("[Cash.Service.setBankDefaultOrNo]=====>"
							+ Constants.getParamterkey("user,cash.bank.one.error"));
					return result;
				}

				// 获取到的默认银行卡号
				TMemBankinfo bankinfo = cashBankDao.findBankByUseridAndDefault(Integer.parseInt(userId));

				// 新默认银行卡
				TMemBankinfo tMemBankinfo = cashBankDao.findBankByUseridAndBankno(Integer.parseInt(userId), bankId);
				if (bankinfo != null && tMemBankinfo != null) {
					List<TMemBankinfo> list = new ArrayList<TMemBankinfo>();
					bankinfo.setFlag("0");
					tMemBankinfo.setFlag("1");
					list.add(bankinfo);
					list.add(tMemBankinfo);
					cashBankDao.updateBankAll(list);
					result.put("status", "0");
					result.put("msg", "交易成功");
					return result;
				}
				result.put("status", "60022");
				result.put("msg", Constants.getParamterkey("user.cash.bank.default.params.error"));
				logger.error("[Cash.Service.setBankDefaultOrNo]=====>"
						+ Constants.getParamterkey("user.cash.bank.default.params.error"));
				return result;

			}
			result.put("status", "60022");
			result.put("msg", Constants.getParamterkey("user.cash.bank.default.params.error"));
			logger.error("[Cash.Service.setBankDefaultOrNo]=====>"
					+ Constants.getParamterkey("user.cash.bank.default.params.error"));
			return result;

		} catch (Exception e) {
			result.put("errorcaode", "9999");
			result.put("msg", Constants.getParamterkey("common.syserror"));
			logger.info("[Cash.Service.setBankDefaultOrNo]=====>" + Constants.getParamterkey("common.syserror"));
			e.printStackTrace();

		}
		return result;
	}

	/**
	 * 添加银行卡
	 */
	public Map<String, Object> saveBankInfo(TMemBankinfo tBankinfo) throws ServiceException {
		Map<String,Object> result = new TreeMap<String, Object>();
		try {
			
			int bankNoCount=cashBankDao.findBankCountByBankNo(tBankinfo.getBankno());
			
			/**
			 * 判断银行卡是否已经被添加
			 */
			if (bankNoCount!=0) {
				result.put("status", "60027");
				result.put("msg", Constants.getParamterkey("user.cash.bank.add.repeat"));
				return result;
			}
			
			int bankCount = cashBankDao.findBankCountByUserId(tBankinfo.getUserid());
			
			/**
			 * 判断初次添加银行卡
			 */
			if (bankCount < 1) {
				tBankinfo.setFlag("1");
				cashBankDao.saveBankInfo(tBankinfo);
				result.put("status", "0");
				result.put("msg", "交易成功");
				return result;
			}

			/**
			 * 如果 添加 银行卡标识 1, 修改默认银行卡号信息 0, 然后添加 银行卡信息
			 */
			if ("1".equals(tBankinfo.getFlag())) {
				TMemBankinfo bankinfo = cashBankDao.findBankByUseridAndDefault(tBankinfo.getUserid());
				bankinfo.setFlag("0");
				cashBankDao.updateBank(bankinfo);
				
				cashBankDao.saveBankInfo(tBankinfo);
				result.put("status", "0");
				result.put("msg", "交易成功");
				return result;
			} else if ("0".equals(tBankinfo.getFlag())) {
				cashBankDao.saveBankInfo(tBankinfo);
				result.put("status", "0");
				result.put("msg", "交易成功");
				return result;
			} else {
				result.put("status", "60023");
				result.put("msg", Constants.getParamterkey("user.cash.bank.defalut.flag.error"));
				logger.error("[Cash.Service.saveBank]=====>"
						+ Constants.getParamterkey("user.cash.bank.defalut.flag.error"));
				return result;
			}

		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", "9999");
			result.put("msg", Constants.getParamterkey("common.syserror"));
			logger.error("[Cash.Service.saveBank]=====>" + Constants.getParamterkey("user.cash.bank.add.error"));
		}
		return result;
	}

	// 删除银行卡
	public Map<String, Object> deleteBank(String userId, String bankId) throws ServiceException {
		Map<String,Object> result = new TreeMap<String, Object>();
		try {
			if (userId.matches("\\d+") && bankId.matches("\\d+")) {
				TMemBankinfo tBankinfo = cashBankDao.findBankByUseridAndBankno(Integer.parseInt(userId), bankId);
				if (tBankinfo != null) {
					// 删除银行账户
					cashBankDao.deleteBank(tBankinfo);
					// 获取银行账号的
					List<TMemBankinfo> list = cashBankDao.findBanKAllByUserId(Integer.parseInt(userId));
					if (list.size() > 0) {
						TMemBankinfo t = list.get(0);
						t.setFlag("1");
						cashBankDao.updateBank(t);
					}
					result.put("status", "0");
					result.put("msg", "交易成功");
					return result;
				}
				result.put("status", "60026");
				result.put("msg", Constants.getParamterkey("user.cash.bank.delete.params.error"));
				logger.error("[Cash.Service.deleteBank]=====>"
						+ Constants.getParamterkey("user.cash.bank.delete.params.error"));
				return result;

			}
			result.put("status", "60026");
			result.put("msg", Constants.getParamterkey("user.cash.bank.delete.params.error"));
			logger.error(
					"[Cash.Service.deleteBank]=====>" + Constants.getParamterkey("user.cash.bank.delete.params.error"));
		} catch (Exception e) {
			result.put("errorcaode", "9999");
			result.put("msg", Constants.getParamterkey("common.syserror"));
			logger.error(
					"[Cash.Service.deleteBank]=====>" + Constants.getParamterkey("user.cash.bank.delete.params.error"));
			e.printStackTrace();

		}
		return result;
	}

}
