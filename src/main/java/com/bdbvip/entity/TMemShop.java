package com.bdbvip.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import java.util.Date;


/**
 * The persistent class for the t_mem_shop database table.
 * 
 */
@Entity
@Table(name="t_mem_shop")
@NamedQuery(name="TMemShop.findAll", query="SELECT t FROM TMemShop t")
public class TMemShop implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer userid;
	private String address;
	private String cardimg;
	private String cardimg2;
	private Date createtime;
	private String imgurl;
	private String imgurl2;
	private String para;
	private Integer qualitystar;
	private Integer sendstar;
	private Integer servicestar;
	private String shopimg;
	private String shopname;
	private String shopnumber;
	private Integer showpara;
	private String status;
	private Integer shopstar;
	private Integer shopmsg;
	

	public TMemShop() {
	}


	@Id
	@GenericGenerator(name="idGenerator", strategy="native")
    @GeneratedValue(strategy=GenerationType.AUTO, generator = "idGenerator")
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


	public String getCardimg() {
		return this.cardimg;
	}

	public void setCardimg(String cardimg) {
		this.cardimg = cardimg;
	}


	public String getCardimg2() {
		return this.cardimg2;
	}

	public void setCardimg2(String cardimg2) {
		this.cardimg2 = cardimg2;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}


	public String getImgurl() {
		return this.imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}


	public String getImgurl2() {
		return this.imgurl2;
	}

	public void setImgurl2(String imgurl2) {
		this.imgurl2 = imgurl2;
	}


	public String getPara() {
		return this.para;
	}

	public void setPara(String para) {
		this.para = para;
	}


	public Integer getQualitystar() {
		return this.qualitystar;
	}

	public void setQualitystar(Integer qualitystar) {
		this.qualitystar = qualitystar;
	}


	public Integer getSendstar() {
		return this.sendstar;
	}

	public void setSendstar(Integer sendstar) {
		this.sendstar = sendstar;
	}


	public Integer getServicestar() {
		return this.servicestar;
	}

	public void setServicestar(Integer servicestar) {
		this.servicestar = servicestar;
	}


	public String getShopimg() {
		return this.shopimg;
	}

	public void setShopimg(String shopimg) {
		this.shopimg = shopimg;
	}


	public String getShopname() {
		return this.shopname;
	}

	public void setShopname(String shopname) {
		this.shopname = shopname;
	}


	public String getShopnumber() {
		return this.shopnumber;
	}

	public void setShopnumber(String shopnumber) {
		this.shopnumber = shopnumber;
	}


	public Integer getShowpara() {
		return this.showpara;
	}

	public void setShowpara(Integer showpara) {
		this.showpara = showpara;
	}


	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public Integer getShopstar() {
		return shopstar;
	}


	public void setShopstar(Integer shopstar) {
		this.shopstar = shopstar;
	}


	public Integer getUserid() {
		return userid;
	}


	public void setUserid(Integer userid) {
		this.userid = userid;
	}


	public Integer getShopmsg() {
		return shopmsg;
	}


	public void setShopmsg(Integer shopmsg) {
		this.shopmsg = shopmsg;
	}

}