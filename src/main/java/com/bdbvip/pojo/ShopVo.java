package com.bdbvip.pojo;

public class ShopVo extends BaseVo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3490027142728449441L;

	String shopname;
	String realname;
	String cardimg;
	String telephone;
	String kindscode;
	String agree;
	int id;
	
	
	public String getShopname() {
		return shopname;
	}
	public void setShopname(String shopname) {
		this.shopname = shopname;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getCardimg() {
		return cardimg;
	}
	public void setCardimg(String cardimg) {
		this.cardimg = cardimg;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getKindscode() {
		return kindscode;
	}
	public void setKindscode(String kindscode) {
		this.kindscode = kindscode;
	}
	public String getAgree() {
		return agree;
	}
	public void setAgree(String agree) {
		this.agree = agree;
	}
}
