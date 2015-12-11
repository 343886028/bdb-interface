package com.bdbvip.login.interfaces;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.bdbvip.entity.TMemBase;
import com.bdbvip.entity.TMemShop;
import com.bdbvip.pojo.MembaseVo;
import com.bdbvip.pojo.ShopVo;
import com.bdbvip.user.service.UserService;
import com.bdbvip.utils.Constants;
import com.bdbvip.utils.common.BaseAction;
import com.bdbvip.utils.common.exception.ServiceException;


@Controller
@RequestMapping("/interface/reg")
public class RegFace extends BaseAction {

	@Autowired
	UserService userService;
	
	
	@RequestMapping("/unionlogin.shtml")
	@ResponseBody
	public Object unionlogin(HttpServletRequest request,HttpServletResponse response){
		if(log.isInfoEnabled()){
			log.info("[RegFace.unionlogin.show] ==> is comming....");	
		}
		//通过解析request 获取参数 data,partnerid,service,time,version,key
		final	Map<String,String> requestdata = super.parseRequest(request);
		Map<String,String> resultmap = new HashMap<String,String>();
		resultmap.put("partnerid",requestdata.get("partnerid"));
		//验证参数是否合法。 0#cefp 
		String resultstr = super.validparameters(requestdata.get("data"),requestdata.get("partnerid"),requestdata.get("time"),requestdata.get("service"),requestdata.get("version"),requestdata.get("key"));
		
		if(log.isInfoEnabled()){
			log.info("[RegFace.unionlogin.resultstr] ==> "+resultstr);	
		}
		//0#开头的为成功标识可以进行后续业务逻辑处理了。
		if(resultstr.startsWith("0#")){
			
			boolean passflag  = true;
			if(passflag && !"register".equalsIgnoreCase(requestdata.get("service"))){
				resultmap.put("status","9007");
				resultmap.put("msg",Constants.getParamterkey("common.param.service"));
				passflag = false;
			}
			//把传入参数 转化成一个java对象。后续处理。
			MembaseVo vo = JSON.parseObject(requestdata.get("data"),MembaseVo.class);
			vo.setPartnerid(requestdata.get("partnerid"));
			 String openid = vo.getOpenid();
			 if(passflag && (StringUtils.isBlank(openid))){
				 resultmap.put("status","9013");
				 resultmap.put("msg","openid"+Constants.getParamterkey("common.param.isnull"));
				 passflag = false;
			 }
			 if(passflag && StringUtils.isNotBlank(openid) && openid.length()>32){
				 resultmap.put("status","9016");
				 resultmap.put("msg","openid"+Constants.getParamterkey("common.param.maxlength"));
				 passflag = false;
			 }
			 String type = vo.getType();
			 if(passflag && StringUtils.isBlank(type)){
				 resultmap.put("status","9013");
				 resultmap.put("msg","type"+Constants.getParamterkey("common.param.isnull"));
				 passflag = false;
			 } 
			 if(passflag && StringUtils.isNotBlank(type) && !type.matches("^\\d{1,5}$")){
				 resultmap.put("status","9017");
				 resultmap.put("msg","type"+Constants.getParamterkey("common.param.notvalid"));
				 passflag = false;
			 }
			 String uname = vo.getUname();
			 if(passflag && StringUtils.isBlank(uname)){
				 resultmap.put("status","9013");
				 resultmap.put("msg","uname"+Constants.getParamterkey("common.param.isnull"));
				 passflag = false;
			 } 
			 uname = uname.replaceAll("\\s+","").toLowerCase().trim();
			 if(passflag && uname.toLowerCase().length()>50){
				 resultmap.put("status","9016");
				 resultmap.put("msg","uname"+Constants.getParamterkey("common.param.maxlength"));
				 passflag = false;
			 } 
			 vo.setUname(uname);
		    if(passflag && (vo.getPwd().length()!= 32)){
				resultmap.put("status","9017");
			resultmap.put("msg",Constants.getParamterkey("common.param.notvalid"));
				passflag = false;
			}
			vo.setPwd(vo.getPwd().toLowerCase());
			//长度校验
			if(passflag &&  StringUtils.isNotBlank(vo.getSource()) && vo.getSource().length()>20){
				resultmap.put("status","1004");
			resultmap.put("msg","source"+Constants.getParamterkey("login.reg.overlength"));
				passflag = false;
			}
			
			if(passflag &&  StringUtils.isNotBlank(vo.getComment()) && vo.getComment().length()>20){
				resultmap.put("status","1004");
				resultmap.put("msg","comment"+Constants.getParamterkey("login.reg.overlength"));
				passflag = false;
			}
			if(passflag &&  StringUtils.isNotBlank(vo.getRequestip()) && vo.getRequestip().length()>20){
				resultmap.put("status","1004");
				resultmap.put("msg","requestip"+Constants.getParamterkey("login.reg.overlength"));
				passflag = false;
			}
			if(passflag &&  StringUtils.isNotBlank(vo.getRequesturl()) && vo.getRequesturl().length()>50){
				resultmap.put("status","1004");
				resultmap.put("msg","requesturl"+Constants.getParamterkey("login.reg.overlength"));
				passflag = false;
			}
			TMemBase o = userService.getUserByName(vo.getUname());
			if(passflag && null != o){
				resultmap.put("status","1001");
				resultmap.put("msg",Constants.getParamterkey("login.reg.unameexists"));
				passflag = false;
			}
			//common.shop.keys过滤敏感词
			String keys = Constants.getConfigkey("common.shop.keys");
			if(keys.indexOf(vo.getUname())>-1){
				resultmap.put("status","9015");
				resultmap.put("msg",Constants.getParamterkey("common.param.sensitive"));
				passflag = false;
			}
			 if(passflag){
				try {
					vo = userService.doUnion(vo);
					if(vo!=null && StringUtils.isNotBlank(vo.getToken())){
						resultmap.put("status","0");
						resultmap.put("msg","绑定成功");
						resultmap.put("token",vo.getToken());
						resultmap.put("uname",vo.getSafeUname());
					}else{
						resultmap.put("status","1017");
						resultmap.put("msg",Constants.getParamterkey("login.union.fail"));
					}
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
	
	@RequestMapping("/query.shtml")
	@ResponseBody
	public Object query(HttpServletRequest request,HttpServletResponse response){
		if(log.isInfoEnabled()){
			log.info("[RegFace.query.show] ==> is comming....");	
		}
		
		//通过解析request 获取参数 data,partnerid,service,time,version,key
		final	Map<String,String> requestdata = super.parseRequest(request);
		Map<String,String> resultmap = new HashMap<String,String>();
		resultmap.put("partnerid",requestdata.get("partnerid"));
		//验证参数是否合法。 0#cefp 
		String resultstr = super.validparameters(requestdata.get("data"),requestdata.get("partnerid"),requestdata.get("time"),requestdata.get("service"),requestdata.get("version"),requestdata.get("key"));
		
		if(log.isInfoEnabled()){
			log.info("[RegFace.apply.resultstr] ==> "+resultstr);	
		}
		//0#开头的为成功标识可以进行后续业务逻辑处理了。
		if(resultstr.startsWith("0#")){
			
			boolean passflag  = true;
			if(passflag && !"register".equalsIgnoreCase(requestdata.get("service"))){
				resultmap.put("status","9007");
				resultmap.put("msg",Constants.getParamterkey("common.param.service"));
				passflag = false;
			}
			//把传入参数 转化成一个java对象。后续处理。
			MembaseVo vo = JSON.parseObject(requestdata.get("data"),MembaseVo.class);
			vo.setPartnerid(requestdata.get("partnerid"));
				 String openid = vo.getOpenid();
				 if(passflag && (StringUtils.isBlank(openid))){
					 resultmap.put("status","9013");
					 resultmap.put("msg","openid"+Constants.getParamterkey("common.param.isnull"));
					 passflag = false;
				 }
				 if(passflag && StringUtils.isNotBlank(openid) && openid.length()>32){
					 resultmap.put("status","9016");
					 resultmap.put("msg","openid"+Constants.getParamterkey("common.param.maxlength"));
					 passflag = false;
				 }
				 String type = vo.getType();
				 if(passflag && StringUtils.isBlank(type)){
					 resultmap.put("status","9013");
					 resultmap.put("msg","type"+Constants.getParamterkey("common.param.isnull"));
					 passflag = false;
				 } 
				 if(passflag && StringUtils.isNotBlank(type) && !type.matches("^\\d{1,5}$")){
					 resultmap.put("status","9017");
					 resultmap.put("msg","type"+Constants.getParamterkey("common.param.notvalid"));
					 passflag = false;
				 }
				 if(passflag){
					 //实际的业务处理。
					 try {
						vo =  userService.doMemBindUser(vo);
						if(null!=vo && StringUtils.isNotBlank(vo.getToken())){
							resultmap.put("status","0");
							resultmap.put("msg",vo.getMsg());
							resultmap.put("token",vo.getToken());
							resultmap.put("uname",vo.getSafeUname());
						}
					} catch (ServiceException e) {
						log.info(e.getMessage(),e);
						resultmap.put("status","9999");
						resultmap.put("msg",Constants.getParamterkey("common.syserror"));
					}
				 }
		}else{
			String[] str = StringUtils.split(resultstr,"#");
			resultmap.put("status",str[0]);
			resultmap.put("msg",str[1]);
		}
		return super.callback(requestdata.get("callback"), resultmap, request, response);
	}
	
	@RequestMapping("/apply.shtml")
	@ResponseBody
	public Object apply(HttpServletRequest request,HttpServletResponse response){
		if(log.isInfoEnabled()){
			log.info("[RegFace.apply.show] ==> is comming....");	
		}
		
		//通过解析request 获取参数 data,partnerid,service,time,version,key
		final	Map<String,String> requestdata = super.parseRequest(request);
		Map<String,String> resultmap = new HashMap<String,String>();
		resultmap.put("partnerid",requestdata.get("partnerid"));
		//验证参数是否合法。 0#cefp 
		String resultstr = super.validparameters(requestdata.get("data"),requestdata.get("partnerid"),requestdata.get("time"),requestdata.get("service"),requestdata.get("version"),requestdata.get("key"));
		
		if(log.isInfoEnabled()){
			log.info("[RegFace.apply.resultstr] ==> "+resultstr);	
		}
		//0#开头的为成功标识可以进行后续业务逻辑处理了。
		if(resultstr.startsWith("0#")){
			
			boolean passflag  = true;
			if(passflag && !"register".equalsIgnoreCase(requestdata.get("service"))){
				resultmap.put("status","9007");
				resultmap.put("msg",Constants.getParamterkey("common.param.service"));
				passflag = false;
			}
			//把传入参数 转化成一个java对象。后续处理。
			ShopVo base = JSON.parseObject(requestdata.get("data"), ShopVo.class);
			if(passflag && StringUtils.isBlank(base.getShopname())){
				resultmap.put("status","1012");
				resultmap.put("msg",Constants.getParamterkey("login.reg.apply.shopname"));
				passflag = false;
			}
			if(passflag && base.getShopname().length()>10){
				resultmap.put("status","1004");
				resultmap.put("msg","shopname"+Constants.getParamterkey("login.reg.overlength"));
				passflag = false;
			}
			if(passflag && StringUtils.isNotBlank(base.getRealname()) && base.getRealname().length()>50){
				resultmap.put("status","1004");
				resultmap.put("msg","realname"+Constants.getParamterkey("login.reg.overlength"));
				passflag = false;
			} 
			if(passflag && (StringUtils.isBlank(base.getCardimg()))){
				resultmap.put("status","9013");
				resultmap.put("msg","carding"+Constants.getParamterkey("common.param.isnull"));
				passflag = false;
			}
			if(passflag && base.getCardimg().length()>20){
				resultmap.put("status","1004");
				resultmap.put("msg","cardimg"+Constants.getParamterkey("login.reg.overlength"));
				passflag = false;
			}
			
			if(passflag && (StringUtils.isBlank(base.getTelephone()) || !base.getTelephone().matches("^\\d{11}$"))){
				resultmap.put("status","1002");
				resultmap.put("msg","provider"+Constants.getParamterkey("login.reg.nomobile"));
				passflag = false;
			}
			if(passflag && StringUtils.isBlank(base.getKindscode())){
				resultmap.put("status","1013");
				resultmap.put("msg",Constants.getParamterkey("login.reg.apply.onekinds"));
				passflag = false;
			}
			if(passflag && StringUtils.isNotBlank(base.getKindscode()) && base.getKindscode().length()>100){
				resultmap.put("status","1004");
				resultmap.put("msg","kindscode"+Constants.getParamterkey("login.reg.overlength"));
				passflag = false;
			}
			if(passflag && StringUtils.isNotBlank(base.getKindscode())){
				String[] kinds = StringUtils.split(base.getKindscode(),"#");
				boolean f  = false;
				for(String k : kinds){
					if(Constants.kindsmap.get(k) == null){//非指定的经营种类
						f = true;
						break;
					}
				}
				if(f){
					resultmap.put("status","1016");
					resultmap.put("msg",Constants.getParamterkey("login.reg.apply.nokinds"));
					passflag = false;
				}
			}
			if(passflag && !"1".equals(base.getAgree())){
				resultmap.put("status","9014");
				resultmap.put("msg",Constants.getParamterkey("common.param.agree"));
				passflag = false;
			}
			String userid = super.getUserid(base.getToken());
			if(StringUtils.isBlank(userid)){
				resultmap.put("status","9010");
				resultmap.put("msg",Constants.getParamterkey("common.login.token"));
				passflag = false;
			}
			//common.shop.keys过滤敏感词
			String keys = Constants.getConfigkey("common.shop.keys");
			if(keys.indexOf(base.getShopname())>-1){
				resultmap.put("status","9015");
				resultmap.put("msg",Constants.getParamterkey("common.param.sensitive"));
				passflag = false;
			}
			if(passflag){
				TMemShop shop;
				try {
					shop = userService.findTMemShop(base.getShopname());
					if(shop != null ){
						resultmap.put("status","1015");
						resultmap.put("msg",Constants.getParamterkey("login.reg.apply.exitsshop"));
						passflag = false;
					}else{
						 base.setUserid(userid);
						 base = userService.createTMemShop(base);
						 resultmap.put("status","0");
						 resultmap.put("msg",Constants.getParamterkey("login.reg.apply.waitaduit"));
					}
				} catch (ServiceException e1) {
					log.info(e1.getMessage(),e1);
					resultmap.put("status","9999");
					resultmap.put("msg",Constants.getParamterkey("common.syserror"));
				}
				 
			}
		}else{
			String[] str = StringUtils.split(resultstr,"#");
			resultmap.put("status",str[0]);
			resultmap.put("msg",str[1]);
		}
		return super.callback(requestdata.get("callback"), resultmap, request, response);
	}
	
	@RequestMapping("/complete.shtml")
	@ResponseBody
	public Object complete(HttpServletRequest request,HttpServletResponse response){
		if(log.isInfoEnabled()){
			log.info("[RegFace.complete.show] ==> is comming....");	
		}
		//通过解析request 获取参数 data,partnerid,service,time,version,key
		final	Map<String,String> requestdata = super.parseRequest(request);
		Map<String,String> resultmap = new HashMap<String,String>();
		resultmap.put("partnerid",requestdata.get("partnerid"));
		//验证参数是否合法。 0#cefp 
		String resultstr = super.validparameters(requestdata.get("data"),requestdata.get("partnerid"),requestdata.get("time"),requestdata.get("service"),requestdata.get("version"),requestdata.get("key"));
		
		if(log.isInfoEnabled()){
			log.info("[RegFace.register.resultstr] ==> "+resultstr);	
		}
		//0#开头的为成功标识可以进行后续业务逻辑处理了。
		if(resultstr.startsWith("0#")){
			
			boolean passflag  = true;
			if(passflag && !"register".equalsIgnoreCase(requestdata.get("service"))){
				resultmap.put("status","9007");
				resultmap.put("msg",Constants.getParamterkey("common.param.service"));
				passflag = false;
			}
			//把传入参数 转化成一个java对象。后续处理。
			MembaseVo base = JSON.parseObject(requestdata.get("data"), MembaseVo.class);
			if(passflag && StringUtils.isNotBlank(base.getImgurl()) && base.getImgurl().length()>50){
				resultmap.put("status","1004");
				resultmap.put("msg","imgurl"+Constants.getParamterkey("login.reg.overlength"));
				passflag = false;
			}
			
			if(passflag && StringUtils.isNotBlank(base.getNickname()) && base.getNickname().length()>50){
				resultmap.put("status","1004");
				resultmap.put("msg","nickname"+Constants.getParamterkey("login.reg.overlength"));
				passflag = false;
			}
			
			if(passflag && StringUtils.isNotBlank(base.getBirthday()) && !base.getBirthday().matches("^\\d{4}-\\d{2}-\\d{2}$")){
				resultmap.put("status","1009");
				resultmap.put("msg","imgurl"+Constants.getParamterkey("login.reg.invalidformate"));
				passflag = false;
			}
			
			if(passflag && StringUtils.isNotBlank(base.getSex()) && !base.getSex().matches("^[10]$")){
				resultmap.put("status","1010");
				resultmap.put("msg","imgurl"+Constants.getParamterkey("login.reg.invalid"));
				passflag = false;
			}
			if(passflag && StringUtils.isNotBlank(base.getProvider()) && base.getProvider().length()>50){
				resultmap.put("status","1004");
				resultmap.put("msg","provider"+Constants.getParamterkey("login.reg.overlength"));
				passflag = false;
			}
			if(passflag && StringUtils.isNotBlank(base.getCity()) && base.getCity().length()>50){
				resultmap.put("status","1004");
				resultmap.put("msg","city"+Constants.getParamterkey("login.reg.overlength"));
				passflag = false;
			}
			if(passflag && StringUtils.isNotBlank(base.getAddress()) && base.getAddress().length()>50){
				resultmap.put("status","1004");
				resultmap.put("msg","city"+Constants.getParamterkey("login.reg.overlength"));
				passflag = false;
			}
			if(passflag && (StringUtils.isBlank(base.getToken()) || base.getToken().length()!=32)){
				resultmap.put("status","9009");
				resultmap.put("msg",Constants.getParamterkey("common.param.tokenInvalid"));
				passflag = false;
			}
			String userid = super.getUserid(base.getToken());
			if(StringUtils.isBlank(userid)){
				resultmap.put("status","9010");
				resultmap.put("msg",Constants.getParamterkey("common.login.token"));
				passflag = false;
			}
			if(passflag){
				try {
					 base.setUserid(userid);
					 userService.updateTMemBase(base);
					 resultmap.put("status","0");
					 resultmap.put("msg","操作成功");
				} catch (Exception e) {
					 log.info(e.getMessage(),e);
					 resultmap.put("status","9999");
					 resultmap.put("msg",Constants.getParamterkey("common.syserror"));
				}
			}
			
		}else{
			String[] str = StringUtils.split(resultstr,"#");
			resultmap.put("status",str[0]);
			resultmap.put("msg",str[1]);
		}
		
		return callback(requestdata.get("callback"), resultmap, request, response);
	}
	
	@RequestMapping("/register.shtml")
	@ResponseBody
	public Object register(Model model,HttpServletRequest request,HttpServletResponse response){
		if(log.isInfoEnabled()){
			log.info("[RegFace.register.show] ==> is comming....");	
		}
		//通过解析request 获取参数 data,partnerid,service,time,version,key
		final	Map<String,String> requestdata = super.parseRequest(request);
		
		 
		Map<String,String> resultmap = new HashMap<String,String>();
		try {
			resultmap.put("partnerid",requestdata.get("partnerid"));
			resultmap.put("status","0");
			
			
			//验证参数是否合法。 0#cefp 
			String resultstr = super.validparameters(requestdata.get("data"),requestdata.get("partnerid"),requestdata.get("time"),requestdata.get("service"),requestdata.get("version"),requestdata.get("key"));
			
			if(log.isInfoEnabled()){
				log.info("[RegFace.register.resultstr] ==> "+resultstr);	
			}
			//0#开头的为成功标识可以进行后续业务逻辑处理了。
			if(resultstr.startsWith("0#")){
				
				boolean passflag  = true;
				if(passflag && !"register".equalsIgnoreCase(requestdata.get("service"))){
					resultmap.put("status","9007");
					resultmap.put("msg",Constants.getParamterkey("common.param.service"));
					passflag = false;
				}
				
				
				//把传入参数 转化成一个java对象。后续处理。
				MembaseVo base = JSON.parseObject(requestdata.get("data"), MembaseVo.class);
				
				//用户名空判断
				if(passflag && (StringUtils.isBlank(base.getUname()))){
					resultmap.put("status","1005");
					resultmap.put("msg",Constants.getParamterkey("login.reg.userisnull"));
					passflag = false;
				}
				
				//用户名正则判断11位长度且符合手机号规则
				if(passflag && StringUtils.isNotBlank(base.getUname()) && (!base.getUname().matches("^1\\d{10}$"))){
					resultmap.put("status","1002");
					resultmap.put("msg",Constants.getParamterkey("login.reg.nomobile"));
					passflag = false;
				}
				//二次密码匹配且长度为加密后的32位。
				if(passflag && (base.getPwd().length()!= 32 || !base.getPwd().equalsIgnoreCase(base.getPwd2()))){
					resultmap.put("status","1003");
					resultmap.put("msg",Constants.getParamterkey("login.reg.pwdnomatch"));
					passflag = false;
				}
				
				//长度校验
				if(passflag &&  StringUtils.isNotBlank(base.getSource()) && base.getSource().length()>20){
					resultmap.put("status","1004");
					resultmap.put("msg","source"+Constants.getParamterkey("login.reg.overlength"));
					passflag = false;
				}
				
				if(passflag &&  StringUtils.isNotBlank(base.getComment()) && base.getComment().length()>20){
					resultmap.put("status","1004");
					resultmap.put("msg","comment"+Constants.getParamterkey("login.reg.overlength"));
					passflag = false;
				}
				if(passflag &&  StringUtils.isNotBlank(base.getRequestip()) && base.getRequestip().length()>20){
					resultmap.put("status","1004");
					resultmap.put("msg","requestip"+Constants.getParamterkey("login.reg.overlength"));
					passflag = false;
				}
				if(passflag &&  StringUtils.isNotBlank(base.getRequesturl()) && base.getRequesturl().length()>50){
					resultmap.put("status","1004");
					resultmap.put("msg","requesturl"+Constants.getParamterkey("login.reg.overlength"));
					passflag = false;
				}
				TMemBase o = userService.getUserByName(base.getUname().toLowerCase());
				if(passflag && null != o){
					resultmap.put("status","1001");
					resultmap.put("msg",Constants.getParamterkey("login.reg.unameexists"));
					passflag = false;
				}
				//common.shop.keys过滤敏感词
				String keys = Constants.getConfigkey("common.shop.keys");
				if(keys.indexOf(base.getUname())>-1){
					resultmap.put("status","9015");
					resultmap.put("msg",Constants.getParamterkey("common.param.sensitive"));
					passflag = false;
				}
				if(passflag){
					//service处理业务逻辑。TMemBase
					TMemBase mbase = new TMemBase();
					mbase.setAccountflag("0");
					mbase.setChannel(base.getSource()==null?"":base.getSource());
					mbase.setChannelno(base.getComment()==null?"":base.getComment());
					mbase.setLevels(0);
					mbase.setRegtime(new Date());
					mbase.setRegurl(base.getRequesturl());
					mbase.setRegip(base.getRequestip());
					mbase.setTelphone(base.getUname());
					mbase.setUsername(base.getUname().toLowerCase());
					mbase.setPasswd(base.getPwd().toLowerCase());
					mbase =  (TMemBase) userService.saveTMemBase(mbase);
					resultmap.put("msg","注册成功");
				}
				 //参数校验未通过。
			}else{
				String[] str = StringUtils.split(resultstr,"#");
				resultmap.put("status",str[0]);
				resultmap.put("msg",str[1]);
			}
			
		} catch (Exception e) {
			resultmap.put("status","9999");
			resultmap.put("msg",Constants.getParamterkey("common.syserror"));
		}
		
		
		String rkey  = super.md5str(resultmap);
		resultmap.put("key",rkey);
		
		if(log.isInfoEnabled()){
			log.info("[RegFace.register.resultmap] ==> "+resultmap.toString());	
		}
		//做一个预留，允许前端使用jsonp 方式访问
		String callback  = requestdata.get("callback");
		return super.callback(callback, resultmap, request, response);
	}
	 
}
