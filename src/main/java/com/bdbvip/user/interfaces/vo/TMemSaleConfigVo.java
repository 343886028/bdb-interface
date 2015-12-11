package com.bdbvip.user.interfaces.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.bdbvip.pojo.BasePageVo;

public class TMemSaleConfigVo extends BasePageVo {

	private static final long serialVersionUID = -5440084286101278115L;
	private Integer id;
	private Date createtime;
	private Date endtime;
	private BigDecimal maxvalues;
	private String productcode;
	private Integer touserid;
	private Integer userid;
	private String username;
	private BigDecimal price;
	private String flag;

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

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public BigDecimal getMaxvalues() {
		return maxvalues;
	}

	public void setMaxvalues(BigDecimal maxvalues) {
		this.maxvalues = maxvalues;
	}

	public String getProductcode() {
		return productcode;
	}

	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}

	public Integer getTouserid() {
		return touserid;
	}

	public void setTouserid(Integer touserid) {
		this.touserid = touserid;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

}
