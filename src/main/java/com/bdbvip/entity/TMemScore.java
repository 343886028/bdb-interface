package com.bdbvip.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="t_mem_score")
public class TMemScore implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private Integer score;
	private Integer userid;

	public TMemScore() {
	}
	 


	@Id
	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}



	public Integer getScore() {
		return score;
	}



	public void setScore(Integer score) {
		this.score = score;
	}

}
