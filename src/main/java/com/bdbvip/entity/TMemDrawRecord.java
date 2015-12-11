package com.bdbvip.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the t_mem_draw_record database table.
 * 
 */
@Entity
@Table(name="t_mem_draw_record")
@NamedQuery(name="TMemDrawRecord.findAll", query="SELECT t FROM TMemDrawRecord t")
public class TMemDrawRecord implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private Integer drawid;
	private String bankcode;
	private String bankname;
	private String bankno;
	private Date builddate;
	private String buildname;
	private Date createtime;
	private String drawflag;
	private BigDecimal drawmoney;
	private String failmsg;
	private Date optdate;
	private String optuserid;
	private String platcode;
	private String realname;
	private String sendflag;
	private Integer userid;
	private String username;

	public TMemDrawRecord() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getDrawid() {
		return this.drawid;
	}

	public void setDrawid(Integer drawid) {
		this.drawid = drawid;
	}


	public String getBankcode() {
		return this.bankcode;
	}

	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}


	public String getBankname() {
		return this.bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}


	public String getBankno() {
		return this.bankno;
	}

	public void setBankno(String bankno) {
		this.bankno = bankno;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getBuilddate() {
		return this.builddate;
	}

	public void setBuilddate(Date builddate) {
		this.builddate = builddate;
	}


	public String getBuildname() {
		return this.buildname;
	}

	public void setBuildname(String buildname) {
		this.buildname = buildname;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}


	public String getDrawflag() {
		return this.drawflag;
	}

	public void setDrawflag(String drawflag) {
		this.drawflag = drawflag;
	}


	public BigDecimal getDrawmoney() {
		return this.drawmoney;
	}

	public void setDrawmoney(BigDecimal drawmoney) {
		this.drawmoney = drawmoney;
	}


	public String getFailmsg() {
		return this.failmsg;
	}

	public void setFailmsg(String failmsg) {
		this.failmsg = failmsg;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getOptdate() {
		return this.optdate;
	}

	public void setOptdate(Date optdate) {
		this.optdate = optdate;
	}


	public String getOptuserid() {
		return this.optuserid;
	}

	public void setOptuserid(String optuserid) {
		this.optuserid = optuserid;
	}


	public String getPlatcode() {
		return this.platcode;
	}

	public void setPlatcode(String platcode) {
		this.platcode = platcode;
	}


	public String getRealname() {
		return this.realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}


	public String getSendflag() {
		return this.sendflag;
	}

	public void setSendflag(String sendflag) {
		this.sendflag = sendflag;
	}


	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}


	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}