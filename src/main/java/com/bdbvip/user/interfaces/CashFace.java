package com.bdbvip.user.interfaces;


import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.bdbvip.entity.TMemBankinfo;
import com.bdbvip.entity.TMemDrawRecord;
import com.bdbvip.entity.TMemPass;
import com.bdbvip.entity.TMemPayRecord;
import com.bdbvip.pojo.CashTradeVo;
import com.bdbvip.user.service.CashBankService;
import com.bdbvip.user.service.CashService;
import com.bdbvip.user.service.CashTradingService;
import com.bdbvip.utils.Constants;
import com.bdbvip.utils.DateUtils;
import com.bdbvip.utils.TradUtil;
import com.bdbvip.utils.common.BaseAction;
import com.bdbvip.utils.common.dao.support.Page;

@Controller
@RequestMapping("/interface/cash")
public class CashFace extends BaseAction {
	
	
	private final static Logger logger = Logger.getLogger(CashFace.class);
	
	@Autowired
	CashService cashService;
	
	@Autowired
	CashBankService cashBankService;
	
	@Autowired
	CashTradingService  cashTradingService;
	
	/**
	 *  验证支付/提款密码设置与否
	  * @Title: isQueryPwd
	  * @param  request
	  * @param  response
	  * @return Map<String,String>    
	  * @throws
	 */
	
	@RequestMapping("/querypwd.shtml")
	@ResponseBody
	public Object isQueryPwd(HttpServletRequest request, HttpServletResponse response) {
		final Map<String, String> requestParams = super.parseRequest(request);
		Map<String, Object> result = new TreeMap<String, Object>();
		try {
			// 验证参数是否正确
			String resultFlag = super.validparameters(requestParams.get("data"), requestParams.get("partnerid"),
					requestParams.get("time"), requestParams.get("service"), requestParams.get("version"),
					requestParams.get("key"));
			result.put("partnerid", requestParams.get("partnerid"));
			// 参数以0#开头执行 验证通过
			if (resultFlag.startsWith("0#")) {
				@SuppressWarnings("unchecked")
				Map<String, Object> params = (Map<String, Object>) JSON.parse(requestParams.get("data"));

				// 判断data 中是否包含token
				if (StringUtils.isBlank(params.get("token").toString())) {
					result.put("status", "9010");
					result.put("msg", Constants.getParamterkey("common.login.token"));
					String rkey = super.md5str2(result);
					result.put("key", rkey);
					return callback2(requestParams.get("callback2"), result, request, response);
				}
				// 业务逻辑处理
				result.putAll(cashService.doPwdisExist(super.getUserid(params.get("token").toString())));
			    //result.putAll(cashService.doPwdisExist("5"));
				result.put("partnerid", requestParams.get("partnerid"));
				String rkey = super.md5str2(result);
				result.put("key", rkey);
				return callback2(requestParams.get("callback2"), result, request, response);

			}
			logger.info("[Cash.Face.isQueryPwd]====>" + resultFlag);
			result.put("status", resultFlag.split("#")[0]);
			result.put("msg", resultFlag.split("#")[1]);
			String rkey = super.md5str2(result);
			result.put("key", rkey);
			return callback2(requestParams.get("callback2"), result, request, response);
		} catch (Exception e) {
			result.put("status", "9999");
			result.put("msg", Constants.getParamterkey("common.syserror"));
			String rkey = super.md5str2(result);
			result.put("key", rkey);
			logger.error("[Cash.Face.isQueryPwd]====>" + Constants.getParamterkey("common.syserror"));
			e.printStackTrace();
		}
		return callback2(requestParams.get("callback2"), result, request, response);
	}
	
	/**
	 * 设置支付/提款密码
	  * @Title: setPwd
	  * @param request
	  * @param response
	  * @return Map<String,String>    
	  * @throws
	 */
	
