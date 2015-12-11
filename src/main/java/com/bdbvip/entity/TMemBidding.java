package com.bdbvip.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the t_mem_bidding database table.
 * 
 */
@Entity
@Table(name="t_mem_bidding")
@NamedQuery(name="TMemBidding.findAll", query="SELECT t FROM TMemBidding t")
public class TMemBidding implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer activeid;
	private String activename;
	private BigDecimal amount;
	private Integer configid;
	private Date createtime;
	private String procode;
	private Integer productid;
	private String productname;
	private String status;
	private String type;
	private Integer userid;

	public TMemBidding() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getActiveid() {
		return this.activeid;
	}

	public void setActiveid(Integer activeid) {
		this.activeid = activeid;
	}


	public String getActivename() {
		return this.activename;
	}

	public void setActivename(String activename) {
		this.activename = activename;
	}


	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}


	public Integer getConfigid() {
		return this.configid;
	}

	public void setConfigid(Integer configid) {
		this.configid = configid;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}


	public String getProcode() {
		return this.procode;
	}

	public void setProcode(String procode) {
		this.procode = procode;
	}


	public Integer getProductid() {
		return this.productid;
	}

	public void setProductid(Integer productid) {
		this.productid = productid;
	}


	public String getProductname() {
		return this.productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}


	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

}