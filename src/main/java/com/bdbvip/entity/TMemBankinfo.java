package com.bdbvip.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the t_mem_bankinfo database table.
 * 
 */
@Entity
@Table(name = "t_mem_bankinfo")
@NamedQuery(name = "TMemBankinfo.findAll", query = "SELECT t FROM TMemBankinfo t")
public class TMemBankinfo implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String bankcode;
	private String bankname;
	private String bankno;
	private String city;
	private String flag;
	private String openaddress;
	private String provider;
	private String realname;
	private Integer userid;

	public String getBankcode() {
		return this.bankcode;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
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

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getOpenaddress() {
		return this.openaddress;
	}

	public void setOpenaddress(String openaddress) {
		this.openaddress = openaddress;
	}

	public String getProvider() {
		return this.provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getRealname() {
		return this.realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

}