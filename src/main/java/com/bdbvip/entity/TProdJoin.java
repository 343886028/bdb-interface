package com.bdbvip.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_prod_join database table.
 * 
 */
@Entity
@Table(name="t_prod_join")
@NamedQuery(name="TProdJoin.findAll", query="SELECT t FROM TProdJoin t")
public class TProdJoin implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer activeid;
	private String activename;
	private Integer activeuserid;
	private Integer proid;
	private String status;
	private Integer userid;
	private String remark;

	public TProdJoin() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getActiveid() {
		return this.activeid;
	}

	public void setActiveid(Integer activeid) {
		this.activeid = activeid;
	}


	public String getActivename() {
		return this.activename;
	}

	public void setActivename(String activename) {
		this.activename = activename;
	}


	public Integer getActiveuserid() {
		return this.activeuserid;
	}

	public void setActiveuserid(Integer activeuserid) {
		this.activeuserid = activeuserid;
	}


	public Integer getProid() {
		return this.proid;
	}

	public void setProid(Integer proid) {
		this.proid = proid;
	}


	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}
	

}