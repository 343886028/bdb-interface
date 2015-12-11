package com.bdbvip.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;


/**
 * The persistent class for the t_sys_partner database table.
 * 
 */
@Entity
@Table(name="t_sys_partner")
@NamedQuery(name="TSysPartner.findAll", query="SELECT t FROM TSysPartner t")
public class TSysPartner extends com.bdbvip.utils.common.domain.entity.Entity<Integer> implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String connect;
	private String name;
	private String partnername;
	private String partnerpasswd;
	private String signstr;
	private String status;

	public TSysPartner() {
	}


	@Id
	@GeneratedValue(generator = "idGenerator")    
	@GenericGenerator(name = "idGenerator", strategy = "native")  
	@Column(unique=true, nullable=false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	@Column(length=20)
	public String getConnect() {
		return this.connect;
	}

	public void setConnect(String connect) {
		this.connect = connect;
	}


	@Column(length=10)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Column(length=20)
	public String getPartnername() {
		return this.partnername;
	}

	public void setPartnername(String partnername) {
		this.partnername = partnername;
	}


	@Column(length=20)
	public String getPartnerpasswd() {
		return this.partnerpasswd;
	}

	public void setPartnerpasswd(String partnerpasswd) {
		this.partnerpasswd = partnerpasswd;
	}


	@Column(length=50)
	public String getSignstr() {
		return this.signstr;
	}

	public void setSignstr(String signstr) {
		this.signstr = signstr;
	}


	@Column(length=1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}