	@RequestMapping("/setpwd.shtml")
	@ResponseBody
	public Object setPwd(HttpServletRequest request, HttpServletResponse response) {
		final Map<String, String> requestParams = super.parseRequest(request);
		Map<String, Object> result = new TreeMap<String, Object>();
		try {
			// 验证参数是否正确
			String resultFlag = super.validparameters(requestParams.get("data"), requestParams.get("partnerid"),
					requestParams.get("time"), requestParams.get("service"), requestParams.get("version"),
					requestParams.get("key"));
			result.put("partnerid", requestParams.get("partnerid"));
			// 参数以0#开头执行 验证通过
			if (resultFlag.startsWith("0#")) {
				@SuppressWarnings("unchecked")
				Map<String, Object> params = (Map<String, Object>) JSON.parse(requestParams.get("data"));

				// 判断data 中是否包含paypwd和null 判断
				if (StringUtils.isBlank(params.get("paypwd").toString())) {

					result.put("status", "60017");
					result.put("msg", Constants.getParamterkey("user.cash.paypwd.params.error"));
					String rkey = super.md5str2(result);
					result.put("key", rkey);
					logger.error("[Cash.Face.setPwd]====>" + Constants.getParamterkey("user.cash.paypwd.params.error"));
					return callback2(requestParams.get("callback2"), result, request, response);

				}
				// 判断data 中是否包含token和null 判断
				if (StringUtils.isBlank(params.get("token").toString())) {
					result.put("status", "9010");
					result.put("msg", Constants.getParamterkey("common.login.token"));
					String rkey = super.md5str2(result);
					result.put("key", rkey);
					return callback2(requestParams.get("callback2"), result, request, response);
				}

				// 业务逻辑处理
				TMemPass tMemPass = new TMemPass();
				//tMemPass.setUserid(10);

				tMemPass.setUserid(Integer.parseInt(super.getUserid(params.get("token").toString())));
				tMemPass.setPaypass(params.get("paypwd").toString().toLowerCase());

				// 预留 提款密码和客户端密码
				tMemPass.setDrawpass(params.get("paypwd").toString().toLowerCase());
				tMemPass.setClientpass(params.get("paypwd").toString().toLowerCase());

				result.putAll(cashService.createPayPwd(tMemPass));
				String rkey = super.md5str2(result);
				result.put("key", rkey);
				return callback2(requestParams.get("callback2"), result, request, response);

			}
			logger.info(resultFlag);
			result.put("status", resultFlag.split("#")[0]);
			result.put("msg", resultFlag.split("#")[1]);
			String rkey = super.md5str2(result);
			result.put("key", rkey);
			return callback2(requestParams.get("callback2"), result, request, response);
		} catch (Exception e) {
			result.put("status", "9999");
			result.put("msg", Constants.getParamterkey("common.syserror"));
			String rkey = super.md5str2(result);
			result.put("key", rkey);
			logger.error("[Cash.Face.setPwd]====>" + Constants.getParamterkey("common.syserror"));
			e.printStackTrace();
		}
		return callback2(requestParams.get("callback2"), result, request, response);
	}
	
	/**
	 * 修改提款和支付密码
	  * @Title: updatePwd
	  * @param  request
	  * @param  response
	  * @return Map<String,String>    
	  * @throws
	 */
	
	@RequestMapping("/updatepwd.shtml")
	@ResponseBody
	public  Object updatePwd(HttpServletRequest request, HttpServletResponse response) {
		final Map<String, String> requestParams = super.parseRequest(request);
		Map<String, Object> result = new TreeMap<String, Object>();
		try {
			// 验证参数是否正确
			String resultFlag = super.validparameters(requestParams.get("data"), requestParams.get("partnerid"),
					requestParams.get("time"), requestParams.get("service"), requestParams.get("version"),
					requestParams.get("key"));
			result.put("partnerid", requestParams.get("partnerid"));

			// 参数以0#开头执行 验证通过
			if (resultFlag.startsWith("0#")) {
				@SuppressWarnings("unchecked")
				Map<String, Object> params = (Map<String, Object>) JSON.parse(requestParams.get("data"));

				// 判断token
				if (StringUtils.isBlank(params.get("token").toString())) {
					result.put("status", "9010");
					result.put("msg", Constants.getParamterkey("common.login.token"));
					String rkey = super.md5str2(result);
					result.put("key", rkey);
					return callback2(requestParams.get("callback2"), result, request, response);
				}

				// 判断data 中是否包含paypwd和null 判断
				if (StringUtils.isBlank(params.get("paypwd").toString())) {
					result.put("status", "60017");
					result.put("msg", Constants.getParamterkey("user.cash.paypwd.params.error"));
					String rkey = super.md5str2(result);
					result.put("key", rkey);
					logger.error("[Cash.Face.updatePwd]====>" + Constants.getParamterkey("user.cash.paypwd.params.error"));
					return callback2(requestParams.get("callback2"), result, request, response);

				}

				// 判断data 中是否包含oldpaypwd和null 判断
				if (StringUtils.isBlank(params.get("oldpaypwd").toString())) {
					result.put("status", "60016");
					result.put("msg", Constants.getParamterkey("user.cash.oldpass.params.error"));
					String rkey = super.md5str2(result);
					result.put("key", rkey);
					logger.error("[Cash.Face.updatePwd]====>" + Constants.getParamterkey("user.cash.oldpass.params.error"));
					return callback2(requestParams.get("callback2"), result, request, response);

				}

				TMemPass tMemPass = new TMemPass();

				tMemPass.setUserid(Integer.parseInt(super.getUserid(params.get("token").toString())));
				//tMemPass.setUserid(10);

				tMemPass.setPaypass(params.get("paypwd").toString().toLowerCase());

				// 预留 提款密码和客户端密码
				tMemPass.setDrawpass(params.get("paypwd").toString().toLowerCase());
				tMemPass.setClientpass(params.get("paypwd").toString().toLowerCase());

				String oldpaypwd = params.get("oldpaypwd").toString().toLowerCase();
				result.putAll(cashService.updatePwd(oldpaypwd, tMemPass));
				String rkey = super.md5str2(result);
				result.put("key", rkey);
				return callback2(requestParams.get("callback2"), result, request, response);
			}
			logger.info(resultFlag);
			result.put("status", resultFlag.split("#")[0]);
			result.put("msg", resultFlag.split("#")[1]);
			String rkey = super.md5str2(result);
			result.put("key", rkey);
			logger.error("[Cash.Face.updatePwd]====>" + resultFlag.split("#")[0]);
			return callback2(requestParams.get("callback2"), result, request, response);
		} catch (Exception e) {
			result.put("status", "9999");
			result.put("msg", Constants.getParamterkey("common.syserror"));
			String rkey = super.md5str2(result);
			result.put("key", rkey);
			logger.error("[Cash.Face.updatePwd]====>" + Constants.getParamterkey("common.syserror"));
			e.printStackTrace();
		}
		return callback2(requestParams.get("callback2"), result, request, response);
	}
	
	
	/**
	 * 验证提款和支付密码
	  * @Title: validPwd
	  * @param  request
	  * @param  response
	  * @return Map<String,String>    
	  * @throws
	 */
	
