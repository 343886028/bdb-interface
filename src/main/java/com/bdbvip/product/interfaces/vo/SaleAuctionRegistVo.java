package com.bdbvip.product.interfaces.vo;

import java.math.BigDecimal;

/**
 * 用户报名拍卖pojo
 * 
 * @author Administrator
 *
 */
public class SaleAuctionRegistVo {
	private Integer userid;
	private String token;
	private String paypwd;
	private BigDecimal money;
	private String procode;

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPaypwd() {
		return paypwd;
	}

	public void setPaypwd(String paypwd) {
		this.paypwd = paypwd;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public String getProcode() {
		return procode;
	}

	public void setProcode(String procode) {
		this.procode = procode;
	}

}
