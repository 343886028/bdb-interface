package com.bdbvip.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the t_mem_grade_record database table.
 * 
 */
@Entity
@Table(name="t_mem_grade_record")
@NamedQuery(name="TMemGradeRecord.findAll", query="SELECT t FROM TMemGradeRecord t")
public class TMemGradeRecord implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Date createtime;
	private Integer goldstar;
	private String orderno;
	private String remark;
	private Integer slivestar;
	private Integer star;
	private Integer userid;

	public TMemGradeRecord() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
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


	public Integer getGoldstar() {
		return this.goldstar;
	}

	public void setGoldstar(Integer goldstar) {
		this.goldstar = goldstar;
	}


	public String getOrderno() {
		return this.orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}


	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	public Integer getSlivestar() {
		return this.slivestar;
	}

	public void setSlivestar(Integer slivestar) {
		this.slivestar = slivestar;
	}


	public Integer getStar() {
		return this.star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}


	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

}