	@RequestMapping("/validpwd.shtml")
	@ResponseBody
	public Object validPwd(HttpServletRequest request, HttpServletResponse response) {
		final Map<String, String> requestParams = super.parseRequest(request);
		Map<String, Object> result = new TreeMap<String, Object>();
		try {
			// 验证参数是否正确
			String resultFlag = super.validparameters(requestParams.get("data"), requestParams.get("partnerid"),
					requestParams.get("time"), requestParams.get("service"), requestParams.get("version"),
					requestParams.get("key"));
			result.put("partnerid", requestParams.get("partnerid"));

			// 参数以0#开头执行 验证通过
			if (resultFlag.startsWith("0#")) {
				@SuppressWarnings("unchecked")
				Map<String, Object> params = (Map<String, Object>) JSON.parse(requestParams.get("data"));

				// 判断token
				if (StringUtils.isBlank(params.get("token").toString())) {
					result.put("status", "9010");
					result.put("msg", Constants.getParamterkey("common.login.token"));
					String rkey = super.md5str2(result);
					result.put("key", rkey);
					return callback2(requestParams.get("callback2"), result, request, response);
				}

				// 判断data 中是否包含paypwd和null 判断
				if (StringUtils.isBlank(params.get("paypwd").toString())) {
					result.put("status", "60017");
					result.put("msg", Constants.getParamterkey("user.cash.paypwd.params.error"));
					String rkey = super.md5str2(result);
					result.put("key", rkey);
					logger.error("[Cash.Face.validpwd]====>" + Constants.getParamterkey("user.cash.paypwd.params.error"));
					return callback2(requestParams.get("callback2"), result, request, response);

				}

				// 执行业务逻辑
				TMemPass tMemPass = new TMemPass();

				tMemPass.setUserid(Integer.parseInt(super.getUserid(params.get("token").toString())));
				//tMemPass.setUserid(10);

				tMemPass.setPaypass(params.get("paypwd").toString().toLowerCase());
				result.putAll(cashService.dovalidPwd(tMemPass));
				String rkey = super.md5str2(result);
				result.put("key", rkey);
				return callback2(requestParams.get("callback2"), result, request, response);

			}
			logger.info(resultFlag);
			result.put("status", resultFlag.split("#")[0]);
			result.put("msg", resultFlag.split("#")[1]);
			String rkey = super.md5str2(result);
			result.put("key", rkey);
			logger.error("[Cash.Face.validPwd]====>" + resultFlag.split("#")[0]);
			return callback2(requestParams.get("callback2"), result, request, response);
		} catch (Exception e) {
			result.put("status", "9999");
			result.put("msg", Constants.getParamterkey("common.syserror"));
			String rkey = super.md5str2(result);
			result.put("key", rkey);
			logger.error("[Cash.Face.validPwd]====>" + Constants.getParamterkey("common.syserror"));
			e.printStackTrace();
		}
		return callback2(requestParams.get("callback2"), result, request, response);
	}
	
	
	/**
	 * 账户余额查询接口
	  * @Title: query
	  * @param  request
	  * @param  response
	  * @return Map<String,Object>
	  * @throws
	 */
	@RequestMapping("/query.shtml")
	@ResponseBody
	public Object query(HttpServletRequest request, HttpServletResponse response) {
		final Map<String, String> requestParams = super.parseRequest(request);
		Map<String, Object> result = new TreeMap<String, Object>();
		try {

			// 验证参数是否正确
			String resultFlag = super.validparameters(requestParams.get("data"), requestParams.get("partnerid"),
					requestParams.get("time"), requestParams.get("service"), requestParams.get("version"),
					requestParams.get("key"));
			result.put("partnerid", requestParams.get("partnerid"));

			// 参数以0#开头执行 验证通过
			if (resultFlag.startsWith("0#")) {

				@SuppressWarnings("unchecked")
				Map<String, Object> params = (Map<String, Object>) JSON.parse(requestParams.get("data"));

				// 判断token
				if (StringUtils.isBlank(params.get("token").toString())) {
					result.put("status", "9010");
					result.put("msg", Constants.getParamterkey("common.login.token"));
					String rkey = super.md5str2(result);
					result.put("key", rkey);
					logger.error("[Cash.Face.query]====>" + Constants.getParamterkey("common.login.token"));
					return callback2(requestParams.get("callback2"), result, request, response);
				}

				// 验证通过 执行业务逻辑
				//result.putAll(cashService.findAccountByUserId(super.getUserid(params.get("token").toString())));
				result.putAll(cashService.findAccountByUserId("10"));
				String rkey = super.md5str2(result);
				result.put("key", rkey);
				return callback2(requestParams.get("callback2"), result, request, response);

			}
			result.put("status", resultFlag.split("#")[0]);
			result.put("msg", resultFlag.split("#")[1]);
			String rkey = super.md5str2(result);
			result.put("key", rkey);
			logger.error("[Cash.Face.query]====>" + resultFlag.split("#")[0]);
			return callback2(requestParams.get("callback2"), result, request, response);

		} catch (Exception e) {
			result.put("status", "9999");
			result.put("msg", Constants.getParamterkey("common.syserror"));
			String rkey = super.md5str2(result);
			result.put("key", rkey);
			logger.error("[Cash.Face.query]====>" + Constants.getParamterkey("common.syserror"));
		}
		return callback2(requestParams.get("callback2"), result, request, response);
	}
	
