package com.bdbvip.login.interfaces;

import java.io.IOException;
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
import com.bdbvip.entity.TMemBase;
import com.bdbvip.login.service.LoginService;
import com.bdbvip.pojo.MembaseVo;
import com.bdbvip.user.service.UserService;
import com.bdbvip.utils.Constants;
import com.bdbvip.utils.common.BaseAction;

@Controller
@RequestMapping("/interface/login")
public class LoginFace extends BaseAction {

	@Autowired
	UserService userService;
	
	@Autowired
	LoginService loginService;
	
	
	@RequestMapping("login2")
	@ResponseBody
	public Object login2(HttpServletRequest request,HttpServletResponse response){
		if(log.isInfoEnabled()){
			log.info("[LoginFace.login2.show] ==> is comming....");	
		}
		
		//通过解析request 获取参数 data,partnerid,service,time,version,key
		final	Map<String,String> requestpara = super.parseRequest(request);
			
		
		Map<String,Object> resultmap = new HashMap<String,Object>();
		try {
			resultmap.put("partnerid",requestpara.get("partnerid"));
			resultmap.put("status","0");
			
			
			//验证参数是否合法。 0#cefp 
			String resultstr = super.validparameters(requestpara.get("data"),requestpara.get("partnerid"),requestpara.get("time"),requestpara.get("service"),requestpara.get("version"),requestpara.get("key"));
			
			if(log.isInfoEnabled()){
				log.info("[LoginFace.login2.resultstr] ==> "+resultstr);	
			}
			//0#开头的为成功标识可以进行后续业务逻辑处理了。
			if(resultstr.startsWith("0#")){
				
				boolean passflag  = true;
				if(passflag && !"login".equalsIgnoreCase(requestpara.get("service"))){
					resultmap.put("status","9007");
					resultmap.put("msg",Constants.getParamterkey("common.param.service"));
					passflag = false;
				}
				
				//传入参数验证
				MembaseVo vo = JSON.parseObject(requestpara.get("data"), MembaseVo.class);
				vo.setPartnerid(requestpara.get("partnerid"));
				//验证用户名
				if(passflag && !vo.getUname().matches("^1\\d{10}$")){
					resultmap.put("status","1002");
					resultmap.put("msg",Constants.getParamterkey("login.reg.nomobile"));
					passflag = false;
				}
				TMemBase o = userService.getUserByName(vo.getUname().toLowerCase());
				if(passflag && o == null){
					resultmap.put("status","1006");
					resultmap.put("msg",Constants.getParamterkey("login.login.username"));
					passflag = false;
				}
				if(passflag && vo.getPwd().length() !=32){
					resultmap.put("status","1007");
					resultmap.put("msg",Constants.getParamterkey("login.login.pwd"));
					passflag = false;
				}
				
				if(passflag){
					//通过校验，进入业务校验。
					  vo = userService.doLogin(vo);
					  if("0".equals(vo.getErrorcode())){
						  //校验成功，做后续处理
						  Map<String,String> rmap = new HashMap<String,String>();
						  rmap.put("token",vo.getToken());
						  rmap.put("username",vo.getUname());
						  rmap.put("userlevel",vo.getUserlevel());
						  rmap.put("imgurl",vo.getImgurl());
						  
						  resultmap.put("status",vo.getErrorcode());
						  resultmap.put("msg",vo.getMsg()); 
						  resultmap.put("data",JSON.toJSON(rmap));
					  }else{
						  resultmap.put("status",vo.getErrorcode());
						  resultmap.put("msg",vo.getMsg()); 
					  }
				}
				
			}else{
				String[] str = resultstr.split("#");
				resultmap.put("status",str[0]);
				  resultmap.put("msg",str[1]);
			}
		}catch(Exception e){
			resultmap.put("status","9999");
			resultmap.put("msg",Constants.getParamterkey("common.syserror"));
		}
		String rkey  = super.md5str2(resultmap);
		resultmap.put("key",rkey);
		
		if(log.isInfoEnabled()){
			log.info("[LoginFace.login.resultmap] ==> "+resultmap.toString());	
		}
		//做一个预留，允许前端使用jsonp 方式访问
		String callback  = requestpara.get("callback");
		return super.callback2(callback, resultmap, request, response);
	}
	
