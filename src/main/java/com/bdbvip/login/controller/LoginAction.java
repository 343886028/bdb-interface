package com.bdbvip.login.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.bdbvip.entity.TSysPartner;
import com.bdbvip.login.service.LoginService;
import com.bdbvip.utils.common.exception.ServiceException;

@Controller
@RequestMapping("/login")
public class LoginAction {

	
	@Autowired
	LoginService loginService;
	
	@RequestMapping("/index.shtml")
	public ModelAndView  index(Model model,HttpServletRequest request,HttpServletResponse response){
		System.out.println("----------");
		
		//TSysConfig config = new TSysConfig();
//		config.setKeyname("login.login.validstr");
//		config.setKeyvalue("12435347568679qwweryrtyuuyosfdSDFGSD@!!&*!#Q&@");
		//config.setStatus("1");
		//Page page = new Page();
		
		try {
			//page = loginService.findAllByStatus(config, page);
			//System.out.println("--search--"+page.getTotalCount());
			
			//config.setKeyname("login.reg.validcodestr");
			//config.setKeyvalue("20151118-!@#$1234689QWewqdsdfs");
			
			//loginService.saveEntity(config);
			//System.out.println("config--"+config.getId());
			TSysPartner p = new TSysPartner();
			p.setConnect("liu");
			p.setName("liu");
			p.setPartnername("bdbvip");
			p.setPartnerpasswd("1234567890");
			p.setSignstr("1234567890!@#$%^&*");
			p.setStatus("1");
			loginService.saveEntity(p);
			
			System.out.println("p--"+p.getId());
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		ModelAndView mv = new ModelAndView();
        mv.setViewName("login/index");
		return mv;
	}
	
	@RequestMapping("/login.shtml")
	public String login(Model model,HttpServletRequest request,HttpServletResponse response){
		System.out.println("=========");
		return "login/login";
	}
}
