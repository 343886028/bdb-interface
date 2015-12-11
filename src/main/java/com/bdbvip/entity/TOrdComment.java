package com.bdbvip.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the t_ord_comment database table.
 * 
 */
@Entity
@Table(name="t_ord_comment")
@NamedQuery(name="TOrdComment.findAll", query="SELECT t FROM TOrdComment t")
public class TOrdComment implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L;
	private Integer commentid;
	private String comment;
	private Date createtime;
	private String imgurl;
	private String imgurl2;
	private String imgurl3;
	private Integer orderitemid;
	private String orderno;
	private String retrycomment;
	private Date retrytime;
	private Integer retryuserid;
	private Integer userid;

	public TOrdComment() {
	}


	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getCommentid() {
		return this.commentid;
	}

	public void setCommentid(Integer commentid) {
		this.commentid = commentid;
	}


	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
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


	public Integer getOrderitemid() {
		return this.orderitemid;
	}

	public void setOrderitemid(Integer orderitemid) {
		this.orderitemid = orderitemid;
	}


	public String getOrderno() {
		return this.orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}


	public String getRetrycomment() {
		return this.retrycomment;
	}

	public void setRetrycomment(String retrycomment) {
		this.retrycomment = retrycomment;
	}


	@Temporal(TemporalType.TIMESTAMP)
	public Date getRetrytime() {
		return this.retrytime;
	}

	public void setRetrytime(Date retrytime) {
		this.retrytime = retrytime;
	}


	public Integer getRetryuserid() {
		return this.retryuserid;
	}

	public void setRetryuserid(Integer retryuserid) {
		this.retryuserid = retryuserid;
	}


	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

}