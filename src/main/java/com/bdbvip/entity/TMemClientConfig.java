package com.bdbvip.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the t_mem_client_config database table.
 * 
 */
@Entity
@Table(name="t_mem_client_config")
@NamedQuery(name="TMemClientConfig.findAll", query="SELECT t FROM TMemClientConfig t")
public class TMemClientConfig implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Date createtime;
	private String flag;
	private String status;
	private Integer type;
	private Date updatetime;
	private Integer userid;

	public TMemClientConfig() {
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


	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}


	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}


	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

}