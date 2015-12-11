package com.bdbvip.pojo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户钱包账户pojo
  * @ClassName: CashAccountVo
  * @Description: TODO
  * @date 2015年12月1日 上午10:34:01
 */
public class CashAccountVo {
	
	private int id;
	private String accountflag;
	private BigDecimal cashmoney;
	private BigDecimal frozenmoney;
	private int userid;
	private Date createtime;
	private String logmsg;
	private String logtype;
	private String optuserid;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAccountflag() {
		return accountflag;
	}
	public void setAccountflag(String accountflag) {
		this.accountflag = accountflag;
	}
	public BigDecimal getCashmoney() {
		return cashmoney;
	}
	public void setCashmoney(BigDecimal cashmoney) {
		this.cashmoney = cashmoney;
	}
	public BigDecimal getFrozenmoney() {
		return frozenmoney;
	}
	public void setFrozenmoney(BigDecimal frozenmoney) {
		this.frozenmoney = frozenmoney;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getLogmsg() {
		return logmsg;
	}
	public void setLogmsg(String logmsg) {
		this.logmsg = logmsg;
	}
	public String getLogtype() {
		return logtype;
	}
	public void setLogtype(String logtype) {
		this.logtype = logtype;
	}
	public String getOptuserid() {
		return optuserid;
	}
	public void setOptuserid(String optuserid) {
		this.optuserid = optuserid;
	}
	
	
	/**
	 * 日志类型的开头   钱包相关模块日志操作开头
	 * 钱包相关操作日志类型以 "CA"开头
	 */
	
	public static String CASH_ACCOUNT_LOGTYPE="CA";
	
	
	/**
	 * 查询相关日志的错误次数
	 */
	public static int CASH_ACCOUNT_ERROR_COUNT=3;
	
	/**
	 * 日志类型: 验证支付密码
	 */
	public static String CASH_ACCOUNT_VAILD="CAV";
	
	/**
	 * 日志类型:修改支付密码
	 */
	public static String CASH_ACCOUNT_UPDATE="CAU";
	
	/**
	 * 日志类型:初始化支付密码
	 */
	public static String CASH_ACCOUNT_INIT="CAI";
	
	
	/**
	 * 日志类型:重置密码前验证
	 */
	public static String CASH_ACCOUNT_RESET_VALID="CARV";
	
	/**
	 * 日志类型:忘记密码重置密码
	 */
	public static String CASH_ACCOUNT_RESET="CAR";
	
	/**
	 * 日志类型：用户提现
	 */
	public static String CASH_ACCOUNT_DRAW="CADW";
	
	
	/**
	 * 日志类型：用户充值
	 */
	public static String CASH_ACCOUNT_PAY="CAPY";
	
	/**
	 * 日志类型：解锁用户
	 */
	public static String CASH_ACCOUNT_UNLOCK_LOG="CAUK";
	
	/**
	 * 日志类型：保证金
	 */
	public static String CASH_ACCOUNT_BOUND="CABD";
	
	
	/**
	 * 日志消息:操作成功标识
	 */
	public static String CASH_ACCOUNT_SUCCESS="1";
	
	
	/**
	 * 日志消息:操作失败标识
	 */
	public static String CASD_ACCOUNT_FAIL="0";
	
	
	
	/**
	 * 账户锁定状态
	 */
	public static String CASH_ACCOUNT_LOCK="1";
	
	
	/**
	 * 非锁定状态
	 */
	public static String CASH_ACCOUNT_UNLOCK="0";
	
	

}
