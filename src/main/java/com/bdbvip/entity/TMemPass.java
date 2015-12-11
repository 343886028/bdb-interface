package com.bdbvip.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_mem_pass database table.
 * 
 */
@Entity
@Table(name="t_mem_pass")
@NamedQuery(name="TMemPass.findAll", query="SELECT t FROM TMemPass t")
public class TMemPass implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private Integer userid;
	private String clientpass;
	private String drawpass;
	private String paypass;

	public TMemPass() {
	}


	@Id
	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}


	public String getClientpass() {
		return this.clientpass;
	}

	public void setClientpass(String clientpass) {
		this.clientpass = clientpass;
	}


	public String getDrawpass() {
		return this.drawpass;
	}

	public void setDrawpass(String drawpass) {
		this.drawpass = drawpass;
	}


	public String getPaypass() {
		return this.paypass;
	}

	public void setPaypass(String paypass) {
		this.paypass = paypass;
	}

}