package com.bdbvip.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the t_ord_base database table.
 * 
 */
@Entity
@Table(name="t_ord_base")
@NamedQuery(name="TOrdBase.findAll", query="SELECT t FROM TOrdBase t")
public class TOrdBase implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private Integer orderid;
	private Integer addressid;
	private Date createtime;
	private BigDecimal oldordermoney;
	private BigDecimal ordermoney;
	private String orderno;
	private String orderstatus;
	private String paystatus;
	private String poIntegerflag;
	private String receiveflag;
	private Date receivetime;
	private String revokemoneyflag;
	private String revokestatus;
	private String sendflag;
	private BigDecimal sendmoney;
	private String sendname;
	private String sendno;
	private Date sendtime;
	private Integer touserid;
	private Date updatetime;
	private Integer userid;
	private String delayreceiveflag;
	private Integer delaydays;

	public TOrdBase() {
	}


	@Id
	@GenericGenerator(name="idGenerator", strategy="native")
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "idGenerator")
	public Integer getOrderid() {
		return this.orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}


	public Integer getAddressid() {
		return this.addressid;
	}

	public void setAddressid(Integer addressid) {
		this.addressid = addressid;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}


	public BigDecimal getOldordermoney() {
		return this.oldordermoney;
	}

	public void setOldordermoney(BigDecimal oldordermoney) {
		this.oldordermoney = oldordermoney;
	}


	public BigDecimal getOrdermoney() {
		return this.ordermoney;
	}

	public void setOrdermoney(BigDecimal ordermoney) {
		this.ordermoney = ordermoney;
	}


	public String getOrderno() {
		return this.orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}


	public String getOrderstatus() {
		return this.orderstatus;
	}

	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}


	public String getPaystatus() {
		return this.paystatus;
	}

	public void setPaystatus(String paystatus) {
		this.paystatus = paystatus;
	}


	public String getPoIntegerflag() {
		return this.poIntegerflag;
	}

	public void setPoIntegerflag(String poIntegerflag) {
		this.poIntegerflag = poIntegerflag;
	}


	public String getReceiveflag() {
		return this.receiveflag;
	}

	public void setReceiveflag(String receiveflag) {
		this.receiveflag = receiveflag;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getReceivetime() {
		return this.receivetime;
	}

	public void setReceivetime(Date receivetime) {
		this.receivetime = receivetime;
	}


	public String getRevokemoneyflag() {
		return this.revokemoneyflag;
	}

	public void setRevokemoneyflag(String revokemoneyflag) {
		this.revokemoneyflag = revokemoneyflag;
	}


	public String getRevokestatus() {
		return this.revokestatus;
	}

	public void setRevokestatus(String revokestatus) {
		this.revokestatus = revokestatus;
	}


	public String getSendflag() {
		return this.sendflag;
	}

	public void setSendflag(String sendflag) {
		this.sendflag = sendflag;
	}


	public BigDecimal getSendmoney() {
		return this.sendmoney;
	}

	public void setSendmoney(BigDecimal sendmoney) {
		this.sendmoney = sendmoney;
	}


	public String getSendname() {
		return this.sendname;
	}

	public void setSendname(String sendname) {
		this.sendname = sendname;
	}


	public String getSendno() {
		return this.sendno;
	}

	public void setSendno(String sendno) {
		this.sendno = sendno;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getSendtime() {
		return this.sendtime;
	}

	public void setSendtime(Date sendtime) {
		this.sendtime = sendtime;
	}


	public Integer getTouserid() {
		return this.touserid;
	}

	public void setTouserid(Integer touserid) {
		this.touserid = touserid;
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
	
	public String getDelayreceiveflag() {
		return delayreceiveflag;
	}


	public void setDelayreceiveflag(String delayreceiveflag) {
		this.delayreceiveflag = delayreceiveflag;
	}


	public Integer getDelaydays() {
		return delaydays;
	}


	public void setDelaydays(Integer delaydays) {
		this.delaydays = delaydays;
	}

}