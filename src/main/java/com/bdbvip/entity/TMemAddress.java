package com.bdbvip.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the t_mem_address database table.
 * 
 */
@Entity
@Table(name = "t_mem_address")
@NamedQuery(name = "TMemAddress.findAll", query = "SELECT t FROM TMemAddress t")
public class TMemAddress implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private Integer addressid;
	private String address;
	private String city;
	private String flag;
	private String mark;
	private String post;
	private String province;
	private String shortaddress;
	private String telphone;
	private Integer userid;
	private String username;

	public TMemAddress() {
	}

	public String getAddress() {
		return this.address;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getAddressid() {
		return addressid;
	}

	public void setAddressid(Integer addressid) {
		this.addressid = addressid;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getMark() {
		return this.mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getPost() {
		return this.post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getShortaddress() {
		return this.shortaddress;
	}

	public void setShortaddress(String shortaddress) {
		this.shortaddress = shortaddress;
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

}