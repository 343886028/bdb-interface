package com.bdbvip.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the t_prod_tag database table.
 * 
 */
@Entity
@Table(name="t_prod_tag")
public class TProdTag implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private Integer keyid;
	private String keyname;
	private Integer source;
	private Integer type;

	public TProdTag() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getKeyid() {
		return this.keyid;
	}

	public void setKeyid(Integer keyid) {
		this.keyid = keyid;
	}


	public String getKeyname() {
		return this.keyname;
	}

	public void setKeyname(String keyname) {
		this.keyname = keyname;
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