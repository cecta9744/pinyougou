package com.pinyougou;

import com.pinyougou.common.util.HttpClientUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * SmsTest
 *
 * @author lee.siu.wah
 * @version 1.0
 * <p>File Created at 2019-03-16<p>
 */
public class SmsTest {
    public static void main(String[] args){
        // 创建 HttpClientUtils对象
        HttpClientUtils httpClientUtils = new HttpClientUtils(false);
        // 定义Map集合封装请求参数
        Map<String, String> params = new HashMap<>();
        params.put("phone", "17396783929");
        params.put("signName", "五子连珠");
        params.put("templateCode", "SMS_11480310");
        params.put("templateParam", "{'number':'888888'}");
        // 调用短信接口
        String content = httpClientUtils.sendPost("http://sms.pinyougou.com/sms/sendSms", params);
        System.out.println(content);
    }
}
