package com.bdbvip.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;


/**
 * The persistent class for the t_mem_shop_kinds database table.
 * 
 */
@Entity
@Table(name="t_mem_shop_kinds")
@NamedQuery(name="TMemShopKind.findAll", query="SELECT t FROM TMemShopKind t")
public class TMemShopKind implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Date createtime;
	private String kindscode;
	private String kindsname;
	private Integer userid;

	public TMemShopKind() {
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


	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}


	public String getKindscode() {
		return this.kindscode;
	}

	public void setKindscode(String kindscode) {
		this.kindscode = kindscode;
	}


	public String getKindsname() {
		return this.kindsname;
	}

	public void setKindsname(String kindsname) {
		this.kindsname = kindsname;
	}


	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

}