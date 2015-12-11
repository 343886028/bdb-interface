package com.bdbvip.shop.interfaces;

import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.bdbvip.entity.TMemMsg;
import com.bdbvip.entity.TOrdCompain;
import com.bdbvip.order.service.OrderService;
import com.bdbvip.shop.service.ShopMsgService;
import com.bdbvip.utils.Constants;
import com.bdbvip.utils.common.BaseAction;
import com.bdbvip.utils.common.date.DateUtil;

@Controller
@RequestMapping("/interface/sms")
public class ShopMsgFace extends BaseAction {
	
	@Autowired
	ShopMsgService shopMsgService;
	
	@Autowired
	OrderService orderService;
	
	/**
	 * 站内消息发送
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/sendtouser.shtml")
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
				if(passflag && !"sms".equalsIgnoreCase(requestParams.get("service"))){
					resultmap.put("status","9007");
					resultmap.put("msg",Constants.getParamterkey("common.param.service"));
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
					String[] userid = data.get("userid").split(",");
					TMemMsg msg = null;
					for(String uid:userid){
						//验证用户是否是关注或投诉ta的用户
						msg = new TMemMsg();
						msg.setCreatetime(DateUtil.getCurTimesTamp());
						msg.setFromuserid(Integer.valueOf(super.getUserid(data.get("token"))));
						msg.setMsgcontent(data.get("msgtype"));
						msg.setMsgtype(data.get("msgtype"));
						msg.setStatus("1");//默认 0无效 1有效
						msg.setTouserid(Integer.valueOf(uid));
						msg.setType("0");//默认0 针对用户类消息 1 针对店家类消息
						msg.setTradeno(data.get("tradeno"));
						shopMsgService.saveTMemMsg(msg);
					}
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
	 * 用户投诉回复
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/complainreply.shtml")
	@ResponseBody
	public Object complainReply(HttpServletRequest request, HttpServletResponse response) {
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
				//是否登录校验
				if(!super.isLogin(data.get("token"))){
					resultmap.put("status", "9010");
					resultmap.put("msg", Constants.getParamterkey("common.login.token"));
					passflag = false;
				}
				//通过校验,进行业务处理
				if(passflag){
					TOrdCompain compain = orderService.getCompainById(Integer.valueOf(data.get("complainid")));
					if(compain!=null){
						TMemMsg msg = new TMemMsg();
						msg.setCreatetime(DateUtil.getCurTimesTamp());
						msg.setFromuserid(Integer.valueOf(super.getUserid(data.get("token"))));
						msg.setMsgcontent(data.get("comment"));
						msg.setMsgtype(data.get("msgtype"));
						msg.setStatus("1");//默认 0无效 1有效
						msg.setTouserid(Integer.valueOf("touserid"));
						msg.setType("0");//默认0 针对用户类消息 1 针对店家类消息
						msg.setTradeno(data.get("tradeno"));
						compain.setMsgid(Integer.valueOf(data.get("msgid")));
						shopMsgService.doComplainReply(msg,compain);
						resultmap.put("partnerid", requestParams.get("partnerid"));
						resultmap.put("msg", "成功");
						resultmap.put("status", "0");
						
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
}
