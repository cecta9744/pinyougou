package com.pinyougou.common.util;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * HttpClientUtils工具类
 * @date 2015年3月18日 上午11:47:27
 * @version 1.0
 */
public class HttpClientUtils {
	
	/** 创建可关闭的httpClient客户端对象 */
	private CloseableHttpClient httpClient;
	/** 定义状态码 */
	private int statusCode;
	/** 定义响应内容 */
	private String content;
	
	/** 定义构造器 */
	public HttpClientUtils(boolean https){
		try{
			/** 判断是否为HTTPS协议 */
			if (https) {
				SSLConnectionSocketFactory sslsf = 
						new SSLConnectionSocketFactory(SSLContext.getDefault());
				httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			} else {
				httpClient = HttpClients.createDefault();
			}
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * 发送GET请求
	 * @param url 请求URL
	 * @param params 请求参数
	 * @return 响应实体
	 */
	public String sendGet(String url, Map<String,String> params){
		try{
			/** 创建URI构建对象 */
			URIBuilder uriBuilder = new URIBuilder(url);
			/** 判断是否需要设置请求参数 */
			if (params != null && params.size() > 0){
				/** 设置GET请求需要的请求参数 */
				for (Map.Entry<String, String> entry : params.entrySet()){
					uriBuilder.setParameter(entry.getKey(), entry.getValue());
				}
			}
			/** 创建HttpGet请求方式对象 */
			HttpGet httpGet = new HttpGet(uriBuilder.build());
			return execute(httpGet);
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * 发送POST请求
	 * @param url 请求URL
	 * @param params 请求参数
	 * @return 响应实体
	 */
	public String sendPost(String url, Map<String,String> params){
		try{
			/** 创建HttpPost请求方式对象 */
			HttpPost httpPost = new HttpPost(url);
			/** 判断是否需要请求参数 */
			if (params != null && params.size() > 0){
				/** 定义List集合封装表单数据 */
				List<NameValuePair> nvpLists = new ArrayList<>();
				for (Map.Entry<String, String> entry : params.entrySet()){
					nvpLists.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				/** 设置post请求需要的参数 */
				httpPost.setEntity(new UrlEncodedFormEntity(nvpLists, Consts.UTF_8));
			}
			return execute(httpPost);
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * 发送POST请求
	 * @param url 请求URL
	 * @param params 请求参数
	 * @return 响应实体
	 */
	public String sendPost(String url, String params){
		try{
			/** 创建HttpPost请求方式对象 */
			HttpPost httpPost = new HttpPost(url);
			/** 判断是否需要请求参数 */
			if (!"".equals(params)){
				/** 设置post请求需要的参数 */
				httpPost.setEntity(new StringEntity(params, Consts.UTF_8));
			}
			return execute(httpPost);
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * 发送PUT请求
	 * @param url 请求URL
	 * @param params 请求参数
	 * @return 响应实体
	 */
	public String sendPut(String url, Map<String,String> params){
		try{
			/** 创建HttpPut请求方式对象 */
			HttpPut httpPut = new HttpPut(url);
			/** 判断是否需要请求参数 */
			if (params != null && params.size() > 0){
				/** 定义List集合封装表单数据 */
				List<NameValuePair> nvpLists = new ArrayList<>();
				for (Map.Entry<String, String> entry : params.entrySet()){
					nvpLists.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				/** 设置Put请求需要的参数 */
				httpPut.setEntity(new UrlEncodedFormEntity(nvpLists, Consts.UTF_8));
			}
			return execute(httpPut);
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * 发送DELETE请求
	 * @param url 请求URL
	 * @param params 请求参数
	 * @return 响应实体
	 */
	public String sendDelete(String url, Map<String,String> params){
		if (params == null){
			params = new HashMap<>();
		}
		params.put("_method", "delete");
		return sendPost(url, params);
	}
	
	/** 执行请求方法 */
	private String execute(HttpUriRequest httpUriRequest)throws Exception{
		/** 执行请求，得到可关闭的响应对象 */
		CloseableHttpResponse response = null;
		try{
			response = httpClient.execute(httpUriRequest);
			/** 设置响应状态码 */
			this.setStatusCode(response.getStatusLine().getStatusCode());
			if (response.getEntity() != null){
				content = EntityUtils.toString(response.getEntity(), Consts.UTF_8);
			}
			return content;
		}finally{
			if (response != null){
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/** setter and getter method */
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}