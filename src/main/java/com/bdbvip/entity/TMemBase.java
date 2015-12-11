package com.bdbvip.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;


/**
 * The persistent class for the t_mem_base database table.
 * 
 */
@Entity
@Table(name="t_mem_base")
@NamedQuery(name="TMemBase.findAll", query="SELECT t FROM TMemBase t")
public class TMemBase implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String accountflag;
	private Date birthdary;
	private String cardno;
	private String channel;
	private String channelno;
	private String email;
	private String emailbindflag;
	private String imgurl;
	private Integer levels;
	private String mobilebindflag;
	private String nickname;
	private String realname;
	private Date regtime;
	private String regurl;
	private String regip;
	private String passwd;
	private String remark;
	
	
	private String sex;
	private Integer shopstar;
	private Integer star;
	private String telphone;
	private String username;
	private String usertype;
	

	
	public TMemBase() {
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


	public String getAccountflag() {
		return this.accountflag;
	}

	public void setAccountflag(String accountflag) {
		this.accountflag = accountflag;
	}


	@Temporal(TemporalType.DATE)
	public Date getBirthdary() {
		return this.birthdary;
	}

	public void setBirthdary(Date birthdary) {
		this.birthdary = birthdary;
	}


	public String getCardno() {
		return this.cardno;
	}

	public void setCardno(String cardno) {
		this.cardno = cardno;
	}


	public String getChannel() {
		return this.channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}


	public String getChannelno() {
		return this.channelno;
	}

	public void setChannelno(String channelno) {
		this.channelno = channelno;
	}


	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	public String getEmailbindflag() {
		return this.emailbindflag;
	}

	public void setEmailbindflag(String emailbindflag) {
		this.emailbindflag = emailbindflag;
	}


	public String getImgurl() {
		return this.imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}


	public Integer getLevels() {
		return this.levels;
	}

	public void setLevels(Integer levels) {
		this.levels = levels;
	}


	public String getMobilebindflag() {
		return this.mobilebindflag;
	}

	public void setMobilebindflag(String mobilebindflag) {
		this.mobilebindflag = mobilebindflag;
	}


	public String getNickname() {
		return this.nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}


	public String getRealname() {
		return this.realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getRegtime() {
		return this.regtime;
	}

	public void setRegtime(Date regtime) {
		this.regtime = regtime;
	}


	public String getRegurl() {
		return this.regurl;
	}

	public void setRegurl(String regurl) {
		this.regurl = regurl;
	}


	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}


	public Integer getShopstar() {
		return this.shopstar;
	}

	public void setShopstar(Integer shopstar) {
		this.shopstar = shopstar;
	}


	public Integer getStar() {
		return this.star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}


	public String getTelphone() {
		return this.telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}


	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public String getUsertype() {
		return this.usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}


	public String getRegip() {
		return regip;
	}


	public void setRegip(String regip) {
		this.regip = regip;
	}


	public String getPasswd() {
		return passwd;
	}


	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}

}