	@RequestMapping("/forgetpwd.shtml")
	@ResponseBody
	public Object forgetpwd(HttpServletRequest request,HttpServletResponse response){
		if(log.isInfoEnabled()){
			log.info("[LoginFace.validforgetpwd.show] ==> is comming....");	
		}
		
		//通过解析request 获取参数 data,partnerid,service,time,version,key
		final	Map<String,String> requestpara = super.parseRequest(request);
			
		
		Map<String,String> resultmap = new HashMap<String,String>();
		try {
			resultmap.put("partnerid",requestpara.get("partnerid"));
			resultmap.put("status","0");
			
			
			//验证参数是否合法。 0#cefp 
			String resultstr = super.validparameters(requestpara.get("data"),requestpara.get("partnerid"),requestpara.get("time"),requestpara.get("service"),requestpara.get("version"),requestpara.get("key"));
			
			if(log.isInfoEnabled()){
				log.info("[LoginFace.validforgetpwd.resultstr] ==> "+resultstr);	
			}
			//0#开头的为成功标识可以进行后续业务逻辑处理了。
			if(resultstr.startsWith("0#")){
				
				boolean passflag  = true;
				if(passflag && !"login".equalsIgnoreCase(requestpara.get("service"))){
					resultmap.put("status","9007");
					resultmap.put("msg",Constants.getParamterkey("common.param.service"));
					passflag = false;
				}
				
				//传入参数验证
				MembaseVo vo = JSON.parseObject(requestpara.get("data"), MembaseVo.class);
				vo.setPartnerid(requestpara.get("partnerid"));
				//验证用户名
				if(passflag && !vo.getUname().matches("^1\\d{10}$")){
					resultmap.put("status","1002");
					resultmap.put("msg",Constants.getParamterkey("login.reg.nomobile"));
					passflag = false;
				}
				if(passflag && StringUtils.isBlank(vo.getCardno())){
					resultmap.put("status","9013");
					resultmap.put("msg","cardno"+Constants.getParamterkey("common.param.isnull"));
					passflag = false;
				}
				if(passflag && StringUtils.isBlank(vo.getCode())){
					resultmap.put("status","9013");
					resultmap.put("msg","code"+Constants.getParamterkey("common.param.isnull"));
					passflag = false;
				}
				if(passflag && (StringUtils.isBlank(vo.getSign())||vo.getSign().length()!=32)){
					resultmap.put("status","9017");
					resultmap.put("msg","sign"+Constants.getParamterkey("common.param.notvalid"));
					passflag = false;
				}
				if(passflag && (StringUtils.isBlank(vo.getNewpwd()) || vo.getNewpwd().length()!=32)){
					resultmap.put("status","9017");
					resultmap.put("msg","newpwd"+Constants.getParamterkey("common.param.notvalid"));
					passflag = false;
				}
				TMemBase o = userService.getUserByName(vo.getUname());
				if(passflag && o == null){
					resultmap.put("status","1006");
					resultmap.put("msg",Constants.getParamterkey("login.login.username"));
					passflag = false;
				}
				
				if(passflag){
					//通过校验，进入业务校验。
					  vo = loginService.updateLoginPwd(vo);
					  if("0".equals(vo.getErrorcode())){
						  //校验成功，做后续处理
						  resultmap.put("status",vo.getErrorcode());
						  resultmap.put("msg","校验通过"); 
						  resultmap.put("sign",vo.getMsg());
					  }else{
						  resultmap.put("status",vo.getErrorcode());
						  resultmap.put("msg",vo.getMsg()); 
					  }
				}
				
			}else{
				String[] str = resultstr.split("#");
				resultmap.put("status",str[0]);
				  resultmap.put("msg",str[1]);
			}
		}catch(Exception e){
			resultmap.put("status","9999");
			resultmap.put("msg",Constants.getParamterkey("common.syserror"));
		}
		String rkey  = super.md5str(resultmap);
		resultmap.put("key",rkey);
		
		if(log.isInfoEnabled()){
			log.info("[LoginFace.login.resultmap] ==> "+resultmap.toString());	
		}
		//做一个预留，允许前端使用jsonp 方式访问
		String callback  = requestpara.get("callback");
		return super.callback(callback, resultmap, request, response);
	}
	
