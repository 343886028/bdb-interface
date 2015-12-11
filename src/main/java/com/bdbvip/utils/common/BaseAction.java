package com.bdbvip.utils.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.bdbvip.entity.TSysPartner;
import com.bdbvip.utils.CommUtil;
import com.bdbvip.utils.Constants;
import com.bdbvip.utils.CookieUtil;
import com.bdbvip.utils.DateUtils;
import com.bdbvip.utils.Md5Util;
import com.bdbvip.utils.MemcacheUtil;


public class BaseAction {

	public Logger log = Logger.getLogger(getClass());
	
	Integer page = 1;
	Integer pagesize = 20;
	Integer id;
	String token;
	String version;
	String partnerid;
	String key;
	String service;
	
	
	String msg;
	String status;
	
	public String getSignstrByPartnerid(String partnerid){
		String returnStr = DateUtils.date2String(new Date(), new SimpleDateFormat("yyyyMMddHHMMSS"));
		//根据一定的规则，生成唯一token
		Object obj = MemcacheUtil.get(Constants.PARTNER);
		if(obj==null){
			return returnStr;
		}
		@SuppressWarnings("unchecked")
		Map<String,TSysPartner> partnermap = (HashMap<String,TSysPartner>) obj; 
		if(null==partnermap || null== partnermap.get(partnerid)){
			return returnStr;
		}
		TSysPartner tsp = partnermap.get(partnerid);
		return tsp.getSignstr();
	}
	
	/**
	 * 1，初步简单的校验传入参数
	 * 2，验证md5码{所有参数排列顺序见文档说明}
	 * 3,校验partnerid在数据库中是否有匹配存在的。
	 * 全部通过，返回0#
	 * 校验规则 {data=, partnerid=, service=, time=, version=}
	 * @param data
	 * @param panterid
	 * @param time
	 * @param service
	 * @param version
	 * @param key
	 * @return
	 */
	public String validparameters(String data,String partnerid,String time,String service,String version,String key){
		   String md5str = data+partnerid+service+time+version;
		
			if(StringUtils.isBlank(service)){
				return "9002#"+Constants.getParamterkey("common.param.serviceisnull");
			}
			if(StringUtils.isBlank(data)){
				return "9003#"+Constants.getParamterkey("common.param.dataisnull");
			}
			if(StringUtils.isBlank(partnerid)){
				return "9004#"+Constants.getParamterkey("common.param.partneridisnull");
			}
			if(StringUtils.isBlank(time)){
				return "9005#"+Constants.getParamterkey("common.param.timeisnull");
			}
			if(StringUtils.isBlank(version)){
				return "9006#"+Constants.getParamterkey("common.param.versionisnull");
			}
			TSysPartner tp = Constants.getPartner(partnerid);
			if(tp == null){
				return "9998#"+Constants.getParamterkey("common.param.error");
			}
			md5str = md5str+tp.getSignstr();
			
			if(log.isInfoEnabled()){
				
				log.info("[baseAction.validparameters.md5str] ==> "+md5str);	
			}
			
			if(key.equalsIgnoreCase(Md5Util.md5_32(md5str))){
					return  "0#ok";
			 }else {
				 return "9001#"+Constants.getParamterkey("common.md5valid");
			 }
		
	}
	
	/**
	 * 解析请求入口的参数
	 * @param request
	 * @return
	 */
	public Map<String,String> parseRequest(HttpServletRequest request){
		String data = request.getParameter("data");
		String partnerid = request.getParameter("partnerid");
		String version = request.getParameter("version");
		String key = request.getParameter("key");
		String time  = request.getParameter("time");
		String service = request.getParameter("service");
		String callback = request.getParameter("callback");
		
		String requestid = this.getReqestIp(request);
		Map<String,String> paramap = new HashMap<String,String>();
		paramap.put("data",data);
		paramap.put("partnerid",partnerid);
		paramap.put("version",version);
		paramap.put("time",time);
		paramap.put("service", service);
		paramap.put("key",key);
		paramap.put("requestid", requestid);
		paramap.put("callback", callback);
		if(log.isInfoEnabled()){
			log.info("[baseAction.parseRequest.paramap] ==> "+paramap.toString());	
		}
		return paramap;
	}
	/**
	 * @deprecated 该方法将来会废弃，请使用md5str2
	 * 对返回的所有值，进行排序以后再加密
	 * 返回对应的key
	 * @param paramer
	 * @return
	 */
	public String md5str(Map<String,String> paramer){
		Map<String,String> paramap = new TreeMap<String,String>();
		paramap.putAll(paramer);
		String signstr  ="";
		if(paramer.get("partnerid") !=null){
			Object obj = MemcacheUtil.get(Constants.PARTNER);
			if(null != obj){
				Map<String,TSysPartner> tpmap = (HashMap<String,TSysPartner>)obj;
				TSysPartner tp = tpmap.get(paramer.get("partnerid"));
				if(null != tp){
					signstr  = tp.getSignstr();
				}
			}
		}
		StringBuilder mdstr = new StringBuilder(); 
		Iterator<String> itor = paramap.keySet().iterator();
		while(itor.hasNext()){
			String key = itor.next().toString();
			mdstr.append(paramer.get(key));
		}
		return Md5Util.md5_32(mdstr+signstr);
	}
	
