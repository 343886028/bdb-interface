package com.bdbvip.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * 交易工具类
  * @ClassName: TradUtil
  * @Description: TODO
  * @date 2015年11月26日 上午10:42:14
 */
public class TradUtil {
	
	/**
	 *  获取交易编号 
	  * @Title: getTradingNo
	  * @param  tradType 交易类型  长度不能超过4个字节
	  * @return String   交易编号
	  * @throws
	 */
	public static String getTradingNo(String tradType) {
		StringBuffer sBuffer = new StringBuffer(26);
		if(StringUtils.isBlank(tradType)){
			tradType = "";
		}
		if(tradType.length()>4){
			throw new ArrayIndexOutOfBoundsException();
		}
		sBuffer.append(tradType).append(new SimpleDateFormat("yyMMddHHmmss").format(new Date()))
				.append(TradUtil.getRandom(6));
		return sBuffer.toString();
	}
	
	/**
	 * 根据短信类型，返回对应的短信模版。
	 * 00 代表6位数字短信验证码。
	 * 
	 * @param smstype
	 * @param code
	 * @return
	 */
	public static String getSms(String smstype,String code){
		if(StringUtils.isBlank(code)){
			code = TradUtil.getRandom(6);
		}
		String sms = "";
		if("00".equals(smstype)){
			 sms = Constants.getConfigkey("login.valid.sms").replaceAll("xxx", code);
		}
		if(StringUtils.isBlank(sms)){
			return  "xxx,请您不要透露验证码给任何人。【百多宝】".replaceAll("xxx", code);
		}
		return sms;
	}
	
	/**
	 * 生成len 位随机整数
	 * 最小1位，最大30位
	  * @Title: getSexRandom
	  * @return int    
	  * @throws
	 */
	public static String getRandom(int len){
		if (len<1) {
			len = 1;
		}
		if(len>30){
			len = 30;
		}
		double f = (Math.random()*9+1);
		long p= pow2(BigInteger.valueOf(10), len-1).longValue();
		BigDecimal big = BigDecimal.valueOf(f).multiply(BigDecimal.valueOf(p));
		return String.valueOf(big).replaceAll("[-|.]", "").substring(0, len);
	}		
	public static BigInteger pow2(BigInteger x,int n){  
	    if(n==0)  
	        return BigInteger.valueOf(1);  
	    else {  
	        if(n%2==0)  
	            return pow2(x.multiply(x), n/2);  
	        else   
	            return pow2(x.multiply(x), (n-1)/2).multiply(x);  
	          
	    }  
	      
	} 
	
	/**
	 * 验证是否是金额
	  * @Title: isMoney
	  * @param  str
	  * @param @return 
	  * @return boolean    
	  * @throws
	 */
	
	public static boolean isMoney(String str) {
		Pattern pattern = java.util.regex.Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$"); // 判断小数点后一位的数字的正则表达式
		java.util.regex.Matcher match = pattern.matcher(str);
		if (match.matches() == false) {
			return false;
		} else {
			return true;
		}

	}
}
