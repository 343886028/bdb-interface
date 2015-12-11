package com.bdbvip.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the t_mem_readmsg database table.
 * 
 */
@Entity
@Table(name="t_mem_readmsg")
@NamedQuery(name="TMemReadmsg.findAll", query="SELECT t FROM TMemReadmsg t")
public class TMemReadmsg implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private Integer readmsgid;
	private Date createtime;
	private Integer msgid;
	private Integer userid;

	public TMemReadmsg() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getReadmsgid() {
		return this.readmsgid;
	}

	public void setReadmsgid(Integer readmsgid) {
		this.readmsgid = readmsgid;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}


	public Integer getMsgid() {
		return this.msgid;
	}

	public void setMsgid(Integer msgid) {
		this.msgid = msgid;
	}


	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

}