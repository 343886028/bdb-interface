package com.bdbvip.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.bdbvip.entity.TSysPartner;

public class Constants {
	private static Logger log = Logger.getLogger(Constants.class);
	public static String PARTNER = "_goal.partner";
	/**
	 * 短信验证码保存有效时间，10分钟.
	 */
	public static int SMS_MAX_VALID = 10;//短信有效保存时间 10分钟
	/**
	 * 短信验证码有效签名串
	 */
	public static String SMS_SIGSTR="1243434QWWdxcw!@#$";
	
	
	//全局的系统参数
	public static Map<String,String> configmap = new HashMap<String,String>();
	
	//全局的对外提示信息或异常参数都从数据库读取。
	public static Map<String, String> parametermap = new HashMap<String, String>();
	
	//全局商家partnerid
	public static Map<String,TSysPartner> partnermap = new HashMap<String, TSysPartner>();
	
	private static long ptime = System.currentTimeMillis();
	
	public static TSysPartner getPartner(String key){
		if(partnermap == null || System.currentTimeMillis()-ptime > 10 * 60 *1000){
			partnermap = DBhelper.intiPartnerMap();
			if(log.isInfoEnabled()){
				log.info("【Constats.getPartner.size】=>"+partnermap.size());
			}
		}
		return partnermap.get(key);
	}
	
	public static String getParamterkey(String key){
		return parametermap.get(key);
	}
	
	public static String getConfigkey(String key){
		return configmap.get(key);
	}
	
	public static Map<String,String> kindsmap = new HashMap<String,String>();
	static{
		kindsmap.put("1","黄金铂金");
		kindsmap.put("2","钻石");kindsmap.put("3","翡翠");
		kindsmap.put("4","和田玉");kindsmap.put("5","彩宝");
		kindsmap.put("6","琥珀蜜蜡");kindsmap.put("7","珍珠");
		kindsmap.put("8","天然玉石");kindsmap.put("9","银饰");
		kindsmap.put("10","文玩");kindsmap.put("99","其他");
		
	}
}