	/**
	 * 交易记录查询
	  * @Title: tradRecords
	  * @param  request
	  * @param  response
	  * @return Map<String,String>    
	  * @throws
	 */
	
	@RequestMapping("/tradrecords.shtml")
	@ResponseBody
	public Object tradRecords(HttpServletRequest request, HttpServletResponse response){
		final Map<String, String> requestParams = super.parseRequest(request);
		Map<String, Object> result = new TreeMap<String, Object>();
		try {
			// 验证参数是否正确
			String resultFlag = super.validparameters(requestParams.get("data"), requestParams.get("partnerid"),
					requestParams.get("time"), requestParams.get("service"), requestParams.get("version"),
					requestParams.get("key"));

			result.put("partnerid", requestParams.get("partnerid"));

			// 判断验证是否通过
			if (resultFlag.startsWith("0#")) {
				@SuppressWarnings("unchecked")
				Map<String, Object> params = (Map<String, Object>) JSON.parse(requestParams.get("data"));
				// 判断data 中是否包含token
				if (StringUtils.isBlank(params.get("token").toString())) {
					result.put("status", "9010");
					result.put("msg", Constants.getParamterkey("common.login.token"));
					String rkey = super.md5str2(result);
					result.put("key", rkey);
					return callback2(requestParams.get("callback2"), result, request, response);
				}
				// 判断data 中是否包含page  页码
				if (StringUtils.isBlank(params.get("page").toString())
						|| (!params.get("page").toString().matches("\\d+"))) {
					result.put("status", "9010");
					result.put("msg", Constants.getParamterkey("common.login.token"));
					String rkey = super.md5str2(result);
					result.put("key", rkey);
					return callback2(requestParams.get("callback2"), result, request, response);
				}
				// 判断data 中是否包含pagesize  每页条数
				if (StringUtils.isBlank(params.get("pagesize").toString())
						|| (!params.get("pagesize").toString().matches("\\d+"))) {
					result.put("status", "9010");
					result.put("msg", Constants.getParamterkey("common.login.token"));
					String rkey = super.md5str2(result);
					result.put("key", rkey);
					return callback2(requestParams.get("callback2"), result, request, response);
				}
				
				CashTradeVo cashTradeVo=new CashTradeVo();
				//账单号和账单类型
				if (params.containsKey("tradeno")&&StringUtils.isBlank(params.get("tradeno").toString())) {
					cashTradeVo.setTradeno(params.get("tradeno").toString());
				}
				if (params.containsKey("tradetype")&&StringUtils.isBlank(params.get("tradetype").toString())) {
					cashTradeVo.setTradetype(params.get("tradetype").toString());
				}
				
				//设置开始时间和结束时间
				if (params.containsKey("btime")&&StringUtils.isBlank(params.get("btime").toString())) {
					cashTradeVo.setStartTime(DateUtils.string2Date(params.get("btime").toString(), DateUtils.simpleDateFormat3));
				}
				if (params.containsKey("etime")&&StringUtils.isBlank(params.get("etime").toString())) {
					cashTradeVo.setEndTime(DateUtils.string2Date(params.get("etime").toString(), DateUtils.simpleDateFormat3));
				}
				
				// 执行业务逻辑
				Page page = new Page();
				page.setCurrentPageNo(Integer.parseInt(params.get("page").toString()));
				page.setPageSize(Integer.parseInt(params.get("pagesize").toString()));
				
				//cashTradeVo.setUserid(Integer.parseInt(super.getUserid(params.get("token").toString())));
				cashTradeVo.setUserid(2);
				
				//执行业务逻辑   返回的结果 添加到result中
				result.putAll(cashService.findTradRecord( cashTradeVo, page));
				
				String rkey = super.md5str2(result);
				result.put("key", rkey);
				return callback2(requestParams.get("callback2"), result, request, response);

			}
			
			//参数验证没有通过
			logger.info("[Cash.BankFace.queryBankList]====>" + resultFlag);
			result.put("status", resultFlag.split("#")[0]);
			result.put("msg", resultFlag.split("#")[1]);
			String rkey = super.md5str2(result);
			result.put("key", rkey);
			return callback2(requestParams.get("callback2"), result, request, response);
		} catch (Exception e) {
			result.put("status", "9999");
			result.put("msg", Constants.getParamterkey("common.syserror"));
			String rkey = super.md5str2(result);
			result.put("key", rkey);
			logger.error(Constants.getParamterkey("common.syserror"));
			e.printStackTrace();
		}
		return callback2(requestParams.get("callback2"), result, request, response);
	}
	
	
	 /**
	  * 发起提现
	  * @Title: draw
	  * @param  request
	  * @param  response
	  * @return Map<String,String>    
	  * @throws
	 */
	@RequestMapping("/draw/add.shtml")
	@ResponseBody
	public Object draw(HttpServletRequest request, HttpServletResponse response) {
		final Map<String, String> requestParams = super.parseRequest(request);
		Map<String, Object> result = new TreeMap<String, Object>();
		try {

			// 验证参数是否正确
			String resultFlag = super.validparameters(requestParams.get("data"), requestParams.get("partnerid"),
					requestParams.get("time"), requestParams.get("service"), requestParams.get("version"),
					requestParams.get("key"));
			result.put("partnerid", requestParams.get("partnerid"));

			// 参数以0#开头执行 验证通过
			if (resultFlag.startsWith("0#")) {

				@SuppressWarnings("unchecked")
				Map<String, Object> params = (Map<String, Object>) JSON.parse(requestParams.get("data"));

				// 判断token
				if (StringUtils.isBlank(params.get("token").toString())) {
					result.put("status", "9010");
					result.put("msg", Constants.getParamterkey("common.login.token"));
					String rkey = super.md5str2(result);
					result.put("key", rkey);
					return callback2(requestParams.get("callback2"), result, request, response);
				}
				
				if (StringUtils.isBlank(params.get("bankno").toString())||!StringUtils.isNumeric(params.get("bankno").toString())) {
					result.put("status", "60028");
					result.put("msg", Constants.getParamterkey("user.cash.bank.params.error"));
					String rkey = super.md5str2(result);
					result.put("key", rkey);
					return callback2(requestParams.get("callback2"), result, request, response);
				}

				if (StringUtils.isBlank(params.get("createtime").toString())) {
					
					return callback2(requestParams.get("callback2"), result, request, response);
				}
				
				if (StringUtils.isBlank(params.get("money").toString())) {
					result.put("status", "600291");
					result.put("msg", Constants.getParamterkey("user.cash.bank.money.erro"));
					String rkey = super.md5str2(result);
					result.put("key", rkey);
					return callback2(requestParams.get("callback2"), result, request, response);
				}
				TMemDrawRecord tRecord=new TMemDrawRecord();
				
				//判断输入金额是否合法 如果合法 进行下面逻辑  如果不合法  返回
				if (TradUtil.isMoney(params.get("money").toString())) {
					tRecord.setDrawmoney(new BigDecimal(params.get("money").toString()));
				} else {
					result.put("status", "600291");
					result.put("msg", Constants.getParamterkey("user.cash.bank.money.error"));
					String rkey = super.md5str2(result);
					result.put("key", rkey);
					return callback2(requestParams.get("callback2"), result, request, response);
				}
				
				//设置短信是否发送短信标识
				if (StringUtils.isNotBlank(params.get("sendflag").toString())&&params.get("sendflag").toString().trim().length()==1) {
                       tRecord.setSendflag(params.get("sendflag").toString());
				}
				
				tRecord.setUserid(2);
				//tRecord.setUserid(Integer.parseInt(super.getUserid(params.get("token").toString())));
				tRecord.setCreatetime(DateUtils.string2Date(params.get("createtime").toString(), DateUtils.simpleDateFormat3));
				tRecord.setBankno(params.get("bankno").toString());
				
				
				// 验证通过 执行业务逻辑
				result.putAll(cashTradingService.saveDrawRecords(tRecord));
				
				//result.putAll(cashService.findAccountByUserId("jiji"));
				
				String rkey = super.md5str2(result);
				result.put("key", rkey);
				return callback2(requestParams.get("callback2"), result, request, response);

			}
			result.put("status", resultFlag.split("#")[0]);
			result.put("msg", resultFlag.split("#")[1]);
			String rkey = super.md5str2(result);
			result.put("key", rkey);
			logger.error("[Cash.Face.query]====>" + resultFlag.split("#")[0]);
			return callback2(requestParams.get("callback2"), result, request, response);

		} catch (Exception e) {
			result.put("status", "9999");
			result.put("msg", Constants.getParamterkey("common.syserror"));
			String rkey = super.md5str2(result);
			result.put("key", rkey);
			logger.error("[Cash.Face.query]====>" + Constants.getParamterkey("common.syserror"));
			e.printStackTrace();
		}
		return callback2(requestParams.get("callback2"), result, request, response);
	}
	
	
	/**
	 * 账户充值
	  * @Title: recharge
	  * @param  request
	  * @param  response
	  * @return Map<String,String>    
	  * @throws
	 */
	@RequestMapping("/pay/recharge.shtml")
	@ResponseBody
	public Object recharge(HttpServletRequest request, HttpServletResponse response) {
		final Map<String, String> requestParams = super.parseRequest(request);
		Map<String, Object> result = new TreeMap<String, Object>();
		try {

			// 验证参数是否正确
			String resultFlag = super.validparameters(requestParams.get("data"), requestParams.get("partnerid"),
					requestParams.get("time"), requestParams.get("service"), requestParams.get("version"),
					requestParams.get("key"));
			result.put("partnerid", requestParams.get("partnerid"));

			// 参数以0#开头执行 验证通过
			if (resultFlag.startsWith("0#")) {

				@SuppressWarnings("unchecked")
				Map<String, Object> params = (Map<String, Object>) JSON.parse(requestParams.get("data"));

				// 判断token
				if (StringUtils.isBlank(params.get("token").toString())) {
					result.put("status", "9010");
					result.put("msg", Constants.getParamterkey("common.login.token"));
					String rkey = super.md5str2(result);
					result.put("key", rkey);
					logger.error("[Cash.Face.recharge]====>" + Constants.getParamterkey("common.login.token"));
					return callback2(requestParams.get("callback2"), result, request, response);
				}
				if (StringUtils.isBlank(params.get("money").toString())) {
					result.put("status", "600291");
					result.put("msg", Constants.getParamterkey("user.cash.bank.money.error"));
					String rkey = super.md5str2(result);
					result.put("key", rkey);
					return callback2(requestParams.get("callback2"), result, request, response);
				}
				
				if (StringUtils.isBlank(params.get("code").toString())) {

					return callback2(requestParams.get("callback2"), result, request, response);
				}
				if (StringUtils.isBlank(params.get("type").toString())) {

					return callback2(requestParams.get("callback2"), result, request, response);
				}
               				
				
				TMemPayRecord payRecord=new TMemPayRecord();
				
				//判断输入金额是否合法 如果合法 进行下面逻辑  如果不合法  返回
				if (TradUtil.isMoney(params.get("money").toString())) {
					payRecord.setPaymoney(new BigDecimal(params.get("money").toString()));
				} else {
					result.put("status", "600291");
					result.put("msg", Constants.getParamterkey("user.cash.bank.money.error"));
					String rkey = super.md5str2(result);
					result.put("key", rkey);
					return callback2(requestParams.get("callback2"), result, request, response);
				}
				
				payRecord.setUserid(2);
				//payRecord.setUserid(Integer.parseInt(super.getUserid(params.get("token").toString())));
				payRecord.setCreatetime(new Date());
				
				//payRecord.setCouponno(params.get("couponno").toString());
				
				payRecord.setPayplatform(params.get("type").toString());
				payRecord.setPaycode(params.get("code").toString());
				
				// 验证通过 执行业务逻辑
				result.putAll(cashTradingService.savePayRecords(payRecord));				
				String rkey = super.md5str2(result);
				result.put("key", rkey);
				return callback2(requestParams.get("callback2"), result, request, response);

			}
			result.put("status", resultFlag.split("#")[0]);
			result.put("msg", resultFlag.split("#")[1]);
			String rkey = super.md5str2(result);
			result.put("key", rkey);
			logger.error("[Cash.Face.query]====>" + resultFlag.split("#")[0]);
			return callback2(requestParams.get("callback2"), result, request, response);

		} catch (Exception e) {
			result.put("status", "9999");
			result.put("msg", Constants.getParamterkey("common.syserror"));
			String rkey = super.md5str2(result);
			result.put("key", rkey);
			logger.error("[Cash.Face.query]====>" + Constants.getParamterkey("common.syserror"));
			e.printStackTrace();
		}
		return callback2(requestParams.get("callback2"), result, request, response);
	}
	
	
	/**
	  * 银行卡查询 
	  * @Title: queryBankList
	  * @param  request
	  * @param  response
	  * @return Map<String,Object> 
	  * @throws
	 */
	
