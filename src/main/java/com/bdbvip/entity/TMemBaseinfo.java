package com.bdbvip.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="t_mem_base_info")
public class TMemBaseinfo implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private Integer userid;

	public TMemBaseinfo() {
	}
	 


	@Id
	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	 
}
