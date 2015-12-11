package com.bdbvip.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the t_prod_sale_list database table.
 * 
 */
@Entity
@Table(name="t_prod_sale_list")
@NamedQuery(name="TProdSaleList.findAll", query="SELECT t FROM TProdSaleList t")
public class TProdSaleList implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private Integer prosaleid;
	private Integer activeid;
	private String activename;
	private BigDecimal addprice;
	private String aduitflag;
	private Integer browers;
	private Date createtime;
	private Integer delaytime;
	private String descs;
	private BigDecimal forusermoney;
	private String imgurl;
	private String imgurl2;
	private String imgurl3;
	private String issendflag;
	private Date lasttime;
	private Integer lastuserid;
	private BigDecimal maxprice;
	private BigDecimal price;
	private String procode;
	private Integer prodid;
	private String proname;
	private String revokeflag;
	private Date saleendtime;
	private String saleflag;
	private Date salestarttime;
	private Date saletime;
	private Integer userid;
	private BigDecimal marketvalues;
	private BigDecimal bound;

	public TProdSaleList() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getProsaleid() {
		return this.prosaleid;
	}

	public void setProsaleid(Integer prosaleid) {
		this.prosaleid = prosaleid;
	}


	public Integer getActiveid() {
		return this.activeid;
	}

	public void setActiveid(Integer activeid) {
		this.activeid = activeid;
	}


	public String getActivename() {
		return this.activename;
	}

	public void setActivename(String activename) {
		this.activename = activename;
	}


	public BigDecimal getAddprice() {
		return this.addprice;
	}

	public void setAddprice(BigDecimal addprice) {
		this.addprice = addprice;
	}


	public String getAduitflag() {
		return this.aduitflag;
	}

	public void setAduitflag(String aduitflag) {
		this.aduitflag = aduitflag;
	}


	public Integer getBrowers() {
		return this.browers;
	}

	public void setBrowers(Integer browers) {
		this.browers = browers;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}


	public Integer getDelaytime() {
		return this.delaytime;
	}

	public void setDelaytime(Integer delaytime) {
		this.delaytime = delaytime;
	}


	public String getDescs() {
		return this.descs;
	}

	public void setDescs(String descs) {
		this.descs = descs;
	}


	public BigDecimal getForusermoney() {
		return this.forusermoney;
	}

	public void setForusermoney(BigDecimal forusermoney) {
		this.forusermoney = forusermoney;
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


	public String getIssendflag() {
		return this.issendflag;
	}

	public void setIssendflag(String issendflag) {
		this.issendflag = issendflag;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getLasttime() {
		return this.lasttime;
	}

	public void setLasttime(Date lasttime) {
		this.lasttime = lasttime;
	}


	public Integer getLastuserid() {
		return lastuserid;
	}


	public void setLastuserid(Integer lastuserid) {
		this.lastuserid = lastuserid;
	}


	public BigDecimal getMaxprice() {
		return this.maxprice;
	}

	public void setMaxprice(BigDecimal maxprice) {
		this.maxprice = maxprice;
	}


	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}


	public String getProcode() {
		return this.procode;
	}

	public void setProcode(String procode) {
		this.procode = procode;
	}


	public Integer getProdid() {
		return this.prodid;
	}

	public void setProdid(Integer prodid) {
		this.prodid = prodid;
	}


	public String getProname() {
		return this.proname;
	}

	public void setProname(String proname) {
		this.proname = proname;
	}


	public String getRevokeflag() {
		return this.revokeflag;
	}

	public void setRevokeflag(String revokeflag) {
		this.revokeflag = revokeflag;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getSaleendtime() {
		return this.saleendtime;
	}

	public void setSaleendtime(Date saleendtime) {
		this.saleendtime = saleendtime;
	}


	public String getSaleflag() {
		return this.saleflag;
	}

	public void setSaleflag(String saleflag) {
		this.saleflag = saleflag;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getSalestarttime() {
		return this.salestarttime;
	}

	public void setSalestarttime(Date salestarttime) {
		this.salestarttime = salestarttime;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getSaletime() {
		return this.saletime;
	}

	public void setSaletime(Date saletime) {
		this.saletime = saletime;
	}


	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}


	public BigDecimal getMarketvalues() {
		return marketvalues;
	}


	public void setMarketvalues(BigDecimal marketvalues) {
		this.marketvalues = marketvalues;
	}


	public BigDecimal getBound() {
		return bound;
	}


	public void setBound(BigDecimal bound) {
		this.bound = bound;
	}
	


	
}