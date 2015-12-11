package com.bdbvip.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 用户参加拍卖纪录
 * @author Administrator
 *
 */
@Entity
@Table(name = "t_mem_sale_regist")
public class TMemSaleRegist {
	private Integer id;
	private String procode;
	private Integer registerid;
	private Integer proid;
	private Integer shopid;
	private Integer ownid;
	private Date createtime;
	private BigDecimal money;
	private Integer isflagmoney;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProcode() {
		return procode;
	}

	public void setProcode(String procode) {
		this.procode = procode;
	}

	public Integer getRegisterid() {
		return registerid;
	}

	public void setRegisterid(Integer registerid) {
		this.registerid = registerid;
	}

	public Integer getProid() {
		return proid;
	}

	public void setProid(Integer proid) {
		this.proid = proid;
	}

	public Integer getShopid() {
		return shopid;
	}

	public void setShopid(Integer shopid) {
		this.shopid = shopid;
	}

	public Integer getOwnid() {
		return ownid;
	}

	public void setOwnid(Integer ownid) {
		this.ownid = ownid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public Integer getIsflagmoney() {
		return isflagmoney;
	}

	public void setIsflagmoney(Integer isflagmoney) {
		this.isflagmoney = isflagmoney;
	}

}
