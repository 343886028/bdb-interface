package com.bdbvip.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the t_ord_revoke database table.
 * 
 */
@Entity
@Table(name="t_ord_revoke")
@NamedQuery(name="TOrdRevoke.findAll", query="SELECT t FROM TOrdRevoke t")
public class TOrdRevoke implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private Integer revokeid;
	private Integer orderitemid;
	private String orderno;
	private Date receivetime;
	private String revokemsg;
	private String revoketelphone;
	private Date revoketime;
	private String revoketype;
	private int touserid;

	public TOrdRevoke() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getRevokeid() {
		return this.revokeid;
	}

	public void setRevokeid(Integer revokeid) {
		this.revokeid = revokeid;
	}


	public Integer getOrderitemid() {
		return this.orderitemid;
	}

	public void setOrderitemid(Integer orderitemid) {
		this.orderitemid = orderitemid;
	}


	public String getOrderno() {
		return this.orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getReceivetime() {
		return this.receivetime;
	}

	public void setReceivetime(Date receivetime) {
		this.receivetime = receivetime;
	}


	public String getRevokemsg() {
		return this.revokemsg;
	}

	public void setRevokemsg(String revokemsg) {
		this.revokemsg = revokemsg;
	}


	public String getRevoketelphone() {
		return this.revoketelphone;
	}

	public void setRevoketelphone(String revoketelphone) {
		this.revoketelphone = revoketelphone;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getRevoketime() {
		return this.revoketime;
	}

	public void setRevoketime(Date revoketime) {
		this.revoketime = revoketime;
	}


	public String getRevoketype() {
		return this.revoketype;
	}

	public void setRevoketype(String revoketype) {
		this.revoketype = revoketype;
	}


	public Integer getTouserid() {
		return this.touserid;
	}

	public void setTouserid(Integer touserid) {
		this.touserid = touserid;
	}

}