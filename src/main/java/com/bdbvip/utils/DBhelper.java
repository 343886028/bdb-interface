package com.bdbvip.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.bdbvip.entity.TSysPartner;


public class DBhelper {
   
   public static Connection conn= null ;
   public static PreparedStatement  ps = null;
   public static ResultSet rs = null;
	
   // get connection
   public static Connection getConn(){
	   try{
			Context initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:comp/env");
			 DataSource ds = (DataSource) envContext.lookup("jdbc/bdbvip");
			Connection conn = ds.getConnection();
			return conn;
	   }catch(Exception e){
		   e.printStackTrace();
	   }
	   return null;
   }
   public static Map<String,TSysPartner> intiPartnerMap(){
	   Map<String,TSysPartner> map = new HashMap<String,TSysPartner>();   
	   	if(conn==null){
	   		conn = getConn();
	   	}
	   	TSysPartner tp = null;
	   	try{
	   		String sql = "select * from t_sys_partner where status = '1' ";
	   		ps = conn.prepareStatement(sql);
	   		rs = ps.executeQuery();
	   		while(rs.next()){
	   			tp = new TSysPartner();
	   			tp.setConnect(rs.getString("connect"));
	   			tp.setId(rs.getInt("id"));
	   			tp.setPartnername(rs.getString("partnername"));
	   			tp.setPartnerpasswd(rs.getString("partnerpasswd"));
	   			tp.setSignstr(rs.getString("signstr"));
	   			tp.setName(rs.getString("name"));
	   			map.put(tp.getPartnername(),tp);
	   		}
	   		return map ;
	   	}catch(Exception e){
	   		e.printStackTrace();
	   	}finally{
	   		try {
					if (rs != null) {
						rs.close();
						rs = null;
					}
					if (ps != null) {
						ps.close();
						ps = null;
					}
					if (conn != null) {
						conn.close();
						conn = null;
					}
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
	   	}
	   	return map ;
   }
   public static Map<String,String> initParameMap(){
   	Map<String,String> map = new HashMap<String,String>();   
   	if(conn==null){
   		conn = getConn();
   	}
   	try{
   		String sql = "select * from t_sys_dict where status = '1' ";
   		ps = conn.prepareStatement(sql);
   		rs = ps.executeQuery();
   		while(rs.next()){
   			map.put( rs.getString("dictname") , rs.getString("dictvalue"));
   		}
   		return map ;
   	}catch(Exception e){
   		e.printStackTrace();
   	}finally{
   		try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
   	}
   	return map ;
  }
   //init config map
   public static Map<String,String> initConfigMap(){
    	Map<String,String> map = new HashMap<String,String>();   
    	if(conn==null){
    		conn = getConn();
    	}
    	String sql = "select * from t_sys_config where status = '1' ";
    	try{
    		ps = conn.prepareStatement(sql);
    		rs = ps.executeQuery();
    		while(rs.next()){
    			map.put( rs.getString("keyname") , rs.getString("keyvalue"));
    		}
    		Constants.configmap = map;
    		rs.close();
    		map = new HashMap<String,String>();
    		
    		sql = "select * from t_sys_dict where status = '1' ";
    		ps = conn.prepareStatement(sql);
       		rs = ps.executeQuery();
       		while(rs.next()){
       			map.put( rs.getString("dictname") , rs.getString("dictvalue"));
       		}
       		Constants.parametermap = map;
       		
       		rs.close();
       		
       	    Map<String,TSysPartner>	maps = new HashMap<String,TSysPartner>();
       		sql = "select * from t_sys_partner where status = '1' ";
	   		ps = conn.prepareStatement(sql);
	   		rs = ps.executeQuery();
	   		
	   		TSysPartner tp = null;
	   		while(rs.next()){
	   			tp = new TSysPartner();
	   			tp.setConnect(rs.getString("connect"));
	   			tp.setId(rs.getInt("id"));
	   			tp.setPartnername(rs.getString("partnername"));
	   			tp.setPartnerpasswd(rs.getString("partnerpasswd"));
	   			tp.setSignstr(rs.getString("signstr"));
	   			tp.setName(rs.getString("name"));
	   			maps.put(tp.getPartnername(),tp);
	   		}
       		Constants.partnermap = maps;
    		
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (ps != null) {
					ps.close();
					ps = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
    	}
    	return map ;
   }
}
