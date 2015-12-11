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
import com.bdbvip.entity.TMemMsg;
import com.bdbvip.user.service.UserService;
import com.bdbvip.utils.Constants;
import com.bdbvip.utils.common.BaseAction;
import com.bdbvip.utils.common.dao.support.Page;

@Controller
@RequestMapping("/interface/msg")
public class MsgFace extends BaseAction {
	@Autowired
	UserService userService;
	/**
	 * 站短信查询 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/query.shtml")
	@ResponseBody
	public Object query(Model model,HttpServletRequest request,HttpServletResponse response){
		if(log.isInfoEnabled()) {
			log.info("[OrderFace.index.show] ==> is coming....");
		}
		Map<String,String> requestParams = new HashMap<String, String>();
		Map<String,String> resultmap = new TreeMap<String,String>();
		//解析request 获取参数 data,partnerid,service,time,version,key
		requestParams = super.parseRequest(request);
		//验证参数是否合法
		String resultstr = super.validparameters(requestParams.get("data"),
				requestParams.get("partnerid"), requestParams.get("time"),
				requestParams.get("service"), requestParams.get("version"),
				requestParams.get("key"));
		if(log.isInfoEnabled()) {
			log.info("[OrderFace.query.resultstr] ==> "+resultstr);
		}
		try{
			//0#开头是成功标识，可以进行后续业务逻辑处理
			if(resultstr.startsWith("0#")){
				boolean flag = true;
				Map<String,Object> params = (Map<String,Object>)JSON.parse(requestParams.get("data"));
				if(params.containsKey("token")) {
					//验证token
					if(flag && StringUtils.isBlank(params.get("token").toString())) {
						resultmap.put("status", "2001");
						resultmap.put("msg", Constants.getParamterkey("user.token.error"));
						flag = false;
					}
					//验证service
					if(flag && !"msg".equalsIgnoreCase(requestParams.get("service"))){
						resultmap.put("status","9007");
						resultmap.put("msg",Constants.getParamterkey("common.param.service"));
						flag = false;
					}
					//验证status
					if(flag && (!params.containsKey("status") || "".equals(requestParams.get("status"))) ){
						resultmap.put("status","2014");
						resultmap.put("msg",Constants.getParamterkey("user.user.msg.invalidstatus"));
						flag = false;
					}
					//验证page
					if (flag && (!params.containsKey("page") || !params.containsKey("pagesize") || "".equals(requestParams.get("pagesize")) || "".equals(requestParams.get("page")))) {
						resultmap.put("status", "2002");
						resultmap.put("msg", Constants.getParamterkey("user.user.invalidpage"));
						flag = false;
					}
					
					if (flag) {
						// service处理业务逻辑
						List<TMemMsg> msgs = null;
						Page page = (Page) params.get("page");
						page = userService.findMsgByStatus(super.getUserid(params.get("userid").toString()), params.get("status").toString(), page.getCurrentPageNo(), page.getPageSize());
						Map<String, String> msgMaps = new TreeMap<String, String>();
						if (page != null) {
							msgs = page.getResult();
						}
						for (TMemMsg msg : msgs) {
							Map<String, String> msgMap = new TreeMap<String, String>();
							msgMap.put("id", String.valueOf(msg.getMsgid()));
							msgMap.put("msgcontent", msg.getMsgcontent());
							msgMap.put("fromuserid", String.valueOf(msg.getFromuserid()));
							msgMap.put("fromusername", (userService.findUserById(String.valueOf(msg.getFromuserid()))).getUsername());
							msgMap.put("createtime", String.valueOf(msg.getCreatetime()));
							msgMap.put("msgtype", msg.getMsgtype());
							msgMaps.putAll(msgMap);
						}
						resultmap.put("partnerid", requestParams.get("partnerid"));
						resultmap.put("id", params.get("id").toString());
						resultmap.put("userid", super.getUserid(params.get("token").toString()));
						resultmap.put("fromuserid", super.getUserid(params.get("token").toString()));
						resultmap.put("data", JSON.toJSONString(msgMaps));
						resultmap.put("status","0");
						resultmap.put("msg", "成功");
					}
					String rkey  = super.md5str(resultmap);
					resultmap.put("key",rkey);
				}
			} else {
				String[] str = StringUtils.split(resultstr,"#");
				resultmap.put("status", str[0]);
				resultmap.put("msg", str[1]);
			}
		}catch(Exception e) {
			resultmap.put("status", "9999");
			resultmap.put("msg", Constants.getParamterkey("common.syserror"));
			log.error(e.getMessage(),e);
			log.error("[MsgFace.query] ==> "+Constants.getParamterkey("common.syserror"));
		}
		return callback(requestParams.get("callback"),resultmap,request,response);
	}
	
	/**
	 * 站短信查看  
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/detail.shtml")
	@ResponseBody
	public Object detail(Model model,HttpServletRequest request,HttpServletResponse response){
		if(log.isInfoEnabled()) {
			log.info("[OrderFace.index.show] ==> is coming....");
		}
		Map<String,String> requestParams = new HashMap<String, String>();
		Map<String,String> resultmap = new TreeMap<String,String>();
		resultmap.put("status","0");
		//解析request 获取参数 data,partnerid,service,time,version,key
		requestParams = super.parseRequest(request);
		//验证参数是否合法
		String resultstr = super.validparameters(requestParams.get("data"),
				requestParams.get("partnerid"), requestParams.get("time"),
				requestParams.get("service"), requestParams.get("version"),
				requestParams.get("key"));
		if(log.isInfoEnabled()) {
			log.info("[OrderFace.query.resultstr] ==> "+resultstr);
		}
		try{
			//0#开头是成功标识，可以进行后续业务逻辑处理
			if (resultstr.startsWith("0#")) {
				boolean flag = true;
				Map<String, String> params = (Map<String, String>) JSON.parse(requestParams.get("data"));
				if (params.containsKey("token")) {
					// 验证token
					if (flag && StringUtils.isBlank(params.get("token"))) {
						resultmap.put("status", "2001");
						resultmap.put("msg", Constants.getParamterkey("user.token.error"));
						flag = false;
					}
					// 验证service
					if (flag && !"msg".equalsIgnoreCase(requestParams.get("service"))) {
						resultmap.put("status", "9007");
						resultmap.put("msg", Constants.getParamterkey("common.param.service"));
						flag = false;
					}
					// 验证msgID
					if (flag && StringUtils.isBlank(params.get("id"))) {
						resultmap.put("status", "2025");
						resultmap.put("msg", Constants.getParamterkey("user.user.msg.invalidmsgid"));
						flag = false;
					}
					if (flag) {
						// service处理业务逻辑
						// 查看站短消息
						Page page = userService.findMsgById(Integer.valueOf(params.get("id")));
						TMemMsg msg = (TMemMsg) page.getResult().get(0);
						resultmap.put("total", String.valueOf(page.getTotalCount()));
						resultmap.put("page", JSON.toJSONString(page));
						resultmap.put("pagesize", String.valueOf(page.getPageSize()));
						resultmap.put("id", String.valueOf(msg.getMsgid()));
						resultmap.put("msgcontent", msg.getMsgcontent());
						resultmap.put("fromuserid", String.valueOf(msg.getFromuserid()));
						resultmap.put("fromusername", (userService.findUserById(String.valueOf(msg.getFromuserid()))).getUsername());
						resultmap.put("createtime", msg.getCreatetime().toString());
						resultmap.put("msgtype", msg.getMsgtype());
						resultmap.put("partnerid", requestParams.get("partnerid"));
						String rkey = super.md5str(resultmap);
						resultmap.put("key", rkey);
						resultmap.put("msg", "成功");
					}
				}
			} else {
				String[] str = StringUtils.split(resultstr,"#");
				resultmap.put("status", str[0]);
				resultmap.put("msg", str[1]);
			}
		}catch(Exception e) {
			resultmap.put("status", "9999");
			resultmap.put("msg", Constants.getParamterkey("common.syserror"));
			log.error(e.getMessage(),e);
			log.error("[MsgFace.query] ==> "+Constants.getParamterkey("common.syserror"));
		}
		return callback(requestParams.get("callback"),resultmap,request,response);
	}
}
