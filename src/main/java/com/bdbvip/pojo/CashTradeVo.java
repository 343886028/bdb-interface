package com.bdbvip.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 *  用户交易信息
  *   6XXX  代表收入
  *   7XXX  代表支出
  *   9XXX  代表中性
  * @ClassName: CashTradeVo
  * @Description: TODO
  * @date 2015年11月26日 下午2:40:11
 */
public class CashTradeVo implements Serializable {

	
	private static final long serialVersionUID = -5351984009681728977L;
	
	
	/**
	 * 开始时间
	 */
	private Date startTime;
	
	/**
	 * 结束时间
	 */
	private Date endTime;
	
	
	private int id;
	private BigDecimal accountcashmoney;
	private BigDecimal accountfrzoenmoney;
	private BigDecimal cashmoney;
	private Date createtime;
	
	/**
	 * 交易来自
	 */
	private String fromuser;
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
	private int fronuserid;
	
	
	
	
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getAccountcashmoney() {
		return accountcashmoney;
	}

	public void setAccountcashmoney(BigDecimal accountcashmoney) {
		this.accountcashmoney = accountcashmoney;
	}

	public BigDecimal getAccountfrzoenmoney() {
		return accountfrzoenmoney;
	}

	public void setAccountfrzoenmoney(BigDecimal accountfrzoenmoney) {
		this.accountfrzoenmoney = accountfrzoenmoney;
	}

	public BigDecimal getCashmoney() {
		return cashmoney;
	}

	public void setCashmoney(BigDecimal cashmoney) {
		this.cashmoney = cashmoney;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}


	public String getFromuser() {
		return fromuser;
	}

	public void setFromuser(String fromuser) {
		this.fromuser = fromuser;
	}

	public int getFronuserid() {
		return fronuserid;
	}

	public void setFronuserid(int fronuserid) {
		this.fronuserid = fronuserid;
	}

	public BigDecimal getFrzoenmoney() {
		return frzoenmoney;
	}

	public void setFrzoenmoney(BigDecimal frzoenmoney) {
		this.frzoenmoney = frzoenmoney;
	}

	public String getInorout() {
		return inorout;
	}

	public void setInorout(String inorout) {
		this.inorout = inorout;
	}

	public String getPara() {
		return para;
	}

	public void setPara(String para) {
		this.para = para;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public BigDecimal getTradecashmoney() {
		return tradecashmoney;
	}

	public void setTradecashmoney(BigDecimal tradecashmoney) {
		this.tradecashmoney = tradecashmoney;
	}

	public BigDecimal getTradefrozenmoney() {
		return tradefrozenmoney;
	}

	public void setTradefrozenmoney(BigDecimal tradefrozenmoney) {
		this.tradefrozenmoney = tradefrozenmoney;
	}

	public String getTradeno() {
		return tradeno;
	}

	public void setTradeno(String tradeno) {
		this.tradeno = tradeno;
	}

	public BigDecimal getTradetotalmoney() {
		return tradetotalmoney;
	}

	public void setTradetotalmoney(BigDecimal tradetotalmoney) {
		this.tradetotalmoney = tradetotalmoney;
	}

	public String getTradetype() {
		return tradetype;
	}

	public void setTradetype(String tradetype) {
		this.tradetype = tradetype;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}



	/**
	 * 充值
	 */
	public static String TRADING_TYPE_PY="PY"; 
	
	/**
	 * 提现
	 */
	public static String TRADING_TYPE_DW="DW"; 

	/**
	 * 打赏
	 */
	public static String TRADING_TYPE_RW="RW"; 
	
	/**
	 * 商品交易
	 */
	public static String TRADING_TYPE_CP="CP";
	
	/**
	 * 退款
	 */
	public static String TRADING_TYPE_RD="RD"; 
	
	/**
	 * 保证金
	 */
	public static String TRADING_TYPE_BD="BD"; //保证金
	
	/**
	 * 转账
	 */
	public static String TRADING_TYPE_TC="TC"; //转账
	
	
	
	
	/**
	 * 充值收入
	 */
	public static String TRADING_TYPE_PY_IN="6001";
	
	/**
	 * 提现支出
	 */
	public static String TRADING_TYPE_DW_OUT="7001";
	
	/**
	 * 打赏收入
	 */
	public static String TRADING_TYPE_RW_IN="6002";
	
	/**
	 * 打赏支出
	 */
	public static String TRADING_TYPE_RW_OUT="7002";
	
	/**
	 * 商品交易收入
	 */
	public static String TRADING_TYPE_CP_IN="6003";
	
	/**
	 * 商品交易支出
	 */
	public static String TRADING_TYPE_CP_OUT="7003";
	
	/**
	 * 保证金收入
	 */
	public static String TRADING_TYPE_BD_IN="6005";
	
	/**
	 * 保证金支出
	 */
	public static String TRADING_TYPE_BD_OUT="7005";
	
	/**
	 * 返还保证金收入
	 */
	public static String TRADING_TYPE_BD_FIN="6015";
	
	/**
	 * 返还保证经支出
	 */
	public static String TRADING_TYPE_BD_FOUT="7015";
	
	/**
	 * 退款收入
	 */
	public static String TRADING_TYPE_RD_IN="6004";
	
	/**
	 * 退款支出
	 */
	public static String TRADING_TYPE_RD_OUT="7004"; 
	
	/**
	 * 转账
	 */
	public static String TRADING_TYPE_TC_NO="9001";
	
	/**
	 * 充值成功
	 */
	public static String TRADING_TYPE_PY_SUCCESS="1";
	
	/**
	 * 充值失败
	 */
	public static String TRADING_TYPE_PY_FAIL="0"; 
	
	/**
	 * 提现成功
	 */
	public static String TRADING_TYPE_DW_SUCCESS="1"; 
	
	/**
	 * 提现失败
	 */
	public static String TRADING_TYPE_DW_FAIL="0"; 
	

	/**
	 * 收入
	 */
	public static String TRADING_IN="0";
	
	/**
	 * 支出
	 */
	public static String TRADING_OUT="1";
	
	/**
	 * 提现处理中
	 */
	public static String TRADING_DRAW_PROCEING="0";
	
	/**
	 * 提现审核
	 */
	public static String TRADING_DRAW_EXAMINE="1";
	
	/**
	 *提现处理完成 
	 */
	public static String TRADING_DRAW_PROCE="2";
	
	/**
	 * 提现银行处理失败
	 */
	public static String TRADING_DRAW_BANK_FIAL="3";
	
	/**
	 * 提现取消
	 */
	public static String TRADING_DRAW_CANCLE="4";
	
	/**
	 * 发送短信
	 */
	public static String TRADING_SENDFLAG_TRUE="1";
	
	/**
	 * 不发送短信
	 */
	public static String TRADING_SENDFLAG_FALSE="0";
	


}
