package com.bdbvip.login;

import static org.junit.Assert.fail;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.bdbvip.SpringTest;
import com.bdbvip.entity.TMemBase;
import com.bdbvip.pojo.MembaseVo;
import com.bdbvip.user.service.UserService;
import com.bdbvip.utils.Md5Util;
import com.bdbvip.utils.common.exception.ServiceException;

public class LoginTest extends SpringTest {

	 @Test
	public void saveUserTest() {
		 UserService userService = (UserService) super.getBean("userService");
		TMemBase mbase = new TMemBase();
		mbase.setAccountflag("0");
		mbase.setChannel("");
		mbase.setChannelno("");
		mbase.setLevels(0);
		mbase.setRegtime(new Date());
		mbase.setRegurl("");
		mbase.setRegip("");
		mbase.setTelphone("张三李四王五");
		mbase.setUsername("张三李四王五");
		mbase.setPasswd(Md5Util.md5_32("123456").toLowerCase());
		
		Assert.assertEquals("张三李四王五", mbase.getUsername());
		
		try {
			//mbase = (TMemBase) userService.saveTMemBase(mbase);
			System.out.println("---没有抛出异常。。。");
		}   catch (Exception e) {
			 System.out.println("抛出异常。。"+e.getMessage());
		}
		 
		//Assert.assertNotNull("id",mbase.getId());
		System.out.println("-------------------mbase="+mbase.getId());
	}
	 
	 @Test
	 public void unionloginTest(){
//		 UserService userService = (UserService) super.getBean("userService");
//		 MembaseVo vo = new MembaseVo();
//		 vo.setUname("ad sdfsd dsfd ");
//		 vo.setOpenid("12143345356464576");
//		 vo.setSource("aa");
//		 vo.setRequestip("11");
//		 vo.setRequesturl("122");
//		 vo.setComment("132");
//		 vo.setPwd(Md5Util.md5_32("123456"));
//		 vo.setType("0");
//		 try {
//			vo = userService.doUnion(vo);
//			System.out.println(vo.getToken()+"--uname="+vo.getUname()+"-userid--"+vo.getUserid());
//			fail(vo.getToken()+"--uname="+vo.getUname()+"-userid--"+vo.getUserid());
//		} catch (ServiceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		 
		 Map<String,String> datamap = new HashMap<String,String>();
		 datamap.put("openid",Md5Util.md5_32("abcdefg"));
		 datamap.put("type","1");
		 datamap.put("uname","张三李四王五");
		 datamap.put("mobile","");
		 datamap.put("pwd",Md5Util.md5_32("123456"));
		 datamap.put("requesturl","a");
		 datamap.put("requestip","b");
		 datamap.put("source","c");
		 datamap.put("comment","d");
		 datamap.put("source","c");
		 
	 }
	 
	 @Test
	 public void queryTest(){
		 
	 }
}