	@RequestMapping("/validforgetpwd.shtml")
	public Object validforgetpwd(HttpServletRequest request,HttpServletResponse response){
		if(log.isInfoEnabled()){
			log.info("[LoginFace.validforgetpwd.show] ==> is comming....");	
		}
		
		//通过解析request 获取参数 data,partnerid,service,time,version,key
		final	Map<String,String> requestpara = super.parseRequest(request);
			
		
		Map<String,Object> resultmap = new HashMap<String,Object>();
		try {
			resultmap.put("partnerid",requestpara.get("partnerid"));
			resultmap.put("status","0");
			
			
			//验证参数是否合法。 0#cefp 
			String resultstr = super.validparameters(requestpara.get("data"),requestpara.get("partnerid"),requestpara.get("time"),requestpara.get("service"),requestpara.get("version"),requestpara.get("key"));
			
			if(log.isInfoEnabled()){
				log.info("[LoginFace.validforgetpwd.resultstr] ==> "+resultstr);	
			}
			//0#开头的为成功标识可以进行后续业务逻辑处理了。
			if(resultstr.startsWith("0#")){
				
				boolean passflag  = true;
				if(passflag && !"login".equalsIgnoreCase(requestpara.get("service"))){
					resultmap.put("status","9007");
					resultmap.put("msg",Constants.getParamterkey("common.param.service"));
					passflag = false;
				}
				
				//传入参数验证
				MembaseVo vo = JSON.parseObject(requestpara.get("data"), MembaseVo.class);
				vo.setPartnerid(requestpara.get("partnerid"));
				//验证用户名
				if(passflag && !vo.getUname().matches("^1\\d{10}$")){
					resultmap.put("status","1002");
					resultmap.put("msg",Constants.getParamterkey("login.reg.nomobile"));
					passflag = false;
				}
				if(passflag && StringUtils.isBlank(vo.getCardno())){
					resultmap.put("status","9013");
					resultmap.put("msg","cardno"+Constants.getParamterkey("common.param.isnull"));
					passflag = false;
				}
				if(passflag && StringUtils.isBlank(vo.getCode())){
					resultmap.put("status","9013");
					resultmap.put("msg","code"+Constants.getParamterkey("common.param.isnull"));
					passflag = false;
				}
				TMemBase o = userService.getUserByName(vo.getUname());
				if(passflag && o == null){
					resultmap.put("status","1006");
					resultmap.put("msg",Constants.getParamterkey("login.login.username"));
					passflag = false;
				}
				
				if(passflag){
					//通过校验，进入业务校验。
					  vo = loginService.validLoginPwd(vo);
					  if("0".equals(vo.getErrorcode())){
						  //校验成功，做后续处理
						  resultmap.put("status",vo.getErrorcode());
						  resultmap.put("msg","校验通过"); 
						  resultmap.put("sign",vo.getMsg());
					  }else{
						  resultmap.put("status",vo.getErrorcode());
						  resultmap.put("msg",vo.getMsg()); 
					  }
				}
				
			}else{
				String[] str = resultstr.split("#");
				resultmap.put("status",str[0]);
				  resultmap.put("msg",str[1]);
			}
		}catch(Exception e){
			resultmap.put("status","9999");
			resultmap.put("msg",Constants.getParamterkey("common.syserror"));
		}
		String rkey  = super.md5str2(resultmap);
		resultmap.put("key",rkey);
		
		if(log.isInfoEnabled()){
			log.info("[LoginFace.login.resultmap] ==> "+resultmap.toString());	
		}
		//做一个预留，允许前端使用jsonp 方式访问
		String callback  = requestpara.get("callback");
		return super.callback2(callback, resultmap, request, response);
	}
	
