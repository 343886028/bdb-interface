package com.bdbvip.product.interfaces;

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
import com.bdbvip.pojo.SaleSubscribeVo;
import com.bdbvip.product.interfaces.vo.SaleAuctionRegistVo;
import com.bdbvip.product.interfaces.vo.SaleTMemBiddingVo;
import com.bdbvip.product.interfaces.vo.SaleTProdSaleListVo;
import com.bdbvip.product.service.AuctionService;
import com.bdbvip.product.service.SellingService;
import com.bdbvip.utils.Constants;
import com.bdbvip.utils.common.BaseAction;

/**
 * 拍卖相关接口controller
 * 
 * @ClassName: AuctionFace
 * @Description: TODO
 * @date 2015年12月3日 下午4:03:16
 */

@Controller
@RequestMapping("/interface/auction")
public class AuctionFace extends BaseAction {

	private final static Logger logger = Logger.getLogger(ProductFace.class);

	@Autowired
	SellingService sellingService;
	
	@Autowired
	AuctionService auctionService;

	
	
	/**
	 * 获取拍卖品详情接口
	  * @Title: detail
	  * @param request
	  * @param  response
	  * @return String    
	  * @throws
	 */
	@RequestMapping("/detial.shtml")
	@ResponseBody
	public Object detail(HttpServletRequest request, HttpServletResponse response) {
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
				Map<String, String> params = (Map<String, String>) JSON.parse(requestParams.get("data"));

				if (StringUtils.isBlank(params.get("procode")) && params.get("procode").toString().length() != 20) {
					result.put("status","7000" );
					result.put("msg", Constants.getParamterkey("user.sale.procode.error"));
					String rkey = super.md5str2(result);
					result.put("key", rkey);
					return callback2(requestParams.get("callback"), result, request, response);
				}
				//业务逻辑执行
			    result.putAll(sellingService.dofindSaleProdListByProcode(params.get("procode").toString()));
				String rkey = super.md5str2(result);
				result.put("key", rkey);
				return callback2(requestParams.get("callback"), result, request, response);

			}
			String [] msgStrings=resultFlag.split("#");
			result.put("status", msgStrings[0]);
			result.put("msg", msgStrings[1]);
			String rkey = super.md5str2(result);
			result.put("key", rkey);

		} catch (Exception e) {
			result.put("status", "9999");
			result.put("msg", Constants.getParamterkey("common.syserror"));
			String rkey = super.md5str2(result);
			result.put("key", rkey);
			e.printStackTrace();
		}

		return callback2(null, result, request, response);

	}
	
	/**
	 * 拍品订阅/取消订阅
	  * @Title: subcribe
	  * @param request
	  * @param  response
	  * @param @return 
	  * @return String    
	  * @throws
	 */
	@RequestMapping("/subcribe.shtml")
	@ResponseBody
	public Object subcribe(HttpServletRequest request, HttpServletResponse response) {
		final Map<String, String> requestParams = super.parseRequest(request);
		Map<String, Object> result = new TreeMap<String, Object>();
		try {

			String resultFlag = super.validparameters(requestParams.get("data"), requestParams.get("partnerid"),
					requestParams.get("time"), requestParams.get("service"), requestParams.get("version"),
					requestParams.get("key"));
			result.put("partnerid", requestParams.get("partnerid"));

			SaleSubscribeVo sub = JSON.parseObject(requestParams.get("data"), SaleSubscribeVo.class);

			if (resultFlag.startsWith("0#")) {
				String token=sub.getToken();
				if (StringUtils.isBlank(token)) {
					// token 异常 处理逻辑
					result.put("status", "9010");
					result.put("msg", Constants.getParamterkey("common.login.token"));
					String rkey = super.md5str2(result);
					result.put("key", rkey);
					return callback2(requestParams.get("callback"), result, request, response);
				}
				// 拍品编号验证
				if (sub.getProcode().length() != 20) {
					result.put("status", "7000");
					result.put("key", Constants.getParamterkey("sale.procode.error"));
					String rkey = super.md5str2(result);
					result.put("key", rkey);
					return callback2(requestParams.get("callback"), result, request, response);
				}
				
				//=========
				sub.setUserid(Integer.parseInt(super.getUserid(token)));;
				sub.setSubflag(SaleSubscribeVo.SUBSCRIBE_TRUE);
				
				//业务逻辑执行
				result.putAll(sellingService.doSubscribe(sub));
				
				String rkey = super.md5str2(result);
				result.put("key", rkey);
				return callback2(requestParams.get("callback"), result, request, response);
			}
			result.put("status", resultFlag.split("#")[0]);
			result.put("msg", resultFlag.split("#")[1]);
			String rkey = super.md5str2(result);
			result.put("key", rkey);
			return callback2(null, result, request, response);

		} catch (Exception e) {
			result.put("status", "9999");
			result.put("msg", Constants.getParamterkey("common.syserror"));
			String rkey = super.md5str2(result);
			result.put("key", rkey);
			e.printStackTrace();
		}

		return callback2(requestParams.get("callback"), result, request, response);
	}
	
	
	/**
	 * 拍卖纪录查询
	 * @param request
	 * @param response
	 * @return
	 */
     @RequestMapping("/record.shtml")
     @ResponseBody
	public Object auctionRecords(HttpServletRequest request, HttpServletResponse response) {
		final Map<String, String> requestParams = super.parseRequest(request);
		Map<String, Object> result = new TreeMap<String, Object>();
		try {
			String resultFlag = super.validparameters(requestParams.get("data"), requestParams.get("partnerid"),
					requestParams.get("time"), requestParams.get("service"), requestParams.get("version"),
					requestParams.get("key"));
			result.put("partnerid", requestParams.get("partnerid"));

			SaleTMemBiddingVo sBiddingVo = JSON.parseObject(requestParams.get("data"), SaleTMemBiddingVo.class);
			if (resultFlag.startsWith("0#")) {
				if (StringUtils.isBlank(sBiddingVo.getToken())) {
					// token 异常 处理逻辑
					result.put("status", "9010");
					result.put("msg", Constants.getParamterkey("common.login.token"));
					String rkey = super.md5str2(result);
					result.put("key", rkey);
					return callback2(requestParams.get("callback"), result, request, response);
				}

				if (sBiddingVo.getProcode().length() != 20) {
					result.put("status", "7000");
					result.put("key", Constants.getParamterkey("7000|"));
					String rkey = super.md5str2(result);
					result.put("key", rkey);
					// 拍品编号异常处理逻辑
					return callback2(requestParams.get("callback"), result, request, response);
				}

				// 执行业务逻辑   获取 商品进价列表
				result.putAll(auctionService.listSaleBidding(sBiddingVo));
				String rkey = super.md5str2(result);
				result.put("key", rkey);
				return callback2(requestParams.get("callback"), result, request, response);

			}
			result.put("status", resultFlag.split("#")[0]);
			result.put("msg", resultFlag.split("#")[1]);
			String rkey = super.md5str2(result);
			result.put("key", rkey);
			return callback2(null, result, request, response);
		} catch (Exception e) {
			result.put("status", "9999");
			result.put("msg", Constants.getParamterkey("common.syserror"));
			String rkey = super.md5str2(result);
			result.put("key", rkey);
			e.printStackTrace();
		}
		return callback2(requestParams.get("callback"), result, request, response);
	}
	
	/**
	 * 拍品竞价
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/doauction.shtml")
	@ResponseBody
	public Object auctionBidding(HttpServletRequest request,HttpServletResponse response){
		final Map<String, String> requestParams = super.parseRequest(request);
		Map<String, Object> result = new TreeMap<String, Object>();
		try {
			String resultFlag = super.validparameters(requestParams.get("data"), requestParams.get("partnerid"),
					requestParams.get("time"), requestParams.get("service"), requestParams.get("version"),
					requestParams.get("key"));
			result.put("partnerid", requestParams.get("partnerid"));
			//获取参数 封装
			SaleTMemBiddingVo bidding = JSON.parseObject(requestParams.get("data"),SaleTMemBiddingVo.class);
			if (resultFlag.startsWith("0#")) {
				if (StringUtils.isBlank(bidding.getToken())) {

					// token 异常 处理逻辑
					result.put("status", "9010");
					result.put("msg", Constants.getParamterkey("common.login.token"));
					String rkey = super.md5str2(result);
					result.put("key", rkey);
					return callback2(null, result, request, response);
				}
				
				if (bidding.getProcode().length()!=20) {
					
					//拍品编号异常处理逻辑
					return callback2(null, result, request, response);
				}
				bidding.setActiveid(1);
				bidding.setUserid(1);
				bidding.setCreatetime(new Date());
				auctionService.doAuctionSaleBidding(bidding);
				
				return callback2(null, result, request, response);
			}
			String [] msgStrings=resultFlag.split("#");
			result.put("status", msgStrings[0]);
			result.put("msg", msgStrings[1]);
			String rkey = super.md5str2(result);
			result.put("key", rkey);
			return callback2(null, result, request, response);
         
			
			
		} catch (Exception e) {
			result.put("status", "9999");
			result.put("msg", Constants.getParamterkey("common.syserror"));
			String rkey = super.md5str2(result);
			result.put("key", rkey);
			e.printStackTrace();
		}
		
		return callback2(requestParams.get("callback"), result, request, response);
	}
	
	/**
	 * 拍品查询接口
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/query.shtml")
	@ResponseBody
	public Object querySaleList(HttpServletRequest request, HttpServletResponse response) {
		final Map<String, String> requestParams = super.parseRequest(request);
		Map<String, Object> result = new TreeMap<String, Object>();
		try {
			String resultFlag = super.validparameters(requestParams.get("data"), requestParams.get("partnerid"),
					requestParams.get("time"), requestParams.get("service"), requestParams.get("version"),
					requestParams.get("key"));
			result.put("partnerid", requestParams.get("partnerid"));
			SaleTProdSaleListVo saleListVo = JSON.parseObject(requestParams.get("data"), SaleTProdSaleListVo.class);
			if (resultFlag.startsWith("0#")) {

				if (StringUtils.isBlank(saleListVo.getToken())) {
					// token 异常 处理逻辑
					result.put("status", "9010");
					result.put("msg", Constants.getParamterkey("common.login.token"));
					String rkey = super.md5str2(result);
					result.put("key", rkey);
					return callback2(null, result, request, response);
				}

				// 业务逻辑执行 返回结果添加 result
				result.putAll(sellingService.listTProdSaleList(saleListVo));
				String rkey = super.md5str2(result);
				result.put("key", rkey);
				return callback2(null, result, request, response);
			}
			String[] msgStrings = resultFlag.split("#");
			result.put("status", msgStrings[0]);
			result.put("msg", msgStrings[1]);
			String rkey = super.md5str2(result);
			result.put("key", rkey);
			return callback2(null, result, request, response);

		} catch (Exception e) {
			result.put("status", "9999");
			result.put("msg", Constants.getParamterkey("common.syserror"));
			String rkey = super.md5str2(result);
			result.put("key", rkey);
			e.printStackTrace();
		}
		return callback2(null, result, request, response);
		
	}
	
	
	/**
	 * 拍品报名
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/register.shtml")
	@ResponseBody
	public Object registAuction(HttpServletRequest request, HttpServletResponse response) {
		final Map<String, String> requestParams = super.parseRequest(request);
		Map<String, Object> result = new TreeMap<String, Object>();
		try {
			String resultFlag = super.validparameters(requestParams.get("data"), requestParams.get("partnerid"),
					requestParams.get("time"), requestParams.get("service"), requestParams.get("version"),
					requestParams.get("key"));
			result.put("partnerid", requestParams.get("partnerid"));
			SaleAuctionRegistVo registVo = JSON.parseObject(requestParams.get("data"), SaleAuctionRegistVo.class);
			if (resultFlag.startsWith("0#")) {

				if (StringUtils.isBlank(registVo.getToken())) {
					// token 异常 处理逻辑
					result.put("status", "9010");
					result.put("msg", Constants.getParamterkey("common.login.token"));
					String rkey = super.md5str2(result);
					result.put("key", rkey);
				}
				if (StringUtils.isBlank(registVo.getPaypwd())) {
					// 执行返回
					String rkey = super.md5str2(result);
					result.put("key", rkey);
					return callback2(null, result, request, response);
				}
				if (StringUtils.isBlank(registVo.getProcode())) {

					// 执行返回
					String rkey = super.md5str2(result);
					result.put("key", rkey);
					return callback2(null, result, request, response);
				}
				
				//String token = registVo.getToken();
				//registVo.setUserid(Integer.parseInt(super.getUserid(token)));
				registVo.setUserid(2);

				// 业务逻辑执行操作
				result.putAll(auctionService.doAuctionRegist(registVo));
				String rkey = super.md5str2(result);
				result.put("key", rkey);
				return callback2(null, result, request, response);

			}
			String[] msgStrings = resultFlag.split("#");
			result.put("status", msgStrings[0]);
			result.put("msg", msgStrings[1]);
			String rkey = super.md5str2(result);
			result.put("key", rkey);
			return callback2(null, result, request, response);

		} catch (Exception e) {
			result.put("status", "9999");
			result.put("msg", Constants.getParamterkey("common.syserror"));
			String rkey = super.md5str2(result);
			result.put("key", rkey);
			e.printStackTrace();
		}
		return callback2(null, result, request, response);
	}
}