	@RequestMapping("/bank/list.shtml")
	@ResponseBody
	public Object queryBankList(HttpServletRequest request, HttpServletResponse response) {

		final Map<String, String> requestParams = super.parseRequest(request);
		Map<String, Object> result = new TreeMap<String, Object>();
		try {
			// 验证参数是否正确
			String resultFlag = super.validparameters(requestParams.get("data"), requestParams.get("partnerid"),
					requestParams.get("time"), requestParams.get("service"), requestParams.get("version"),
					requestParams.get("key"));

			result.put("partnerid", requestParams.get("partnerid"));

			// 判断验证是否通过
			if (resultFlag.startsWith("0#")) {
				@SuppressWarnings("unchecked")
				Map<String, Object> params = (Map<String, Object>) JSON.parse(requestParams.get("data"));
				// 判断data 中是否包含token
				if (StringUtils.isBlank(params.get("token").toString())) {
					result.put("status", "9010");
					result.put("msg", Constants.getParamterkey("common.login.token"));
					String rkey = super.md5str2(result);
					result.put("key", rkey);
					return callback2(requestParams.get("callback2"), result, request, response);
				}
				// 判断data 中是否包含page
				if (StringUtils.isBlank(params.get("page").toString())
						|| (!params.get("page").toString().matches("\\d+"))) {
					result.put("status", "9010");
					result.put("msg", Constants.getParamterkey("common.login.token"));
					String rkey = super.md5str2(result);
					result.put("key", rkey);
					return callback2(requestParams.get("callback2"), result, request, response);
				}
				// 判断data 中是否包含page
				if (StringUtils.isBlank(params.get("pagesize").toString())
						|| (!params.get("pagesize").toString().matches("\\d+"))) {
					result.put("status", "9010");
					result.put("msg", Constants.getParamterkey("common.login.token"));
					String rkey = super.md5str2(result);
					result.put("key", rkey);
					return callback2(requestParams.get("callback2"), result, request, response);
				}

				// 执行业务逻辑
				Page page = new Page();
				page.setCurrentPageNo(Integer.parseInt(params.get("page").toString()));
				page.setPageSize(Integer.parseInt(params.get("pagesize").toString()));
				
				
				//result.putAll(cashBankService.findBankList("10", page));
				
				// 获取银行卡号列表 添加到返回的result中
				result.putAll(cashBankService.findBankList(super.getUserid(params.get("token").toString()), page));
				String rkey = super.md5str2(result);
				result.put("key", rkey);
				return callback2(requestParams.get("callback2"), result, request, response);

			}
			
			//参数验证没有通过
			logger.info("[Cash.BankFace.queryBankList]====>" + resultFlag);
			result.put("status", resultFlag.split("#")[0]);
			result.put("msg", resultFlag.split("#")[1]);
			String rkey = super.md5str2(result);
			result.put("key", rkey);
			return callback2(requestParams.get("callback2"), result, request, response);
		} catch (Exception e) {
			result.put("status", "9999");
			result.put("msg", Constants.getParamterkey("common.syserror"));
			String rkey = super.md5str2(result);
			result.put("key", rkey);
			logger.error(Constants.getParamterkey("common.syserror"));
			e.printStackTrace();
		}
		return  callback2(requestParams.get("callback2"), result, request, response);
	}
	
	
	/**
	 * 重置默认银行卡号信息
	  * @Title: setBankDefault
	  * @param  request
	  * @param  response
	  * @return Map<String,String>    
	  * @throws
	 */
	
