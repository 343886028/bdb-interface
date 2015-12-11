package com.bdbvip.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.bdbvip.utils.common.date.DateUtil;


/**
 * The persistent class for the t_mem_cash_trade_record database table.
 * 
 */
@Entity
@Table(name="t_mem_cash_trade_record")
@NamedQuery(name="TMemCashTradeRecord.findAll", query="SELECT t FROM TMemCashTradeRecord t")
public class TMemCashTradeRecord implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private int id;
	private BigDecimal accountcashmoney;
	private BigDecimal accountfrzoenmoney;
	private BigDecimal cashmoney;
	private Date createtime;
	private int fromuser;
	private BigDecimal frzoenmoney;
	private String inorout;
	private String para;
	private String remark;
	private BigDecimal tradecashmoney;
	private BigDecimal tradefrozenmoney;
	private String tradeno;
	private BigDecimal tradetotalmoney;
	private String tradetype;
	private int userid;

	public TMemCashTradeRecord() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public BigDecimal getAccountcashmoney() {
		return this.accountcashmoney;
	}

	public void setAccountcashmoney(BigDecimal accountcashmoney) {
		this.accountcashmoney = accountcashmoney;
	}


	public BigDecimal getAccountfrzoenmoney() {
		return this.accountfrzoenmoney;
	}

	public void setAccountfrzoenmoney(BigDecimal accountfrzoenmoney) {
		this.accountfrzoenmoney = accountfrzoenmoney;
	}


	public BigDecimal getCashmoney() {
		return this.cashmoney;
	}

	public void setCashmoney(BigDecimal cashmoney) {
		this.cashmoney = cashmoney;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatetime() {
		return this.createtime;
	}

	@Transient
	public String getStrCreatetime(){
		return DateUtil.date2String(this.createtime,new SimpleDateFormat("yyyy-MM-dd HH:MM:ss"));
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}


	public int getFromuser() {
		return this.fromuser;
	}

	public void setFromuser(int fromuser) {
		this.fromuser = fromuser;
	}


	public BigDecimal getFrzoenmoney() {
		return this.frzoenmoney;
	}

	public void setFrzoenmoney(BigDecimal frzoenmoney) {
		this.frzoenmoney = frzoenmoney;
	}


	public String getInorout() {
		return this.inorout;
	}

	public void setInorout(String inorout) {
		this.inorout = inorout;
	}


	public String getPara() {
		return this.para;
	}

	public void setPara(String para) {
		this.para = para;
	}


	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}


	public BigDecimal getTradecashmoney() {
		return this.tradecashmoney;
	}

	public void setTradecashmoney(BigDecimal tradecashmoney) {
		this.tradecashmoney = tradecashmoney;
	}


	public BigDecimal getTradefrozenmoney() {
		return this.tradefrozenmoney;
	}

	public void setTradefrozenmoney(BigDecimal tradefrozenmoney) {
		this.tradefrozenmoney = tradefrozenmoney;
	}


	public String getTradeno() {
		return this.tradeno;
	}

	public void setTradeno(String tradeno) {
		this.tradeno = tradeno;
	}


	public BigDecimal getTradetotalmoney() {
		return this.tradetotalmoney;
	}

	public void setTradetotalmoney(BigDecimal tradetotalmoney) {
		this.tradetotalmoney = tradetotalmoney;
	}


	public String getTradetype() {
		return this.tradetype;
	}

	public void setTradetype(String tradetype) {
		this.tradetype = tradetype;
	}


	public int getUserid() {
		return this.userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

}