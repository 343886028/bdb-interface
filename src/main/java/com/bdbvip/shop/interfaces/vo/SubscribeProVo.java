package com.bdbvip.shop.interfaces.vo;

import java.util.Date;

import com.bdbvip.pojo.BasePageVo;

public class SubscribeProVo extends BasePageVo{
	
	private static final long serialVersionUID = -4237457788532255413L;
	private Integer id;
	private Date createtime;
	private String procode;
	private String shopname;
	private String status;
	private Integer touserid;
	private Date updatetime;
	private Integer userid;
	
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
}