	@RequestMapping("/bank/setdefault.shtml")
	@ResponseBody
	public Object setBankDefault(HttpServletRequest request, HttpServletResponse response) {
		final Map<String, String> requestParams = super.parseRequest(request);
		Map<String, Object> result = new TreeMap<String, Object>();
		try {
			// 验证参数是否正确
			String resultFlag = super.validparameters(requestParams.get("data"), requestParams.get("partnerid"),
					requestParams.get("time"), requestParams.get("service"), requestParams.get("version"),
					requestParams.get("key"));
			result.put("partnerid", requestParams.get("partnerid"));
			// 参数以0#开头执行 验证通过
			if (resultFlag.startsWith("0#")) {
				@SuppressWarnings("unchecked")
				Map<String, Object> params = (Map<String, Object>) JSON.parse(requestParams.get("data"));

				// 判断data 中是否包含token
				if (StringUtils.isBlank(params.get("token").toString())) {
					result.put("status", "9010");
					result.put("msg", Constants.getParamterkey("common.login.token"));
					String rkey = super.md5str2(result);
					result.put("key", rkey);
					return callback2(requestParams.get("callback2"), result, request, response);
				}

				// 银行卡异常
				if (StringUtils.isBlank(params.get("bankno").toString())
						|| !params.get("bankno").toString().matches("\\d+")) {
					result.put("status", "60028");
					result.put("msg", Constants.getParamterkey("user.cash.bank.params.error"));
					String rkey = super.md5str2(result);
					result.put("key", rkey);
					return callback2(requestParams.get("callback2"), result, request, response);
				}

				//result.putAll(cashBankService.doBankDefaultOrNo("2",params.get("bankno").toString()));
				// 执行重置默认银行卡 业务逻辑
				result.putAll(cashBankService.doBankDefaultOrNo(super.getUserid(params.get("token").toString()),params.get("bankno").toString()));
				String rkey = super.md5str2(result);
				result.put("key", rkey);
				return callback2(requestParams.get("callback2"), result, request, response);

			}
			logger.info(resultFlag);
			result.put("status", resultFlag.split("#")[0]);
			result.put("msg", resultFlag.split("#")[1]);
			String rkey = super.md5str2(result);
			result.put("key", rkey);
			return callback2(requestParams.get("callback2"), result, request, response);
		} catch (Exception e) {
			result.put("status", "9999");
			result.put("msg", Constants.getParamterkey("common.syserror"));
			String rkey = super.md5str2(result);
			result.put("key", rkey);
			logger.error(Constants.getParamterkey("common.syserror"));
			e.printStackTrace();
		}
		return callback2(requestParams.get("callback2"), result, request, response);
	}
		
