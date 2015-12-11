package com.bdbvip.shop.interfaces;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.bdbvip.entity.TProdActive;
import com.bdbvip.pojo.ActivityVo;
import com.bdbvip.shop.service.ActivityService;
import com.bdbvip.utils.Constants;
import com.bdbvip.utils.common.BaseAction;
import com.bdbvip.utils.common.dao.support.Page;
import com.bdbvip.utils.common.date.DateUtil;

@Controller
@RequestMapping("/interface/shop/activity")
public class ShopActivityFace extends BaseAction {
	
	@Autowired
	ActivityService activityService;
	
	/**
	 * 店铺活动查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/query.shtml")
	@ResponseBody
	public Object query(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultmap = new TreeMap<String, Object>();

		// 解析request 获取参数 data,partnerid,service,time,version,key
		final Map<String,String> requestParams = super.parseRequest(request);
		// 验证参数是否合法
		try{
			String resultstr = super.validparameters(requestParams.get("data"), requestParams.get("partnerid"), requestParams.get("time"), requestParams.get("service"), requestParams.get("version"),
					requestParams.get("key"));
			if(resultstr.startsWith("0#")){
				//传入参数验证
				boolean passflag = true;
				ActivityVo vo = JSON.parseObject(requestParams.get("data"), ActivityVo.class);
				//token校验
				if(passflag && "".equals(vo.getToken())){
					resultmap.put("status", "9009");
					resultmap.put("msg", Constants.getParamterkey("common.param.tokenInvalid"));
					passflag = false;
				}
				//service校验
				if(passflag && !"shop".equalsIgnoreCase(requestParams.get("service"))){
					resultmap.put("status","9007");
					resultmap.put("msg",Constants.getParamterkey("common.param.service"));
					passflag = false;
				}
				if(StringUtils.isBlank("page")||StringUtils.isBlank("pagesize")){
					resultmap.put("status","9013");
					resultmap.put("msg",Constants.getParamterkey("common.param.isnull"));
					passflag = false;
				}
				//通过校验,进行业务处理
				if(passflag){
					Page resultPage = activityService.listShopActivity(vo);
					resultmap.put("data", resultPage.getResult());
					resultmap.put("partnerid", requestParams.get("partnerid"));
					resultmap.put("total",resultPage.getTotalCount());
					resultmap.put("page", resultPage.getCurrentPageNo());
					resultmap.put("pagesize", resultPage.getPageSize());
					resultmap.put("msg", "成功");
					resultmap.put("status", "0");
					
					String rkey  = super.md5str2(resultmap);
					resultmap.put("key",rkey);
				}
			}
		}catch(Exception e){
			resultmap.put("status","9999");
			resultmap.put("msg",Constants.getParamterkey("common.syserror"));
		}
		return callback2(requestParams.get("callback"),resultmap,request,response);
	}
	
	/**
	 * 店铺活动审核状态查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/queryaduit.shtml")
	@ResponseBody
	public Object queryAduit(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultmap = new TreeMap<String, Object>();

		// 解析request 获取参数 data,partnerid,service,time,version,key
		final Map<String,String> requestParams = super.parseRequest(request);
		// 验证参数是否合法
		try{
			String resultstr = super.validparameters(requestParams.get("data"), requestParams.get("partnerid"), requestParams.get("time"), requestParams.get("service"), requestParams.get("version"),
					requestParams.get("key"));
			if(resultstr.startsWith("0#")){
				//传入参数验证
				boolean passflag = true;
				ActivityVo vo = JSON.parseObject(requestParams.get("data"), ActivityVo.class);
				if(passflag && !"activity".equalsIgnoreCase(requestParams.get("service"))){
					resultmap.put("status","9007");
					resultmap.put("msg",Constants.getParamterkey("common.param.service"));
					passflag = false;
				}
				if(passflag && vo.getId()==null){
					resultmap.put("status","8001");
					resultmap.put("msg",Constants.getParamterkey("shop.activity.activityid.null"));
					passflag = false;
				}
				//通过校验,进行业务处理
				if(passflag){
					TProdActive active = activityService.getActiveById(vo.getId());
					if(active==null){
						resultmap.put("status","8005");
						resultmap.put("msg",Constants.getParamterkey("shop.activity.nonexistent"));
					}else{
						resultmap.put("parnterid", requestParams.get("partnerid"));
						resultmap.put("status", "0");
						resultmap.put("msg", active.getRemark());
						resultmap.put("auditstatus", active.getAduitflag());//默认0 未审 1 已通过 2未通过
						
						String rkey  = super.md5str2(resultmap);
						resultmap.put("key",rkey);
					}
				}
			}
		}catch(Exception e){
			resultmap.put("status","9999");
			resultmap.put("msg",Constants.getParamterkey("common.syserror"));
		}
		return callback2(requestParams.get("callback"),resultmap,request,response);
	}
	
	/**
	 * 店铺发起活动
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/create.shtml")
	@ResponseBody
	public Object create(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultmap = new TreeMap<String, Object>();

		// 解析request 获取参数 data,partnerid,service,time,version,key
		final Map<String,String> requestParams = super.parseRequest(request);
		// 验证参数是否合法
		try{
			String resultstr = super.validparameters(requestParams.get("data"), requestParams.get("partnerid"), requestParams.get("time"), requestParams.get("service"), requestParams.get("version"),
					requestParams.get("key"));
			if(resultstr.startsWith("0#")){
				//传入参数验证
				boolean passflag = true;
				Map<String, String> params = (Map<String, String>) JSON.parse(requestParams.get("data"));
				if(passflag && !"activity".equalsIgnoreCase(requestParams.get("service"))){
					resultmap.put("status","9007");
					resultmap.put("msg",Constants.getParamterkey("common.param.service"));
					passflag = false;
				}
				if(passflag && "".equals(params.get("token"))){
					resultmap.put("status", "9009");
					resultmap.put("msg", Constants.getParamterkey("common.param.tokenInvalid"));
					passflag = false;
				}
				if(passflag && !super.isLogin(params.get("token"))){
					resultmap.put("status", "9010");
					resultmap.put("msg", Constants.getParamterkey("common.login.token"));
					passflag = false;
				}
				//通过校验,进行业务处理
				if(passflag){
					//判断是否是商家
					TProdActive active = new TProdActive();
					active.setActivename(params.get("activename"));
					active.setStarttime(DateUtil.string2Date(params.get("begtime"),DateUtil.simpleDateFormat5));
					active.setEndtime(DateUtil.string2Date(params.get("endtime"),DateUtil.simpleDateFormat5));
					active.setDescs(params.get("desc"));
					active.setJoinmoney(new BigDecimal(params.get("joinmoney")));
					active.setType("1");//默认0平台 1店铺
					active.setAduitflag("0");//默认0 未审 1 已通过 2未通过
					active.setStatus("1");//默认1有效 0无效 
					active.setPrice(new BigDecimal(params.get("price")));
					active.setFrousermoney(new BigDecimal(params.get("frousermoney")));
					active.setOptuser(Integer.valueOf(params.get("optuser")));
					active.setRemark(params.get("remark"));
					active.setAddprice(new BigDecimal(params.get("addprice")));
					active.setFrouser(Integer.valueOf(super.getUserid(params.get("token"))));
					activityService.saveTProdActive(active);
					
					resultmap.put("partnerid", requestParams.get("partnerid"));
					resultmap.put("status", "0");
					resultmap.put("msg","成功");
					String rkey  = super.md5str2(resultmap);
					resultmap.put("key",rkey);
				}
			}
		}catch(Exception e){
			resultmap.put("status","9999");
			resultmap.put("msg",Constants.getParamterkey("common.syserror"));
		}
		return callback2(requestParams.get("callback"),resultmap,request,response);
	}
	
	/**
	 * 店铺活动修改
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/update.shtml")
	@ResponseBody
	public Object update(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultmap = new TreeMap<String, Object>();

		// 解析request 获取参数 data,partnerid,service,time,version,key
		final Map<String,String> requestParams = super.parseRequest(request);
		// 验证参数是否合法
		try{
			String resultstr = super.validparameters(requestParams.get("data"), requestParams.get("partnerid"), requestParams.get("time"), requestParams.get("service"), requestParams.get("version"),
					requestParams.get("key"));
			if(resultstr.startsWith("0#")){
				//传入参数验证
				boolean passflag = true;
				Map<String, String> params = (Map<String, String>) JSON.parse(requestParams.get("data"));
				if(passflag && !"activity".equalsIgnoreCase(requestParams.get("service"))){
					resultmap.put("status","9007");
					resultmap.put("msg",Constants.getParamterkey("common.param.service"));
					passflag = false;
				}
				if(passflag && "".equals(params.get("token"))){
					resultmap.put("status", "9009");
					resultmap.put("msg", Constants.getParamterkey("common.param.tokenInvalid"));
					passflag = false;
				}
				if(passflag && !super.isLogin(params.get("token"))){
					resultmap.put("status", "9010");
					resultmap.put("msg", Constants.getParamterkey("common.login.token"));
					passflag = false;
				}
				//通过校验,进行业务处理
				if(passflag){
					activityService.updateTProdActive(params);
					resultmap.put("partnerid", requestParams.get("partnerid"));
					resultmap.put("status", "0");
					String rkey  = super.md5str2(resultmap);
					resultmap.put("key",rkey);
				}
			}
		}catch(Exception e){
			resultmap.put("status","9999");
			resultmap.put("msg",Constants.getParamterkey("common.syserror"));
		}
		return callback2(requestParams.get("callback"),resultmap,request,response);
	}
}
