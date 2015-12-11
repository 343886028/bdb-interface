package com.bdbvip.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the t_prod_active database table.
 * 
 */
@Entity
@Table(name="t_prod_active")
@NamedQuery(name="TProdActive.findAll", query="SELECT t FROM TProdActive t")
public class TProdActive implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String activename;
	private BigDecimal addprice;
	private String aduitflag;
	private Date createtime;
	private String descs;
	private Date endtime;
	private Integer frouser;
	private BigDecimal frousermoney;
	private BigDecimal joinmoney;
	private BigDecimal salemoney;
	private String activiecode;
	private Integer optuser;
	private BigDecimal price;
	private String remark;
	private Date starttime;
	private String status;
	private String type;

	public TProdActive() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getActivename() {
		return this.activename;
	}

	public void setActivename(String activename) {
		this.activename = activename;
	}


	public BigDecimal getAddprice() {
		return this.addprice;
	}

	public void setAddprice(BigDecimal addprice) {
		this.addprice = addprice;
	}


	public String getAduitflag() {
		return this.aduitflag;
	}

	public void setAduitflag(String aduitflag) {
		this.aduitflag = aduitflag;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}


	public String getDescs() {
		return this.descs;
	}

	public void setDescs(String descs) {
		this.descs = descs;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getEndtime() {
		return this.endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}


	public Integer getFrouser() {
		return this.frouser;
	}

	public void setFrouser(Integer frouser) {
		this.frouser = frouser;
	}


	public BigDecimal getFrousermoney() {
		return this.frousermoney;
	}

	public void setFrousermoney(BigDecimal frousermoney) {
		this.frousermoney = frousermoney;
	}


	public BigDecimal getJoinmoney() {
		return this.joinmoney;
	}

	public void setJoinmoney(BigDecimal joinmoney) {
		this.joinmoney = joinmoney;
	}


	public Integer getOptuser() {
		return this.optuser;
	}

	public void setOptuser(Integer optuser) {
		this.optuser = optuser;
	}


	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}


	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getStarttime() {
		return this.starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
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


	public BigDecimal getSalemoney() {
		return salemoney;
	}


	public void setSalemoney(BigDecimal salemoney) {
		this.salemoney = salemoney;
	}


	public String getActiviecode() {
		return activiecode;
	}


	public void setActiviecode(String activiecode) {
		this.activiecode = activiecode;
	}
	

}