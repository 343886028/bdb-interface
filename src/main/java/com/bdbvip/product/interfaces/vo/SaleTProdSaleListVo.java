package com.bdbvip.product.interfaces.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.bdbvip.pojo.BasePageVo;
import com.bdbvip.utils.common.date.DateUtil;

/**
 * TProdSaleList pojo
 * 拍卖商品信息 pojo 拍卖品编号生成 如：EM 开头 代表翡翠
 * 
 * @ClassName: SaleProductVo
 * @Description: TODO
 * @date 2015年12月3日 下午4:21:34
 */

public class SaleTProdSaleListVo extends BasePageVo {

	
	public SaleTProdSaleListVo() {
	}
	
	public SaleTProdSaleListVo(String procode) {
		super();
		this.procode = procode;
	}

	private static final long serialVersionUID = -8874824306955035656L;
	private Integer id;
	private String proname;
	private String procode;
	private String title;
	private BigDecimal price;
	private BigDecimal addprice;
	private BigDecimal bound;
	private Date createtime;
	private Date salestarttime;
	private Date saleendtime;
	private BigDecimal maxprice;
	private Date lasttime;
	private Integer lastuserid;
	private String lastusername;
	private String saleflag;
	private String aduitflag;
	private Integer activeid;
	private String activename;
	private String shopname;
	private int browers;
	private String descs;
	private String imgurl;
	private String imgurl2;
	private String imgurl3;


	

	public Integer getActiveid() {
		return activeid;
	}


	public void setBrowers(Integer browers) {
		this.browers = browers;
	}

	public String getActivename() {
		return activename;
	}

	public void setActivename(String activename) {
		this.activename = activename;
	}

	public BigDecimal getAddprice() {
		return addprice;
	}

	public void setAddprice(BigDecimal addprice) {
		this.addprice = addprice;
	}

	public String getAduitflag() {
		return aduitflag;
	}

	public void setAduitflag(String aduitflag) {
		this.aduitflag = aduitflag;
	}

	public int getBrowers() {
		return browers;
	}

	public void setBrowers(int browers) {
		this.browers = browers;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public String getImgurl2() {
		return imgurl2;
	}

	public void setImgurl2(String imgurl2) {
		this.imgurl2 = imgurl2;
	}

	public String getImgurl3() {
		return imgurl3;
	}

	public void setImgurl3(String imgurl3) {
		this.imgurl3 = imgurl3;
	}

	public Date getLasttime() {
		/*if (lasttime != null) {
			return DateUtil.simpleDateFormat3.format(lasttime);
		}*/
		return lasttime;
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
		return maxprice;
	}

	public void setMaxprice(BigDecimal maxprice) {
		this.maxprice = maxprice;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	

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

	public String getProname() {
		return proname;
	}

	public void setProname(String proname) {
		this.proname = proname;
	}

	public Date getSaleendtime() {
		/*if (saleendtime != null) {
			return DateUtil.simpleDateFormat3.format(saleendtime);
		}*/
		return saleendtime;
	}

	public void setSaleendtime(Date saleendtime) {
		this.saleendtime = saleendtime;
	}

	public String getSaleflag() {
		return saleflag;
	}

	public void setSaleflag(String saleflag) {
		this.saleflag = saleflag;
	}

	public Date getSalestarttime() {
		/*if (saleendtime != null) {
			return DateUtil.simpleDateFormat3.format(salestarttime);
		}
		return "";*/
		return salestarttime;
	}

	public void setSalestarttime(Date salestarttime) {
		this.salestarttime = salestarttime;
	}

	


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigDecimal getBound() {
		return bound;
	}

	public void setBound(BigDecimal bound) {
		this.bound = bound;
	}

	public String getLastusername() {
		return lastusername;
	}

	public void setLastusername(String lastusername) {
		this.lastusername = lastusername;
	}

	public String getShopname() {
		return shopname;
	}

	public void setShopname(String shopname) {
		this.shopname = shopname;
	}

	public String getDescs() {
		return descs;
	}

	public void setDescs(String descs) {
		this.descs = descs;
	}

	public void setActiveid(Integer activeid) {
		this.activeid = activeid;
	}




	/**
	 * 翡翠
	 */
	public static String SALE_PRODUCT_EMELALD = "EM";

	/**
	 * 黄金
	 */
	public static String SALE_PRODUCT_GOLD = "GO";

}
