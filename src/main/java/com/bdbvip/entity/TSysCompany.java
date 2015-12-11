package com.bdbvip.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_sys_company database table.
 * 
 */
@Entity
@Table(name="t_sys_company")
@NamedQuery(name="TSysCompany.findAll", query="SELECT t FROM TSysCompany t")
public class TSysCompany implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private Integer companyid;
	private String company;
	private String status;

	public TSysCompany() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(Integer companyid) {
		this.companyid = companyid;
	}


	public String getCompany() {
		return this.company;
	}

	public void setCompany(String company) {
		this.company = company;
	}


	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}