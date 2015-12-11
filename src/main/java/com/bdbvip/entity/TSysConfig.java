package com.bdbvip.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;


/**
 * The persistent class for the t_sys_config database table.
 * 
 */
@Entity
@Table(name="t_sys_config")
public class TSysConfig  implements Serializable,Cloneable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String keyname;
	private String keyvalue;
	private String status;

	public TSysConfig() {
	}

	 
	@Id
	@GenericGenerator(name="idGenerator", strategy="native")
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "idGenerator")
	@Column(unique=true, nullable=false)
	public Integer getId() {
		return this.id;
	}
//
	public void setId(Integer id) {
		this.id = id;
	}


	@Column(length=20)
	public String getKeyname() {
		return this.keyname;
	}

	public void setKeyname(String keyname) {
		this.keyname = keyname;
	}


	@Column(length=50)
	public String getKeyvalue() {
		return this.keyvalue;
	}

	public void setKeyvalue(String keyvalue) {
		this.keyvalue = keyvalue;
	}


	@Column(length=1)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}