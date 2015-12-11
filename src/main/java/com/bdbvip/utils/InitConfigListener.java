package com.bdbvip.utils;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

public class InitConfigListener extends ContextLoaderListener {
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		System.out.println("--------contextInitialized......");
		super.contextInitialized(event);
		try {
			 
				
			DBhelper.initConfigMap();
			System.out.println("----------config&parameter....inited-["+Constants.configmap.size()+"|"+Constants.parametermap.size()+"]");
			
			MemcacheUtil.init();
			
			System.out.println("-------TSysPartner....inited-["+Constants.partnermap.size()+"]");
			MemcacheUtil.put(Constants.PARTNER, Constants.partnermap);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
