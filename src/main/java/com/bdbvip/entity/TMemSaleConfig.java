package com.bdbvip.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the t_mem_sale_config database table.
 * 
 */
@Entity
@Table(name="t_mem_sale_config")
@NamedQuery(name="TMemSaleConfig.findAll", query="SELECT t FROM TMemSaleConfig t")
public class TMemSaleConfig implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
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

	public TMemSaleConfig() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getEndtime() {
		return this.endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}


	public BigDecimal getMaxvalues() {
		return this.maxvalues;
	}

	public void setMaxvalues(BigDecimal maxvalues) {
		this.maxvalues = maxvalues;
	}


	public String getProductcode() {
		return this.productcode;
	}

	public void setProductcode(String productcode) {
		this.productcode = productcode;
	}


	public Integer getTouserid() {
		return this.touserid;
	}

	public void setTouserid(Integer touserid) {
		this.touserid = touserid;
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