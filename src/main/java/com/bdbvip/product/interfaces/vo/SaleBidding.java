package com.bdbvip.product.interfaces.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.bdbvip.utils.common.date.DateUtil;

public class SaleBidding {
	private String username;
	private Integer id;
	private Integer type;
	private BigDecimal amount;
	private String procode;
	private Date createtime;
	private Integer userid;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getProcode() {
		return procode;
	}
	public void setProcode(String procode) {
		this.procode = procode;
	}
	
	/*public String getCreatetime() {
		if (createtime!=null) {
			return DateUtil.simpleDateFormat3.format(createtime);
		}
		return "";
	}*/
	public Date getCreatetime(){
		  return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	
	public SaleBidding() {
	}
	public SaleBidding(String username, Integer id, Integer type, BigDecimal amount, String procode, Date createtime,
			Integer userid) {
		super();
		this.username = username;
		this.id = id;
		this.type = type;
		this.amount = amount;
		this.procode = procode;
		this.createtime = createtime;
		this.userid = userid;
	}
	
	

}
