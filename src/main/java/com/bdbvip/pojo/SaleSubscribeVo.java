package com.bdbvip.pojo;

import java.util.Date;

public class SaleSubscribeVo extends BasePageVo {
	private static final long serialVersionUID = 5916254693375281098L;
	private Integer id;
	private Date createtime;
	private String procode;
	private String shopname;
	private String subflag;
	
	/**
	 * 被订阅用户id
	 */
	private Integer touserid;
	
	private Date updatetime;
	/**
	 * 订阅用户id
	 */
	private Integer userid;
	private Integer shopid;
	private String status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getProcode() {
		return procode;
	}

	public void setProcode(String procode) {
		this.procode = procode;
	}

	public String getShopname() {
		return shopname;
	}

	public void setShopname(String shopname) {
		this.shopname = shopname;
	}


	public String getSubflag() {
		return subflag;
	}

	public void setSubflag(String subflag) {
		this.subflag = subflag;
	}

	public Integer getTouserid() {
		return touserid;
	}

	public void setTouserid(Integer touserid) {
		this.touserid = touserid;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	
	
	
	public Integer getShopid() {
		return shopid;
	}

	public void setShopid(Integer shopid) {
		this.shopid = shopid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public static String getSUBSCRIBE_TRUE() {
		return SUBSCRIBE_TRUE;
	}

	public static void setSUBSCRIBE_TRUE(String sUBSCRIBE_TRUE) {
		SUBSCRIBE_TRUE = sUBSCRIBE_TRUE;
	}

	public static String getSUBSCRIBE_FALSE() {
		return SUBSCRIBE_FALSE;
	}

	public static void setSUBSCRIBE_FALSE(String sUBSCRIBE_FALSE) {
		SUBSCRIBE_FALSE = sUBSCRIBE_FALSE;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}




	/**
	 * 订阅
	 */
	public static String SUBSCRIBE_TRUE="0";
	
	/**
	 * 取消订阅
	 */
	public static String SUBSCRIBE_FALSE="1";

}
