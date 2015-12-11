package com.bdbvip.shop.interfaces;

import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.bdbvip.shop.interfaces.vo.SubscribeProVo;
import com.bdbvip.shop.interfaces.vo.SubscribeShopVo;
import com.bdbvip.shop.service.ShopService;
import com.bdbvip.utils.Constants;
import com.bdbvip.utils.common.BaseAction;
import com.bdbvip.utils.common.dao.support.Page;

@Controller
@RequestMapping("/interface/shop")
public class ShopFace extends BaseAction {
	
	@Autowired
	ShopService shopService;
	
	/**
	 * 商家销售数据查询
	 * （接口需要返回的数据未知，以后修改）
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/sales.shtml")
	@ResponseBody
	public Object listSales(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultmap = new TreeMap<String, Object>();

		// 解析request 获取参数 data,partnerid,service,time,version,key
		final Map<String,String> requestParams = super.parseRequest(request);
		// 验证参数是否合法
		try{
			String resultstr = super.validparameters(requestParams.get("data"), requestParams.get("partnerid"), requestParams.get("time"), requestParams.get("service"), requestParams.get("version"),
					requestParams.get("key"));
			if(resultstr.startsWith("0#")){
				boolean passflag = true;
				Map<String, String> data = (Map<String, String>) JSON.parse(requestParams.get("data"));
				//token校验
				if(passflag && "".equals(data.get("token"))){
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
				//是否登录校验
				if(!super.isLogin(data.get("token"))){
					resultmap.put("status", "9010");
					resultmap.put("msg", Constants.getParamterkey("common.login.token"));
					passflag = false;
				}
				//通过校验,进行业务处理
				if(passflag){
					Page page =  shopService.ListSaleAmount(data);
					resultmap.put("data", page.getResult());
					resultmap.put("page", page.getCurrentPageNo());
					resultmap.put("pagesize", page.getPageSize());
					resultmap.put("partnerid", requestParams.get("partnerid"));
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
	 * 受关注商品查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/attionpro.shtml")
	@ResponseBody
	public Object queryAttionProduct(HttpServletRequest request, HttpServletResponse response) {
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
				SubscribeProVo vo = JSON.parseObject(requestParams.get("data"), SubscribeProVo.class);
				if(passflag && "".equals(vo.getToken())){
					resultmap.put("status", "9009");
					resultmap.put("msg", Constants.getParamterkey("common.param.tokenInvalid"));
					passflag = false;
				}
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
					Page resultPage = shopService.listAttionPro(vo);
					resultmap.put("data", resultPage.getResult());
					resultmap.put("partnerid", requestParams.get("partnerid"));
					resultmap.put("total",String.valueOf(resultPage.getTotalCount()));
					resultmap.put("page", String.valueOf(resultPage.getCurrentPageNo()));
					resultmap.put("pagesize", String.valueOf(resultPage.getPageSize()));
					resultmap.put("msg", "查询成功");
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
	 * 店铺资料修改
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/update.shtml")
	@ResponseBody
	public Object updateShop(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> resultmap = new TreeMap<String, Object>();

		// 解析request 获取参数 data,partnerid,service,time,version,key
		final Map<String,String> requestParams = super.parseRequest(request);
		// 验证参数是否合法
		try{
			String resultstr = super.validparameters(requestParams.get("data"), requestParams.get("partnerid"), requestParams.get("time"), requestParams.get("service"), requestParams.get("version"),
					requestParams.get("key"));
			if(resultstr.startsWith("0#")){
				boolean passflag = true;
				Map<String, String> data = (Map<String, String>) JSON.parse(requestParams.get("data"));
				//token校验
				if(passflag && "".equals(data.get("token"))){
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
				//是否登录校验
				if(!super.isLogin(data.get("token"))){
					resultmap.put("status", "9010");
					resultmap.put("msg", Constants.getParamterkey("common.login.token"));
					passflag = false;
				}
				//通过校验,进行业务处理
				if(passflag){
					resultmap.put("partnerid", requestParams.get("partnerid"));
					resultmap.put("msg", "查询成功");
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
	 * 受关注店铺查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/attion/query.shtml")
	@ResponseBody
	public Object queryAttionShop(HttpServletRequest request, HttpServletResponse response) {
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
				SubscribeShopVo vo = JSON.parseObject(requestParams.get("data"), SubscribeShopVo.class);
				if(passflag && "".equals(vo.getToken())){
					resultmap.put("status", "9009");
					resultmap.put("msg", Constants.getParamterkey("common.param.tokenInvalid"));
					passflag = false;
				}
				if(passflag && !"shop".equalsIgnoreCase(requestParams.get("service"))){
					resultmap.put("status","9007");
					resultmap.put("msg",Constants.getParamterkey("common.param.service"));
					passflag = false;
				}
				//通过校验,进行业务处理
				if(passflag){
					Page resultPage = shopService.listAttionShop(vo);
					resultmap.put("data",resultPage.getResult());
					resultmap.put("partnerid", requestParams.get("partnerid"));
					resultmap.put("total",resultPage.getTotalCount());
					resultmap.put("page", resultPage.getCurrentPageNo());
					resultmap.put("pagesize", resultPage.getPageSize());
					resultmap.put("msg", "查询成功");
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
	 * 店铺经营类别修改
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/updateshopkind.shtml")
	@ResponseBody
	public Object updateShopLind(HttpServletRequest request, HttpServletResponse response) {
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
				Map<String, String> data = (Map<String, String>) JSON.parse(requestParams.get("data"));
				if(passflag && "".equals(data.get("token"))){
					resultmap.put("status", "9009");
					resultmap.put("msg", Constants.getParamterkey("common.param.tokenInvalid"));
					passflag = false;
				}
				if(passflag && !"shop".equalsIgnoreCase(requestParams.get("service"))){
					resultmap.put("status","9007");
					resultmap.put("msg",Constants.getParamterkey("common.param.service"));
					passflag = false;
				}
				//是否登录校验
				if(passflag && !super.isLogin(data.get("token"))){
					resultmap.put("status", "9010");
					resultmap.put("msg", Constants.getParamterkey("common.login.token"));
					passflag = false;
				}
				//最多选3种经营类别
				//通过校验,进行业务处理
				if(passflag){
					//List<TMemShopKind> list = shopService.listTMemShopKind(Integer.valueOf(data.get("userid")));
					//shopService.updateCategory(tMemShopKind);
					resultmap.put("partnerid", requestParams.get("partnerid"));
					resultmap.put("msg", "修改成功");
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
	 * 店铺模板修改
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/updatetemplet.shtml")
	@ResponseBody
	public Object updateTemplet(HttpServletRequest request, HttpServletResponse response) {
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
				Map<String, String> data = (Map<String, String>) JSON.parse(requestParams.get("data"));
				if(passflag && "".equals(data.get("token"))){
					resultmap.put("status", "9009");
					resultmap.put("msg", Constants.getParamterkey("common.param.tokenInvalid"));
					passflag = false;
				}
				if(passflag && !"shop".equalsIgnoreCase(requestParams.get("service"))){
					resultmap.put("status","9007");
					resultmap.put("msg",Constants.getParamterkey("common.param.service"));
					passflag = false;
				}
				//是否登录校验
				if(passflag && !super.isLogin(data.get("token"))){
					resultmap.put("status", "9010");
					resultmap.put("msg", Constants.getParamterkey("common.login.token"));
					passflag = false;
				}
				//通过校验,进行业务处理
				if(passflag){
					shopService.updateTemplet(Integer.valueOf(data.get("userid")),Integer.valueOf(data.get("showpara")));
					resultmap.put("partnerid", requestParams.get("partnerid"));
					resultmap.put("msg", "修改成功");
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
