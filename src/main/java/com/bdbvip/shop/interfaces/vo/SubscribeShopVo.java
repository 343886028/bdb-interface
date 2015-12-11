package com.bdbvip.shop.interfaces.vo;

import java.util.Date;

import com.bdbvip.pojo.BasePageVo;

public class SubscribeShopVo extends BasePageVo{
	
	private static final long serialVersionUID = -7613445086099828086L;
	private Integer id;
	private Date createtime;
	private Integer fromuserid;
	private String status;
	private Integer touser;
	private Date updatetime;
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
	public Integer getFromuserid() {
		return fromuserid;
	}
	public void setFromuserid(Integer fromuserid) {
		this.fromuserid = fromuserid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getTouser() {
		return touser;
	}
	public void setTouser(Integer touser) {
		this.touser = touser;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
}
