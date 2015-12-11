package com.bdbvip.pojo;

/**
 * 报名活动pojo
 * @author Administrator
 *
 */
public class SaleJoinActivityVo extends BasePageVo {

	private static final long serialVersionUID = -4752482446898857164L;
	private Integer id;
	private Integer activeid;
	private String activename;
	private Integer activeuserid;
	private Integer proid;
	private String status;
	private Integer userid;
	private String procode;
	private String remark;
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
	public Integer getActiveuserid() {
		return activeuserid;
	}
	public void setActiveuserid(Integer activeuserid) {
		this.activeuserid = activeuserid;
	}
	public Integer getProid() {
		return proid;
	}
	public void setProid(Integer proid) {
		this.proid = proid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public String getProcode() {
		return procode;
	}
	public void setProcode(String procode) {
		this.procode = procode;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	/**
	 * 未审核
	 */
	public static String ACTIVITY_STATUS_AUDIT_NO="0";
	/**
	 * 审核不通过
	 */
	public static String ACTIVITY_STATUS_AUDIT_NOTBY="2";
	/**
	 * 审核通过
	 */
	public static String ACTIVITY_STATUS_AUDIT_THROUGH="1";
	

}