	@RequestMapping("login")
	@ResponseBody
	public Map<String,String> login(HttpServletRequest request,HttpServletResponse response){
		if(log.isInfoEnabled()){
			log.info("[LoginFace.login.show] ==> is comming....");	
		}
		
		//通过解析request 获取参数 data,partnerid,service,time,version,key
		final	Map<String,String> requestpara = super.parseRequest(request);
			
		
		Map<String,String> resultmap = new HashMap<String,String>();
		try {
			resultmap.put("partnerid",requestpara.get("partnerid"));
			resultmap.put("status","0");
			
			
			//验证参数是否合法。 0#cefp 
			String resultstr = super.validparameters(requestpara.get("data"),requestpara.get("partnerid"),requestpara.get("time"),requestpara.get("service"),requestpara.get("version"),requestpara.get("key"));
			
			if(log.isInfoEnabled()){
				log.info("[LoginFace.login.resultstr] ==> "+resultstr);	
			}
			//0#开头的为成功标识可以进行后续业务逻辑处理了。
			if(resultstr.startsWith("0#")){
				
				boolean passflag  = true;
				if(passflag && !"login".equalsIgnoreCase(requestpara.get("service"))){
					resultmap.put("status","9007");
					resultmap.put("msg",Constants.getParamterkey("common.param.service"));
					passflag = false;
				}
				
				//传入参数验证
				MembaseVo vo = JSON.parseObject(requestpara.get("data"), MembaseVo.class);
				vo.setPartnerid(requestpara.get("partnerid"));
				//验证用户名
				if(passflag && !vo.getUname().matches("^1\\d{10}$")){
					resultmap.put("status","1002");
					resultmap.put("msg",Constants.getParamterkey("login.reg.nomobile"));
					passflag = false;
				}
				TMemBase o = userService.getUserByName(vo.getUname().toLowerCase());
				if(passflag && o == null){
					resultmap.put("status","1006");
					resultmap.put("msg",Constants.getParamterkey("login.login.username"));
					passflag = false;
				}
				if(passflag && vo.getPwd().length() !=32){
					resultmap.put("status","1007");
					resultmap.put("msg",Constants.getParamterkey("login.login.pwd"));
					passflag = false;
				}
				
				if(passflag){
					//通过校验，进入业务校验。
					  vo = userService.doLogin(vo);
					  if("0".equals(vo.getErrorcode())){
						  //校验成功，做后续处理
						  resultmap.put("status",vo.getErrorcode());
						  resultmap.put("msg",vo.getMsg()); 
						  resultmap.put("token",vo.getToken());
					  }else{
						  resultmap.put("status",vo.getErrorcode());
						  resultmap.put("msg",vo.getMsg()); 
					  }
				}
				
			}else{
				String[] str = resultstr.split("#");
				resultmap.put("status",str[0]);
				resultmap.put("msg",str[1]);
			}
		}catch(Exception e){
			resultmap.put("status","9999");
			resultmap.put("msg",Constants.getParamterkey("common.syserror"));
		}
		String rkey  = super.md5str(resultmap);
		resultmap.put("key",rkey);
		
		if(log.isInfoEnabled()){
			log.info("[LoginFace.login.resultmap] ==> "+resultmap.toString());	
		}
		//做一个预留，允许前端使用jsonp 方式访问
		String callback  = requestpara.get("callback");
		if(StringUtils.isNotBlank(callback)){
			try {
				response.getWriter().write(callback+"("+JSON.toJSONString(resultmap)+")");
			} catch (IOException e) {
				 
			}
			return null;
		}
		return resultmap;
	}
	 
}
