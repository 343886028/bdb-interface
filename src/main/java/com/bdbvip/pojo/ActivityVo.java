package com.bdbvip.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class ActivityVo extends BasePageVo{

	private static final long serialVersionUID = -2158143579298350293L;
	private Integer id;
	private String activename;
	private BigDecimal addprice;
	private String aduitflag;
	private Date createtime;
	private String descs;
	private Date endtime;
	private String frouser;
	private BigDecimal frousermoney;
	private BigDecimal joinmoney;
	private String optuser;
	private BigDecimal price;
	private String remark;
	private Date starttime;
	private String status;
	private String type;
	private Integer proid;
	private String procode;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getActivename() {
		return activename;
	}
	public void setActivename(String activename) {
		this.activename = activename;
	}
	public BigDecimal getAddprice() {
		return addprice;
	}
	public void setAddprice(BigDecimal addprice) {
		this.addprice = addprice;
	}
	public String getAduitflag() {
		return aduitflag;
	}
	public void setAduitflag(String aduitflag) {
		this.aduitflag = aduitflag;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getDescs() {
		return descs;
	}
	public void setDescs(String descs) {
		this.descs = descs;
	}
	public Date getEndtime() {
		return endtime;
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	public String getFrouser() {
		return frouser;
	}
	public void setFrouser(String frouser) {
		this.frouser = frouser;
	}
	public BigDecimal getFrousermoney() {
		return frousermoney;
	}
	public void setFrousermoney(BigDecimal frousermoney) {
		this.frousermoney = frousermoney;
	}
	public BigDecimal getJoinmoney() {
		return joinmoney;
	}
	public void setJoinmoney(BigDecimal joinmoney) {
		this.joinmoney = joinmoney;
	}
	public String getOptuser() {
		return optuser;
	}
	public void setOptuser(String optuser) {
		this.optuser = optuser;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getStarttime() {
		return starttime;
	}
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getProid() {
		return proid;
	}
	public void setProid(Integer proid) {
		this.proid = proid;
	}
	public String getProcode() {
		return procode;
	}
	public void setProcode(String procode) {
		this.procode = procode;
	}
}
