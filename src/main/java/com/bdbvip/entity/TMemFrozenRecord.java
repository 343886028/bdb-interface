package com.bdbvip.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the t_mem_frozen_record database table.
 * 
 */
@Entity
@Table(name = "t_mem_frozen_record")
@NamedQuery(name = "TMemFrozenRecord.findAll", query = "SELECT t FROM TMemFrozenRecord t")
public class TMemFrozenRecord implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private BigDecimal cashmoney;
	private Date createtime;
	private BigDecimal frzoenmoney;
	private String frzoenorderno;
	private String status;
	private Date updatetime;
	private Integer inuserid;
	private Integer outuserid;

	public TMemFrozenRecord() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getInuserid() {
		return inuserid;
	}

	public void setInuserid(Integer inuserid) {
		this.inuserid = inuserid;
	}

	public Integer getOutuserid() {
		return outuserid;
	}

	public void setOutuserid(Integer outuserid) {
		this.outuserid = outuserid;
	}

	public BigDecimal getCashmoney() {
		return this.cashmoney;
	}

	public void setCashmoney(BigDecimal cashmoney) {
		this.cashmoney = cashmoney;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public BigDecimal getFrzoenmoney() {
		return this.frzoenmoney;
	}

	public void setFrzoenmoney(BigDecimal frzoenmoney) {
		this.frzoenmoney = frzoenmoney;
	}

	public String getFrzoenorderno() {
		return this.frzoenorderno;
	}

	public void setFrzoenorderno(String frzoenorderno) {
		this.frzoenorderno = frzoenorderno;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

}