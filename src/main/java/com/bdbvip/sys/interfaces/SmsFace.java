package com.bdbvip.sys.interfaces;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.bdbvip.pojo.MembaseVo;
import com.bdbvip.sys.service.SysService;
import com.bdbvip.utils.Constants;
import com.bdbvip.utils.common.BaseAction;
import com.bdbvip.utils.common.exception.ServiceException;

@Controller
@RequestMapping("/interface/sms")
public class SmsFace extends BaseAction{

	
	@Autowired
	SysService sysService;
	
	
	@RequestMapping("/resendsms.shtml")
	@ResponseBody
	public Object resendsms(HttpServletRequest request,HttpServletResponse response){
		if(log.isInfoEnabled()){
			log.info("[SmsFace.sendmsg.show] ==> is comming....");	
		}
		//通过解析request 获取参数 data,partnerid,service,time,version,key
		final	Map<String,String> requestdata = super.parseRequest(request);
		Map<String,String> resultmap = new HashMap<String,String>();
		resultmap.put("partnerid",requestdata.get("partnerid"));
		//验证参数是否合法。 0#cefp 
		String resultstr = super.validparameters(requestdata.get("data"),requestdata.get("partnerid"),requestdata.get("time"),requestdata.get("service"),requestdata.get("version"),requestdata.get("key"));
		
		if(log.isInfoEnabled()){
			log.info("[SmsFace.sendmsg.resultstr] ==> "+resultstr);	
		}
		//0#开头的为成功标识可以进行后续业务逻辑处理了。
		if(resultstr.startsWith("0#")){
			
			boolean passflag  = true;
			if(passflag && !"sms".equalsIgnoreCase(requestdata.get("service"))){
				resultmap.put("status","9007");
				resultmap.put("msg",Constants.getParamterkey("common.param.service"));
				passflag = false;
			}
			//把传入参数 转化成一个java对象。后续处理。
			MembaseVo vo = JSON.parseObject(requestdata.get("data"),MembaseVo.class);
			vo.setPartnerid(requestdata.get("partnerid"));
			 
			 if(passflag && (StringUtils.isBlank(vo.getMobile()))){
				 resultmap.put("status","9013");
				 resultmap.put("msg","mobile"+Constants.getParamterkey("common.param.isnull"));
				 passflag = false;
			 }
			 if(passflag && StringUtils.isNotBlank(vo.getMobile()) && !vo.getMobile().matches("^1\\d{10}$")){
				 resultmap.put("status","9017");
				 resultmap.put("msg","mobile"+Constants.getParamterkey("common.param.notvalid"));
				 passflag = false;
			 }
			 String type = vo.getType();
			 if(passflag && (StringUtils.isBlank(type) || (",00,".indexOf(type))==-1)){
				 resultmap.put("status","9017");
				 resultmap.put("msg","type"+Constants.getParamterkey("common.param.notvalid"));
				 passflag = false;
			 } 
			 if(passflag && StringUtils.isBlank(vo.getToken())){
				 resultmap.put("status","9013");
				 resultmap.put("token",Constants.getParamterkey("common.param.isnull"));
				 passflag = false;
			 }
			 String userid = super.getUserid(vo.getToken());
			 if(passflag && StringUtils.isBlank(userid)){
				 resultmap.put("status","9009");
				 resultmap.put("msg",Constants.getParamterkey("common.param.tokenInvalid"));
				 passflag = false;
			 }
			 vo.setUserid(userid);
			 if(passflag){
				try {
					vo = sysService.create2thTMemSm(vo);
					resultmap.put("status",vo.getErrorcode());
					resultmap.put("msg",vo.getMsg());
				} catch (ServiceException e) {
					log.error(e.getMessage(),e);
					resultmap.put("status","9999");
					resultmap.put("msg",Constants.getParamterkey("common.syserror"));
				}
			 }
			
		}else{
			String[] str = StringUtils.split(resultstr,"#");
			resultmap.put("status",str[0]);
			resultmap.put("msg",str[1]);
		}
		return callback(requestdata.get("callback"),resultmap,request,response);
	}
	
	@RequestMapping("/sendsms.shtml")
	@ResponseBody
	public Object sendmsg(HttpServletRequest request,HttpServletResponse response){
		if(log.isInfoEnabled()){
			log.info("[SmsFace.sendmsg.show] ==> is comming....");	
		}
		//通过解析request 获取参数 data,partnerid,service,time,version,key
		final	Map<String,String> requestdata = super.parseRequest(request);
		Map<String,String> resultmap = new HashMap<String,String>();
		resultmap.put("partnerid",requestdata.get("partnerid"));
		//验证参数是否合法。 0#cefp 
		String resultstr = super.validparameters(requestdata.get("data"),requestdata.get("partnerid"),requestdata.get("time"),requestdata.get("service"),requestdata.get("version"),requestdata.get("key"));
		
		if(log.isInfoEnabled()){
			log.info("[SmsFace.sendmsg.resultstr] ==> "+resultstr);	
		}
		//0#开头的为成功标识可以进行后续业务逻辑处理了。
		if(resultstr.startsWith("0#")){
			
			boolean passflag  = true;
			if(passflag && !"sms".equalsIgnoreCase(requestdata.get("service"))){
				resultmap.put("status","9007");
				resultmap.put("msg",Constants.getParamterkey("common.param.service"));
				passflag = false;
			}
			//把传入参数 转化成一个java对象。后续处理。
			MembaseVo vo = JSON.parseObject(requestdata.get("data"),MembaseVo.class);
			vo.setPartnerid(requestdata.get("partnerid"));
			 
			 if(passflag && (StringUtils.isBlank(vo.getMobile()))){
				 resultmap.put("status","9013");
				 resultmap.put("msg","mobile"+Constants.getParamterkey("common.param.isnull"));
				 passflag = false;
			 }
			 if(passflag && StringUtils.isNotBlank(vo.getMobile()) && !vo.getMobile().matches("^1\\d{10}$")){
				 resultmap.put("status","9017");
				 resultmap.put("msg","mobile"+Constants.getParamterkey("common.param.notvalid"));
				 passflag = false;
			 }
			 String type = vo.getType();
			 if(passflag && (StringUtils.isBlank(type) || (",00,".indexOf(type))==-1)){
				 resultmap.put("status","9017");
				 resultmap.put("msg","type"+Constants.getParamterkey("common.param.notvalid"));
				 passflag = false;
			 } 
			 if(passflag && StringUtils.isBlank(vo.getToken())){
				 resultmap.put("status","9013");
				 resultmap.put("token",Constants.getParamterkey("common.param.isnull"));
				 passflag = false;
			 }
			 String userid = super.getUserid(vo.getToken());
			 if(passflag && StringUtils.isBlank(userid)){
				 resultmap.put("status","9009");
				 resultmap.put("msg",Constants.getParamterkey("common.param.tokenInvalid"));
				 passflag = false;
			 }
			 vo.setUserid(userid);
			 if(passflag){
				try {
					vo = sysService.createTMemSm(vo);
					resultmap.put("status",vo.getErrorcode());
					resultmap.put("msg",vo.getMsg());
				} catch (ServiceException e) {
					log.error(e.getMessage(),e);
					resultmap.put("status","9999");
					resultmap.put("msg",Constants.getParamterkey("common.syserror"));
				}
			 }
			
		}else{
			String[] str = StringUtils.split(resultstr,"#");
			resultmap.put("status",str[0]);
			resultmap.put("msg",str[1]);
		}
		return callback(requestdata.get("callback"),resultmap,request,response);
	}
}
