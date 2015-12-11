package com.bdbvip.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

/**
 * The persistent class for the t_mem_consult database table.
 * 
 */
@Entity
@Table(name = "t_mem_consult")
@NamedQuery(name = "TMemConsult.findAll", query = "SELECT t FROM TMemConsult t")
public class TMemConsult implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer userid;
	private Integer shopid;
	private Integer touserid;
	private String content;
	private Date createtime;
	private String ip;

	public TMemConsult() {
	}

	@Id
	@GenericGenerator(name = "idGenerator", strategy = "native")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "idGenerator")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getShopid() {
		return shopid;
	}

	public void setShopid(Integer shopid) {
		this.shopid = shopid;
	}

	public Integer getTouserid() {
		return touserid;
	}

	public void setTouserid(Integer touserid) {
		this.touserid = touserid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

}
