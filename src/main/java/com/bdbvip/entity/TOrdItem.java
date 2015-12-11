package com.bdbvip.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the t_ord_item database table.
 * 
 */
@Entity
@Table(name="t_ord_item")
@NamedQuery(name="TOrdItem.findAll", query="SELECT t FROM TOrdItem t")
public class TOrdItem implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private Integer orderitemid;
	private Integer count;
	private Integer fromuserid;
	private BigDecimal itemmoney;
	private BigDecimal offmoney;
	private String orderno;
	private String pointflag;
	private BigDecimal price;
	private String procode;
	private String proname;
	private String receivevokeflag;
	private String revokestatus;
	private Date revoketime;
	private String receiveflag;



	public TOrdItem() {
	}


	@Id
	@GenericGenerator(name="idGenerator", strategy="native")
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "idGenerator")
	public Integer getOrderitemid() {
		return this.orderitemid;
	}

	public void setOrderitemid(Integer orderitemid) {
		this.orderitemid = orderitemid;
	}


	public Integer getCount() {
		return this.count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}


	public Integer getFromuserid() {
		return this.fromuserid;
	}

	public void setFromuserid(Integer fromuserid) {
		this.fromuserid = fromuserid;
	}


	public BigDecimal getItemmoney() {
		return this.itemmoney;
	}

	public void setItemmoney(BigDecimal itemmoney) {
		this.itemmoney = itemmoney;
	}


	public BigDecimal getOffmoney() {
		return this.offmoney;
	}

	public void setOffmoney(BigDecimal offmoney) {
		this.offmoney = offmoney;
	}


	public String getOrderno() {
		return this.orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}


	public String getPointflag() {
		return this.pointflag;
	}

	public void setPointflag(String pointflag) {
		this.pointflag = pointflag;
	}


	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}


	public String getProcode() {
		return this.procode;
	}

	public void setProcode(String procode) {
		this.procode = procode;
	}


	public String getProname() {
		return this.proname;
	}

	public void setProname(String proname) {
		this.proname = proname;
	}


	public String getReceivevokeflag() {
		return this.receivevokeflag;
	}

	public void setReceivevokeflag(String receivevokeflag) {
		this.receivevokeflag = receivevokeflag;
	}


	public String getRevokestatus() {
		return this.revokestatus;
	}

	public void setRevokestatus(String revokestatus) {
		this.revokestatus = revokestatus;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getRevoketime() {
		return this.revoketime;
	}

	public void setRevoketime(Date revoketime) {
		this.revoketime = revoketime;
	}
	
	public String getReceiveflag() {
		return receiveflag;
	}


	public void setReceiveflag(String receiveflag) {
		this.receiveflag = receiveflag;
	}

}