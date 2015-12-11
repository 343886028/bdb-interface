package com.bdbvip.pojo;

import java.io.Serializable;

/**
 * 分页查询基类
 * @author wuxibo
 */
public class BasePageVo implements Serializable{

	private static final long serialVersionUID = 6035785817008043387L;
	private String token;
	private Integer page;
	private Integer pagesize;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getPagesize() {
		return pagesize;
	}
	public void setPagesize(Integer pagesize) {
		this.pagesize = pagesize;
	}
}
