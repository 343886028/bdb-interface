package com.bdbvip.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

@SuppressWarnings("unused")
public class Posturl {

	public static String postRequest(String url, Map<String, String> data) {
		// 响应内容
		String result = "";

		// 定义http客户端对象--httpClient
		HttpClient httpClient = new HttpClient();

		// 定义并实例化客户端链接对象-postMethod
		PostMethod postMethod = new PostMethod(url);

		try {
			// 设置http的头
			postMethod.getParams().setParameter(
					HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
			postMethod.setRequestHeader("ContentType",
					"application/x-www-form-urlencoded;charset=UTF-8");
			if (data != null) {
				NameValuePair[] data1 = new NameValuePair[data.size()];
				int i = 0;
				for (String mapKey : data.keySet()) {
					// 填入各个表单域的值
					String vString = data.get(mapKey) == null ? "" : data.get(
							mapKey).toString();
					// vString = new String(vString.getBytes("utf-8"),"GBK");
					data1[i++] = new NameValuePair(mapKey, vString);
				}

				// 将表单的值放入postMethod中
				postMethod.setRequestBody(data1);
			}
			// 定义访问地址的链接状态
			int statusCode = 0;
			try {
				// 客户端请求url数据
				statusCode = httpClient.executeMethod(postMethod);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 请求成功状态-200
			if (statusCode == HttpStatus.SC_OK) {
				try {
					result = postMethod.getResponseBodyAsString();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("请求返回状态：" + statusCode);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// 释放链接
			postMethod.releaseConnection();
			httpClient.getHttpConnectionManager().closeIdleConnections(0);
		}
		return result;
	}

	public static String postRequest(String url, Map<String, Object> data,
			Map<String, String> cookie, String domain) {
		// 响应内容
		String result = "";

		// 定义http客户端对象--httpClient
		HttpClient httpClient = new HttpClient();

		// 定义并实例化客户端链接对象-postMethod
		PostMethod postMethod = new PostMethod(url);

		try {
			// 设置http的头
			postMethod.getParams().setParameter(
					HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
			postMethod.setRequestHeader("ContentType",
					"application/x-www-form-urlencoded;charset=UTF-8");
			if (cookie != null) {
				if (domain == null) {
					domain = "";
				}
				HttpState initialState = new HttpState();
				for (String cookieName : cookie.keySet()) {
					Cookie c = new Cookie(domain, cookieName,
							cookie.get(cookieName), "/", -1, true);
					initialState.addCookie(c);
				}
				httpClient.setState(initialState);
			}
			if (data != null) {
				NameValuePair[] data1 = new NameValuePair[data.size()];
				int i = 0;
				for (String mapKey : data.keySet()) {
					// 填入各个表单域的值
					String vString = data.get(mapKey) == null ? "" : data.get(
							mapKey).toString();
					// vString = new String(vString.getBytes("utf-8"),"GBK");
					data1[i++] = new NameValuePair(mapKey, vString);
				}

				// 将表单的值放入postMethod中
				postMethod.setRequestBody(data1);
			}

			// 定义访问地址的链接状态
			int statusCode = 0;
			try {
				// 客户端请求url数据
				statusCode = httpClient.executeMethod(postMethod);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// 请求成功状态-200
			if (statusCode == HttpStatus.SC_OK) {
				try {
					result = postMethod.getResponseBodyAsString();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("请求返回状态：" + statusCode);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			// 释放链接
			postMethod.releaseConnection();
			httpClient.getHttpConnectionManager().closeIdleConnections(0);
		}
		return result;
	}

}
