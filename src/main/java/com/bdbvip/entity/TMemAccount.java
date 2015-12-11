package com.bdbvip.entity;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * The persistent class for the t_mem_account database table.
 * 
 */
@Entity
@Table(name = "t_mem_account")
@NamedQuery(name = "TMemAccount.findAll", query = "SELECT t FROM TMemAccount t")
public class TMemAccount implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private String accountflag;
	private BigDecimal cashmoney;
	private BigDecimal frozenmoney;
	private Integer userid;
	private Date locktime;

	public TMemAccount() {
	}

	public String getAccountflag() {
		return this.accountflag;
	}

	public void setAccountflag(String accountflag) {
		this.accountflag = accountflag;
	}

	public BigDecimal getCashmoney() {
		return this.cashmoney;
	}

	public void setCashmoney(BigDecimal cashmoney) {
		this.cashmoney = cashmoney;
	}

	public BigDecimal getFrozenmoney() {
		return this.frozenmoney;
	}

	public void setFrozenmoney(BigDecimal frozenmoney) {
		this.frozenmoney = frozenmoney;
	}

	@Id
	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getLocktime() {
		return locktime;
	}

	public void setLocktime(Date locktime) {
		this.locktime = locktime;
	}

}