	/**
	 * 添加一张银行卡
	  * @Title: addBank
	  * @param  request
	  * @param  response
	  * @return Map<String,String>    
	  * @throws
	 */
	
	@RequestMapping("/bank/add.shtml")
	@ResponseBody
	public  Object addBank(HttpServletRequest request, HttpServletResponse response) {
		final Map<String, String> requestParams = super.parseRequest(request);
		Map<String, Object> result = new TreeMap<String, Object>();
		try {
			// 验证参数是否正确
			String resultFlag = super.validparameters(requestParams.get("data"), requestParams.get("partnerid"),
					requestParams.get("time"), requestParams.get("service"), requestParams.get("version"),
					requestParams.get("key"));
			result.put("partnerid", requestParams.get("partnerid"));
			// 参数以0#开头执行 验证通过
			if (resultFlag.startsWith("0#")) {
				@SuppressWarnings("unchecked")
				Map<String, Object> params = (Map<String, Object>) JSON.parse(requestParams.get("data"));

				// 格式化json数据

				TMemBankinfo tBankinfo = JSON.parseObject(requestParams.get("data"), TMemBankinfo.class);
				
				// 判断data 中是否包含token
				if (StringUtils.isBlank(params.get("token").toString())) {
					result.put("status", "9010");
					result.put("msg", Constants.getParamterkey("common.login.token"));
					String rkey = super.md5str2(result);
					result.put("key", rkey);
					return callback2(requestParams.get("callback2"), result, request, response);
				}
				//银行卡判断
				if (StringUtils.isBlank(tBankinfo.getBankno())||!tBankinfo.getBankno().matches("\\d+")) {
					result.put("status", "60028");
					result.put("msg", Constants.getParamterkey("user.cash.bank.params.error"));
					String rkey = super.md5str2(result);
					result.put("key", rkey);
					return callback2(requestParams.get("callback2"), result, request, response);
				}
				
				//业务逻辑修改
			    tBankinfo.setUserid(Integer.parseInt(super.getUserid(params.get("token").toString())));
				//tBankinfo.setUserid(3);
				result.putAll(cashBankService.saveBankInfo(tBankinfo));
				result.put("partnerid", requestParams.get("partnerid"));
				String rkey = super.md5str2(result);
				result.put("key", rkey);
				return callback2(requestParams.get("callback2"), result, request, response);
			}
			logger.info(resultFlag);
			result.put("status", resultFlag.split("#")[0]);
			result.put("msg", resultFlag.split("#")[1]);
			String rkey = super.md5str2(result);
			result.put("key", rkey);
			return callback2(requestParams.get("callback2"), result, request, response);
		} catch (Exception e) {
			result.put("status", "9999");
			result.put("msg", Constants.getParamterkey("common.syserror"));
			String rkey = super.md5str2(result);
			result.put("key", rkey);
			logger.error(Constants.getParamterkey("common.syserror"));
			e.printStackTrace();
		}
		return callback2(requestParams.get("callback"), result, request, response);
	}
	
