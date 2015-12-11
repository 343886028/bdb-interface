package com.bdbvip.product.interfaces.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.bdbvip.pojo.BasePageVo;
import com.bdbvip.utils.common.date.DateUtil;

/**
 * 用户竞价记录pojo
  * @ClassName: SaleBiddingVo
  * @Description: TODO
  * @date 2015年12月4日 下午5:17:15
 */
public class SaleTMemBiddingVo extends BasePageVo {

	private static final long serialVersionUID = -6138399245218885189L;
	private Integer id;
	private Integer activeid;
	private String activename;
	private BigDecimal amount;
	private Integer configid;
	private Date createtime;
	private String procode;
	private Integer productid;
	private String productname;
	private String status;
	private String type;
	private Integer userid;
	private String username;
	
	
	public SaleTMemBiddingVo(String procode) {
		super();
		this.procode = procode;
	}
	
	public SaleTMemBiddingVo() {
		super();
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getActiveid() {
		return activeid;
	}
	public void setActiveid(Integer activeid) {
		this.activeid = activeid;
	}
	public String getActivename() {
		return activename;
	}
	public void setActivename(String activename) {
		this.activename = activename;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Integer getConfigid() {
		return configid;
	}
	public void setConfigid(Integer configid) {
		this.configid = configid;
	}
	public String getCreatetime() {
		if (createtime!=null) {
			return DateUtil.simpleDateFormat3.format(createtime);
		}
		return "";
	}
	public Date getCreatetimeFormat(){
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getProcode() {
		return procode;
	}
	public void setProcode(String procode) {
		this.procode = procode;
	}
	public Integer getProductid() {
		return productid;
	}
	public void setProductid(Integer productid) {
		this.productid = productid;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	
	
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}



	/**
	 * 人为报价
	 */
	public static String BIDDING_PERSION="0";
	/**
	 * 托管报价
	 */
	public static String BIDDING_SYSTEM="1";
	

}
