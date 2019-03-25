package com.pinyougou.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 操作Cookie工具类
 * @date 2015年4月24日 下午4:04:55
 * @version 1.0
 */
public final class CookieUtils {
	
	/** 定义Cookie名称的静态内部类 */
	public static class CookieName{
		/** 定义存放在Cookie中购物车的名称 */
		public static final String PINYOUGOU_CART = "pinyougou_cart";
	}
	
	
	/**
	 * 根据Cookie的名称获取指定的Cookie
	 * @param request 请求对象
	 * @param cookieName cookie的名称
	 * @return Cookie
	 */
	public static Cookie getCookie(HttpServletRequest request, String cookieName){
		/** 获取所有的Cookie */
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0){
        	for (Cookie cookie : cookies){
        		if (cookie.getName().equals(cookieName)){
        			return cookie;
        		}
        	}
        }
        return null;
	}
    /**
     * 根据CookieName获取指定的Cookie值
     * @param request 请求对象
     * @param cookieName cookie的名称
     * @param encoded 是否编码
     * @return Cookie的值
     */
    public static String getCookieValue(HttpServletRequest request, 
    				String cookieName, boolean encoded) {
    	/** 获取指定的Cookie */
    	Cookie cookie = getCookie(request, cookieName);
        String cookieValue = null;
        try {
	        if (cookie != null) {
     			if (encoded){
     				cookieValue = URLDecoder.decode(cookie.getValue(), "UTF-8");
     			}else{
     				cookieValue = cookie.getValue();
     			}
	        }
        } catch (Exception e) {
         	e.printStackTrace();
        }
        return cookieValue;
    }
 
    /**
     * 根据Cookie的名称删除指定的Cookie
     * @param request 请求对象
     * @param response 响应对象
     * @param cookieName cookie名称
     */
    public static void deleteCookie(HttpServletRequest request, 
    				HttpServletResponse response, String cookieName) {
    	setCookie(request, response, cookieName, null, 0, false);
    }
    
    /**
     * 设置Cookie
     * @param request 请求对象
     * @param response 响应对象
     * @param cookieName cookie的名称
     * @param cookieValue cookie的值
     * @param maxAge 存放Cookie的最大存放时间(按秒计算)
     * @param encoded 是否编码
     */
    public static void setCookie(HttpServletRequest request, 
								 HttpServletResponse response,
								 String cookieName, String cookieValue, 
								 int maxAge, boolean encoded) {
    	try {
        	/** 对Cookie的值进行判断 */
            if (cookieValue == null) {
                cookieValue = "";
            }
            if (encoded) {
                cookieValue = URLEncoder.encode(cookieValue, "UTF-8");
            }
            Cookie cookie = getCookie(request, cookieName);
            if (cookie == null){
            	cookie = new Cookie(cookieName, cookieValue);
            }
            /** 设置Cookie的值 */
            cookie.setValue(cookieValue);
            /** 设置最大存活时间 */
            cookie.setMaxAge(maxAge);
            /** 设置访问路径 */
            cookie.setPath("/");
            /** 添加到用户浏览器 */
            response.addCookie(cookie);
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
}