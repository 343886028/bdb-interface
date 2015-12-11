package com.bdbvip.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the t_mem_binduser database table.
 * 
 */
@Entity
@Table(name="t_mem_binduser")
@NamedQuery(name="TMemBinduser.findAll", query="SELECT t FROM TMemBinduser t")
public class TMemBinduser implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private String bindid;
	private String bindname;
	private Date createdate;
	private String flag;
	private Integer type;
	private Date updatedate;
	private Integer userid;

	public TMemBinduser() {
	}

	public String getBindid() {
		return this.bindid;
	}

	public void setBindid(String bindid) {
		this.bindid = bindid;
	}


	public String getBindname() {
		return this.bindname;
	}

	public void setBindname(String bindname) {
		this.bindname = bindname;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}


	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}


	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getUpdatedate() {
		return this.updatedate;
	}

	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}


	@Id
	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

}