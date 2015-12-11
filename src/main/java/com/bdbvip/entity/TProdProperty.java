package com.bdbvip.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_prod_properties database table.
 * 
 */
@Entity
@Table(name="t_prod_properties")
@NamedQuery(name="TProdProperty.findAll", query="SELECT t FROM TProdProperty t")
public class TProdProperty implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private Integer prodid;
	private String values;

	public TProdProperty() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Integer getProdid() {
		return this.prodid;
	}

	public void setProdid(Integer prodid) {
		this.prodid = prodid;
	}


	public String getValues() {
		return this.values;
	}

	public void setValues(String values) {
		this.values = values;
	}

}