package com.bdbvip.user.interfaces;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.bdbvip.entity.TMemAddress;
import com.bdbvip.user.service.UserService;
import com.bdbvip.utils.Constants;
import com.bdbvip.utils.common.BaseAction;
import com.bdbvip.utils.common.dao.support.Page;
import com.bdbvip.utils.common.exception.ServiceException;

@Controller
@RequestMapping("/interface/user/address")
public class UserAddressFace extends BaseAction {
	@Autowired
	UserService userService;
	
	
	/**
	 * 收货地址查询
	 * @param model
	 * @param request
	 * @param response
	 * @return json {‘partnerid’:’1003’,’errorcode’:’0’,’total’:’10’,’page’：‘2’，‘pagesize’:’20’,’msg’:’查询成功’,
	 * 			 	 ’list’:[{‘addressid’:’1232’,’username’:’张三’,’provider’:’广东省’, ’city’:’深圳市’,’address:‘福田保税区福年广场B栋’,’mobile’:’123465’,’flag’:’0’,’post’:’411208’},{},{},{}],
	 * 				 ’key’:’123243453511231’}
	 * @throws ServiceException
	 */
	@RequestMapping("/list.shtml")
	@ResponseBody
	public Object list(Model model,HttpServletRequest request,HttpServletResponse response) throws ServiceException{
		Map<String,String> requestParams = new HashMap<String, String>();
		Map<String,String> resultmap = new TreeMap<String,String>();
		//解析request 获取参数 data,partnerid,service,time,version,key
		requestParams = super.parseRequest(request);
		//验证参数是否合法
		String resultstr = super.validparameters(requestParams.get("data"),
				requestParams.get("partnerid"), requestParams.get("time"),
				requestParams.get("service"), requestParams.get("version"),
				requestParams.get("key"));
		try{
			//0#开头是成功标识，可以进行后续业务逻辑处理
			if(resultstr.startsWith("0#")){
				boolean flag = true;
				Map<String,Object> params = (Map<String,Object>)JSON.parse(requestParams.get("data"));
				if(params.containsKey("token")) {
					//验证token
					if(flag && "".equals(params.get("token"))) {
						resultmap.put("status", "9009");
						resultmap.put("msg", Constants.getParamterkey("common.param.tokenInvalid"));
						flag = false;
					}
					//验证service
					if(flag && !"list".equalsIgnoreCase(requestParams.get("service"))){
						resultmap.put("status","9007");
						resultmap.put("msg",Constants.getParamterkey("common.param.service"));
						flag = false;
					}
					if (flag && (!params.containsKey("page")
									|| !params.containsKey("pagesize")
									|| "".equals(params.get("page")) 
									|| "".equals(params.get("pagesize")))) {
						resultmap.put("status","2015");
						resultmap.put("msg","user.user.address.invalidpage");
						flag = false;
					}
					if(flag) {
						Page page=new Page();
						page.setCurrentPageNo(Integer.parseInt(params.get("page").toString()));
						Page resultPage = userService.listUserAddress(Integer.valueOf(super.getUserid(params.get("token").toString())),page);
						List<TMemAddress> userAddress = resultPage.getResult();
						for(TMemAddress address : userAddress) {
							Map<String,String> addressJson = new TreeMap<String,String>();
							addressJson.put("addressid", String.valueOf(address.getAddressid()));
							addressJson.put("username", address.getUsername());
							addressJson.put("province", address.getProvince());
							addressJson.put("city", address.getCity());
							addressJson.put("address", address.getAddress());
							addressJson.put("mobile", address.getTelphone());
							addressJson.put("flag", address.getFlag());
							addressJson.put("post", address.getPost());
							resultmap.putAll(addressJson);
						}
						resultmap.put("partnerid",requestParams.get("partnerid"));
						resultmap.put("total",String.valueOf(resultPage.getTotalCount()));
						resultmap.put("page",String.valueOf(resultPage.getCurrentPageNo()));
						resultmap.put("pagesize",String.valueOf(resultPage.getPageSize()));
						resultmap.put("status","0");
						resultmap.put("msg","查询成功");
					}
				}
			} else {
				String[] str = StringUtils.split(resultstr,"#");
				resultmap.put("status", str[0]);
				resultmap.put("msg", str[1]);
				log.info(resultmap);
			}
		}catch(Exception e) {
			resultmap.put("status", "9999");
			resultmap.put("msg", Constants.getParamterkey("common.syserror"));
			log.error(Constants.getParamterkey("common.syserror"));
		}
		return callback(requestParams.get("callback"),resultmap,request,response);
	}
	