	/**
	 * 删除一张银行卡
	  * @Title: deleteBank
	  * @param  request
	  * @param  response
	  * @return Map<String,String>    
	  * @throws
	 */

	@RequestMapping("/bank/delete.shtml")
	@ResponseBody
	public Object deleteBank(HttpServletRequest request, HttpServletResponse response) {
		final Map<String, String> requestParams = super.parseRequest(request);
		Map<String, Object> result = new TreeMap<String, Object>();
		try {
			// 验证参数是否正确
			String resultFlag = super.validparameters(requestParams.get("data"), requestParams.get("partnerid"),
					requestParams.get("time"), requestParams.get("service"), requestParams.get("version"),
					requestParams.get("key"));
			result.put("partnerid", requestParams.get("partnerid"));
			// 参数以0#开头执行 验证通过
			if (resultFlag.startsWith("0#")) {
				@SuppressWarnings("unchecked")
				Map<String, Object> params = (Map<String, Object>) JSON.parse(requestParams.get("data"));

				// 判断data 中是否包含token

				// 判断data 中是否包含token
				if (StringUtils.isBlank(params.get("token").toString())) {
					result.put("status", "9010");
					result.put("msg", Constants.getParamterkey("common.login.token"));
					String rkey = super.md5str2(result);
					result.put("key", rkey);
					return callback2(requestParams.get("callback2"), result, request, response);
				}

				if (StringUtils.isBlank(params.get("bankno").toString())
						|| !params.get("bankno").toString().matches("\\d+")) {
					result.put("status", "60028");
					result.put("msg", Constants.getParamterkey("user.cash.bank.params.error"));
					String rkey = super.md5str2(result);
					result.put("key", rkey);
					return callback2(requestParams.get("callback2"), result, request, response);
				}

				// 业务逻辑执行
				 //result.putAll(cashBankService.deleteBank("2", params.get("bankno").toString()));
				result.putAll(cashBankService.deleteBank(super.getUserid(params.get("token").toString()),params.get("bankno").toString()));
				String rkey = super.md5str2(result);
				result.put("key", rkey);
				return callback2(requestParams.get("callback2"), result, request, response);

			}
			logger.info(resultFlag);
			result.put("status", resultFlag.split("#")[0]);
			result.put("msg", resultFlag.split("#")[1]);
			String rkey = super.md5str2(result);
			result.put("key", rkey);
			return callback2(requestParams.get("callback2"), result, request, response);
		} catch (Exception e) {
			result.put("status", "9999");
			result.put("msg", Constants.getParamterkey("common.syserror"));
			String rkey = super.md5str2(result);
			result.put("key", rkey);
			logger.error(Constants.getParamterkey("common.syserror"));
			e.printStackTrace();
		}
		return callback2(requestParams.get("callback2"), result, request, response);
	}	
	
	

}
