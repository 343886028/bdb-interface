package com.bdbvip.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the t_prod_list database table.
 * 
 */
@Entity
@Table(name="t_prod_list")
@NamedQuery(name="TProdList.findAll", query="SELECT t FROM TProdList t")
public class TProdList implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String auditflag;
	private Integer browes;
	private String certiedimg;
	private String certiedimg2;
	private String certiednon;
	private String procode;
	private Integer count;
	private String descs;
	private String imgurl;
	private String imgurl2;
	private String imgurl3;
	private String imgurl4;
	private String imgurl5;
	private String imgurl6;
	private String imgurl7;
	private String imgurl8;
	private String imgurl9;
	private String issend;
	private BigDecimal mprice;
	private String proname;
	private BigDecimal offprice;
	private BigDecimal price;
	private String protype;
	private String revokeflag;
	private String saleflag;
	private String saletitle;
	private String title;
	private String type;
	private Integer userid;
	private Integer shopid;

	public TProdList() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getAuditflag() {
		return this.auditflag;
	}

	public void setAuditflag(String auditflag) {
		this.auditflag = auditflag;
	}


	public Integer getBrowes() {
		return this.browes;
	}

	public void setBrowes(Integer browes) {
		this.browes = browes;
	}


	public String getCertiedimg() {
		return this.certiedimg;
	}

	public void setCertiedimg(String certiedimg) {
		this.certiedimg = certiedimg;
	}


	public String getCertiedimg2() {
		return this.certiedimg2;
	}

	public void setCertiedimg2(String certiedimg2) {
		this.certiedimg2 = certiedimg2;
	}


	public String getCertiednon() {
		return this.certiednon;
	}

	public void setCertiednon(String certiednon) {
		this.certiednon = certiednon;
	}

	public String getProcode() {
		return procode;
	}

	public void setProcode(String procode) {
		this.procode = procode;
	}


	public Integer getCount() {
		return this.count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}


	public String getDescs() {
		return this.descs;
	}

	public void setDescs(String descs) {
		this.descs = descs;
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


	public String getImgurl3() {
		return this.imgurl3;
	}

	public void setImgurl3(String imgurl3) {
		this.imgurl3 = imgurl3;
	}


	public String getImgurl4() {
		return this.imgurl4;
	}

	public void setImgurl4(String imgurl4) {
		this.imgurl4 = imgurl4;
	}


	public String getImgurl5() {
		return this.imgurl5;
	}

	public void setImgurl5(String imgurl5) {
		this.imgurl5 = imgurl5;
	}


	public String getImgurl6() {
		return this.imgurl6;
	}

	public void setImgurl6(String imgurl6) {
		this.imgurl6 = imgurl6;
	}


	public String getImgurl7() {
		return this.imgurl7;
	}

	public void setImgurl7(String imgurl7) {
		this.imgurl7 = imgurl7;
	}


	public String getImgurl8() {
		return this.imgurl8;
	}

	public void setImgurl8(String imgurl8) {
		this.imgurl8 = imgurl8;
	}


	public String getImgurl9() {
		return this.imgurl9;
	}

	public void setImgurl9(String imgurl9) {
		this.imgurl9 = imgurl9;
	}


	public String getIssend() {
		return this.issend;
	}

	public void setIssend(String issend) {
		this.issend = issend;
	}


	public BigDecimal getMprice() {
		return this.mprice;
	}

	public void setMprice(BigDecimal mprice) {
		this.mprice = mprice;
	}

	public String getProname() {
		return proname;
	}

	public void setProname(String proname) {
		this.proname = proname;
	}


	public BigDecimal getOffprice() {
		return this.offprice;
	}

	public void setOffprice(BigDecimal offprice) {
		this.offprice = offprice;
	}


	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}


	public String getProtype() {
		return this.protype;
	}

	public void setProtype(String protype) {
		this.protype = protype;
	}


	public String getRevokeflag() {
		return this.revokeflag;
	}

	public void setRevokeflag(String revokeflag) {
		this.revokeflag = revokeflag;
	}


	public String getSaleflag() {
		return this.saleflag;
	}

	public void setSaleflag(String saleflag) {
		this.saleflag = saleflag;
	}


	public String getSaletitle() {
		return this.saletitle;
	}

	public void setSaletitle(String saletitle) {
		this.saletitle = saletitle;
	}


	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}


	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}


	public Integer getShopid() {
		return shopid;
	}


	public void setShopid(Integer shopid) {
		this.shopid = shopid;
	}

}