	/**
	 * 对返回的所有值，进行排序以后再加密
	 * 返回对应的key
	 * @param paramer
	 * @return
	 */
	public String md5str2(Map<String,Object> paramer){
		Map<String,Object> paramap = new TreeMap<String,Object>();
		paramap.putAll(paramer);
		String signstr  ="";
		if(paramer.get("partnerid") !=null){
			Object obj = MemcacheUtil.get(Constants.PARTNER);
			if(null != obj){
				Map<String,TSysPartner> tpmap = (HashMap<String,TSysPartner>)obj;
				TSysPartner tp = tpmap.get(paramer.get("partnerid"));
				if(null != tp){
					signstr  = tp.getSignstr();
				}
			}
		}
		StringBuilder mdstr = new StringBuilder(); 
		Iterator<String> itor = paramap.keySet().iterator();
		while(itor.hasNext()){
			String key = itor.next().toString();
			mdstr.append(paramer.get(key));
		}
		return Md5Util.md5_32(mdstr+signstr);
	}
	
	/**
	 *  从COOKIE 中获取TOKEN 字符串。
	 * @param request
	 * @param response
	 * @return String
	 */
	public String getTokenString(HttpServletRequest request,
			HttpServletResponse response) {
		return CookieUtil.getCookieValue(request, CommUtil.LOGIN_TOKEN_CODE);
	}
	/**
	 * 获取用户ip地址
	 * @param request
	 * @return
	 */
	public String getReqestIp(HttpServletRequest request) {
		// 有cnd 加速时也能取到用户ip地址
		if (request == null)
			return "";
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null) {
			ip = request.getHeader("X-Real-IP");
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Cdn-Src-Ip");
			}
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			}
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("HTTP_CLIENT_IP");
			}
			if (ip == null || ip.length() == 0
					|| "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}
		}
		if (ip != null) {
			return ip.split(",")[0].trim();
		}
		return ip;
	}
	
	
	/**
	 * 获取用户ID
	 * @param token
	 * @return
	 */
	public String getUserid(String token) {
		if(StringUtils.isBlank(token)){
			return "";
		}
		Object obj = MemcacheUtil.get(token);
		Map<String,String> umap = (HashMap<String,String>)obj;
		return umap.get("_userid").toString();
	}

	/**
	 * 获取用户名
	 * @param token
	 * @return
	 */
	public String getUsername(String token) {
		if(StringUtils.isBlank(token)){
			return "";
		}
		Object obj = MemcacheUtil.get(token);
		Map<String,String> umap = (HashMap<String,String>)obj;
		return umap.get("_username").toString();
	}
	public Object callback2(String callback,Map<String,Object> resultmap,HttpServletRequest request,HttpServletResponse response){
		if(StringUtils.isBlank(callback)){
			return JSON.toJSONString(resultmap);
		} 
		try {
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter pw = response.getWriter();
			pw.write(callback+"("+JSON.toJSONString(resultmap)+")");
		} catch (IOException e) {
			 
		}
		return null;
	}
	/**
	 * @deprecated 该方法将来会废弃，请使用callback2
	 * @param callback
	 * @param resultmap
	 * @param request
	 * @param reponse
	 * @return
	 */
	public Object callback(String callback,Map<String,String> resultmap,HttpServletRequest request,HttpServletResponse response){
		if(StringUtils.isBlank(callback)){
			return JSON.toJSONString(resultmap);
		} 
		try {
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			PrintWriter pw = response.getWriter();
			pw.write(callback+"("+JSON.toJSONString(resultmap)+")");
		} catch (IOException e) {
			 
		}
		return null;
	}
	/**
	 * 获取用户账户余额
	 * @param token
	 * @return
	 */
	public String getUsermoney(String token) {
		if(StringUtils.isBlank(token)){
			return "0";
		}
		Object obj = MemcacheUtil.get(token);
		Map<String,String> umap = (HashMap<String,String>)obj;
		return umap.get("_amount").toString();
	}
	
	/**
	 * 判断用户是否登录
	 * @param token
	 * @return
	 */
	public Boolean isLogin(String token){
		Boolean bool = false;
		if(MemcacheUtil.get(token)!=null){
			bool = true;
		}
		return bool;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPagesize() {
		return pagesize;
	}

	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPartnerid() {
		return partnerid;
	}

	public void setPartnerid(String partnerid) {
		this.partnerid = partnerid;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
