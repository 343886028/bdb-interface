package com.bdbvip.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_prod_credit database table.
 * 
 */
@Entity
@Table(name="t_prod_credit")
@NamedQuery(name="TProdCredit.findAll", query="SELECT t FROM TProdCredit t")
public class TProdCredit implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String descs;
	private String eventname;
	private Integer num;
	private Integer source;
	private Integer type;

	public TProdCredit() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getDescs() {
		return this.descs;
	}

	public void setDescs(String descs) {
		this.descs = descs;
	}


	public String getEventname() {
		return this.eventname;
	}

	public void setEventname(String eventname) {
		this.eventname = eventname;
	}


	public Integer getNum() {
		return this.num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}


	public Integer getSource() {
		return this.source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}


	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}