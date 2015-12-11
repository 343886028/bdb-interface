package com.bdbvip.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the t_mem_msg database table.
 * 
 */
@Entity
@Table(name="t_mem_msg")
@NamedQuery(name="TMemMsg.findAll", query="SELECT t FROM TMemMsg t")
public class TMemMsg implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private Integer msgid;
	private Date createtime;
	private Integer fromuserid;
	private String msgcontent;
	private String msgtype;
	private String status;
	private Integer touserid;
	private String tradeno;
	private String type;

	public TMemMsg() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getMsgid() {
		return this.msgid;
	}

	public void setMsgid(Integer msgid) {
		this.msgid = msgid;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}


	public Integer getFromuserid() {
		return this.fromuserid;
	}

	public void setFromuserid(Integer fromuserid) {
		this.fromuserid = fromuserid;
	}


	public String getMsgcontent() {
		return this.msgcontent;
	}

	public void setMsgcontent(String msgcontent) {
		this.msgcontent = msgcontent;
	}


	public String getMsgtype() {
		return this.msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}


	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public Integer getTouserid() {
		return this.touserid;
	}

	public void setTouserid(Integer touserid) {
		this.touserid = touserid;
	}


	public String getTradeno() {
		return this.tradeno;
	}

	public void setTradeno(String tradeno) {
		this.tradeno = tradeno;
	}


	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}