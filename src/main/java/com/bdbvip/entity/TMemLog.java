package com.bdbvip.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;


/**
 * The persistent class for the t_mem_log database table.
 * 
 */
@Entity
@Table(name="t_mem_log")
@NamedQuery(name="TMemLog.findAll", query="SELECT t FROM TMemLog t")
public class TMemLog implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Date createtime;
	private String logmsg;
	private String logtype;
	private String optuserid;
	private Integer userid;

	public TMemLog() {
	}


	@Id
	@GenericGenerator(name="idGenerator", strategy="native")
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "idGenerator")
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


	public String getLogmsg() {
		return this.logmsg;
	}

	public void setLogmsg(String logmsg) {
		this.logmsg = logmsg;
	}


	public String getLogtype() {
		return this.logtype;
	}

	public void setLogtype(String logtype) {
		this.logtype = logtype;
	}


	public String getOptuserid() {
		return this.optuserid;
	}

	public void setOptuserid(String optuserid) {
		this.optuserid = optuserid;
	}


	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

}