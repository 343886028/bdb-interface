package com.bdbvip.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the t_mem_pay_record database table.
 * 
 */
@Entity
@Table(name="t_mem_pay_record")
@NamedQuery(name="TMemPayRecord.findAll", query="SELECT t FROM TMemPayRecord t")
public class TMemPayRecord implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private Integer payid;
	private String bankno;
	private String couponno;
	private Date createtime;
	private String para1;
	private String paybankcode;
	private String paycode;
	private BigDecimal paymoney;
	private String payorderno;
	private String payplatform;
	private String paysrc;
	private String paystatus;
	private String platno;
	private Date updatetime;
	private Integer userid;
	private String username;

	public TMemPayRecord() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getPayid() {
		return this.payid;
	}

	public void setPayid(Integer payid) {
		this.payid = payid;
	}


	public String getBankno() {
		return this.bankno;
	}

	public void setBankno(String bankno) {
		this.bankno = bankno;
	}


	public String getCouponno() {
		return this.couponno;
	}

	public void setCouponno(String couponno) {
		this.couponno = couponno;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}


	public String getPara1() {
		return this.para1;
	}

	public void setPara1(String para1) {
		this.para1 = para1;
	}


	public String getPaybankcode() {
		return this.paybankcode;
	}

	public void setPaybankcode(String paybankcode) {
		this.paybankcode = paybankcode;
	}


	public String getPaycode() {
		return this.paycode;
	}

	public void setPaycode(String paycode) {
		this.paycode = paycode;
	}


	public BigDecimal getPaymoney() {
		return this.paymoney;
	}

	public void setPaymoney(BigDecimal paymoney) {
		this.paymoney = paymoney;
	}


	public String getPayorderno() {
		return this.payorderno;
	}

	public void setPayorderno(String payorderno) {
		this.payorderno = payorderno;
	}


	public String getPayplatform() {
		return this.payplatform;
	}

	public void setPayplatform(String payplatform) {
		this.payplatform = payplatform;
	}


	public String getPaysrc() {
		return this.paysrc;
	}

	public void setPaysrc(String paysrc) {
		this.paysrc = paysrc;
	}


	public String getPaystatus() {
		return this.paystatus;
	}

	public void setPaystatus(String paystatus) {
		this.paystatus = paystatus;
	}


	public String getPlatno() {
		return this.platno;
	}

	public void setPlatno(String platno) {
		this.platno = platno;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}


	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}


	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}