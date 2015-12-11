package com.bdbvip.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;


/**
 * The persistent class for the t_mem_sms database table.
 * 
 */
@Entity
@Table(name="t_mem_sms")
@NamedQuery(name="TMemSm.findAll", query="SELECT t FROM TMemSm t")
public class TMemSm implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private Integer smsid;
	private String code;
	private String content;
	private Date createtime;
	private String telphone;
	private Integer touserid;
	private String type;
	private Date validtime;

	public TMemSm() {
	}


	@Id
	@GenericGenerator(name="idGenerator", strategy="native")
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "idGenerator")
	public Integer getSmsid() {
		return this.smsid;
	}

	public void setSmsid(Integer smsid) {
		this.smsid = smsid;
	}


	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}


	public String getTelphone() {
		return this.telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}


	public Integer getTouserid() {
		return this.touserid;
	}

	public void setTouserid(Integer touserid) {
		this.touserid = touserid;
	}


	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getValidtime() {
		return this.validtime;
	}

	public void setValidtime(Date validtime) {
		this.validtime = validtime;
	}

}