	/**
	 * 收货地址增加
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping("/add.shtml")
	@ResponseBody
	public Object add(Model model,HttpServletRequest request,HttpServletResponse response) throws ServiceException{
		Map<String,String> requestParams = new HashMap<String, String>();
		Map<String,String> resultmap = new TreeMap<String,String>();
		//解析request 获取参数 data,partnerid,service,time,version,key
		requestParams = super.parseRequest(request);
		//验证参数是否合法
		String resultstr = super.validparameters(requestParams.get("data"),
				requestParams.get("partnerid"), requestParams.get("time"),
				requestParams.get("service"), requestParams.get("version"),
				requestParams.get("key"));
		try{
			//0#开头是成功标识，可以进行后续业务逻辑处理
			if(resultstr.startsWith("0#")){
				boolean flag = true;
				Map<String,Object> params = (Map<String,Object>)JSON.parse(requestParams.get("data"));
				if(params.containsKey("token")) {
					//验证token
					if(flag && "".equals(params.get("token"))) {
						resultmap.put("status", "9009");
						resultmap.put("msg", Constants.getParamterkey("common.param.tokenInvalid"));
						flag = false;
					}
					//验证service
					if(flag && !"add".equalsIgnoreCase(requestParams.get("service"))){
						resultmap.put("status","9007");
						resultmap.put("msg",Constants.getParamterkey("common.param.service"));
						flag = false;
					}
					//收货人
					if(flag && (!params.containsKey("username") || "".equals(params.get("username")))) {
						resultmap.put("status","2016");
						resultmap.put("msg",Constants.getParamterkey("user.user.address.invalidreciver"));
						flag = false;
					}
					//省份
					if(flag &&(!params.containsKey("province") || "".equals(params.get("provider")) )) {
						resultmap.put("status","2017");
						resultmap.put("msg",Constants.getParamterkey("user.user.address.invalidprovince"));
						flag = false;
					}
					//城市
					if(flag &&(!params.containsKey("city") || "".equals(params.get("city")) )) {
						resultmap.put("status","2018");
						resultmap.put("msg",Constants.getParamterkey("user.user.address.invalidcity"));
						flag = false;
					}
					//详细地址
					if(flag &&(!params.containsKey("address") || "".equals(params.get("address")) )) {
						resultmap.put("status","2019");
						resultmap.put("msg",Constants.getParamterkey("user.user.address.invalidaddress"));
						flag = false;
					}
					//联系方式
					if(flag &&(!params.containsKey("mobile") || "".equals(params.get("mobile")) )) {
						resultmap.put("status","2020");
						resultmap.put("msg",Constants.getParamterkey("user.user.address.invalidmobile"));
						flag = false;
					}
					//邮编
					if(flag &&(!params.containsKey("post") || "".equals(params.get("post")) )) {
						resultmap.put("status","2021");
						resultmap.put("msg",Constants.getParamterkey("user.user.address.invalidpost"));
						flag = false;
					}
					if(flag) {
						//service处理业务逻辑
						TMemAddress address = new TMemAddress();
						address.setUserid(Integer.valueOf(super.getUserid(params.get("token").toString())));
						address.setProvince(params.get("province").toString());
						address.setCity(params.get("city").toString());
						address.setUsername(params.get("username").toString());
						address.setTelphone(params.get("mobile").toString());
						address.setPost(params.get("post").toString());
						address.setAddress(params.get("address").toString());
						userService.saveUserAddress(address);
						String rkey  = super.md5str(resultmap);
						resultmap.put("partnerid", requestParams.get("partnerid"));
						resultmap.put("status","0");
						resultmap.put("key",rkey);
						resultmap.put("msg", "新增收货地址成功！");
					} 
				}
			} else {
				String[] str = StringUtils.split(resultstr,"#");
				resultmap.put("status", str[0]);
				resultmap.put("msg", str[1]);
				log.info(resultmap);
			}
		}catch(Exception e) {
			resultmap.put("status", "9999");
			resultmap.put("msg", Constants.getParamterkey("common.syserror"));
			log.error(Constants.getParamterkey("common.syserror"));
		}
		return callback(requestParams.get("callback"),resultmap,request,response);
	}
	
	/**
	 * 收货地址删除
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping("/delete.shtml")
	@ResponseBody
	public Object delete(Model model,HttpServletRequest request,HttpServletResponse response) throws ServiceException{
		Map<String,String> requestParams = new HashMap<String, String>();
		Map<String,String> resultmap = new TreeMap<String,String>();
		//解析request 获取参数 data,partnerid,service,time,version,key
		requestParams = super.parseRequest(request);
		//验证参数是否合法
		String resultstr = super.validparameters(requestParams.get("data"),
				requestParams.get("partnerid"), requestParams.get("time"),
				requestParams.get("service"), requestParams.get("version"),
				requestParams.get("key"));
		try{
			//0#开头是成功标识，可以进行后续业务逻辑处理
			if(resultstr.startsWith("0#")){
				boolean flag = true;
				Map<String,Object> params = (Map<String,Object>)JSON.parse(requestParams.get("data"));
				if(params.containsKey("token")) {
					//验证token
					if(flag && "".equals(params.get("token"))) {
						resultmap.put("status", "9009");
						resultmap.put("msg", Constants.getParamterkey("common.param.tokenInvalid"));
						flag = false;
					}
					//验证service
					if(flag && !"delete".equalsIgnoreCase(requestParams.get("service"))){
						resultmap.put("status","9007");
						resultmap.put("msg",Constants.getParamterkey("common.param.service"));
						flag = false;
					}
					//addressID
					if(flag && (!params.containsKey("addressid") || "".equals(params.get("addressid")))) {
						resultmap.put("status","2022");
						resultmap.put("msg",Constants.getParamterkey("user.user.address.invalidaddressid"));
						flag = false;
					}
					if(flag) {
						//service处理业务逻辑
						userService.deleteUserAddressByAddressID(Integer.valueOf(params.get("addressid").toString()));
						String rkey  = super.md5str(resultmap);
						resultmap.put("key",rkey);
						resultmap.put("partnerid", requestParams.get("partnerid"));
						resultmap.put("status","0");
						resultmap.put("msg", "删除收货地址成功！");
					} 
				}
			} else {
				String[] str = StringUtils.split(resultstr,"#");
				resultmap.put("status", str[0]);
				resultmap.put("msg", str[1]);
				log.info(resultmap);
			}
		}catch(Exception e) {
			resultmap.put("status", "9999");
			resultmap.put("msg", Constants.getParamterkey("common.syserror"));
			log.error(Constants.getParamterkey("common.syserror"));
		}
		return callback(requestParams.get("callback"),resultmap,request,response);
	}
	
	@RequestMapping("/setdefault.shtml")
	@ResponseBody
	public Object setDefault(Model model,HttpServletRequest request,HttpServletResponse response) throws ServiceException{
		Map<String,String> requestParams = new HashMap<String, String>();
		Map<String,String> resultmap = new TreeMap<String,String>();
		//解析request 获取参数 data,partnerid,service,time,version,key
		requestParams = super.parseRequest(request);
		//验证参数是否合法
		String resultstr = super.validparameters(requestParams.get("data"),
				requestParams.get("partnerid"), requestParams.get("time"),
				requestParams.get("service"), requestParams.get("version"),
				requestParams.get("key"));
		try{
			//0#开头是成功标识，可以进行后续业务逻辑处理
			if(resultstr.startsWith("0#")){
				boolean flag = true;
				Map<String,Object> params = (Map<String,Object>)JSON.parse(requestParams.get("data"));
				if(params.containsKey("token")) {
					//验证token
					if(flag && "".equals(params.get("token"))) {
						resultmap.put("status", "9009");
						resultmap.put("msg", Constants.getParamterkey("common.param.tokenInvalid"));
						flag = false;
					}
					//验证service
					if(flag && !"delete".equalsIgnoreCase(requestParams.get("service"))){
						resultmap.put("status","9007");
						resultmap.put("msg",Constants.getParamterkey("common.param.service"));
						flag = false;
					}
					//addressID
					if(flag && (!params.containsKey("addressid") || "".equals(params.get("addressid")))) {
						resultmap.put("status","2022");
						resultmap.put("msg",Constants.getParamterkey("user.user.address.invalidaddressid"));
						flag = false;
					}
					if(flag) {
						//service处理业务逻辑
						userService.updateDefaultUserAddress(Integer.valueOf(super.getUserid(params.get("token").toString())), 
											Integer.valueOf(params.get("addressid").toString()));
						String rkey  = super.md5str(resultmap);
						resultmap.put("partnerid", requestParams.get("partnerid"));
						resultmap.put("key",rkey);
						resultmap.put("status","0");
						resultmap.put("msg", "成功设置默认收货地址！");
					} 
				}
			} else {
				String[] str = StringUtils.split(resultstr,"#");
				resultmap.put("status", str[0]);
				resultmap.put("msg", str[1]);
				log.info(resultmap);
			}
		}catch(Exception e) {
			resultmap.put("status", "9999");
			resultmap.put("msg", Constants.getParamterkey("common.syserror"));
			log.error(Constants.getParamterkey("common.syserror"));
		}
		return callback(requestParams.get("callback"), resultmap, request, response);
	}
}
