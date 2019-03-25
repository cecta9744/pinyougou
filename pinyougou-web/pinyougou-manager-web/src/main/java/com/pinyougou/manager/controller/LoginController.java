package com.pinyougou.manager.controller;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录控制器
 *
 * @author lee.siu.wah
 * @version 1.0
 * <p>File Created at 2019-03-03<p>
 */
@RestController
public class LoginController {

    /** 获取登录用户名 */
    @GetMapping("/showLoginName")
    public Map<String,String> showLoginName(){
        // 获取安全上下文对象
        SecurityContext context = SecurityContextHolder.getContext();
        // 获取登录用户名
        String loginName = context.getAuthentication().getName();

        Map<String,String> data = new HashMap<>();
        data.put("loginName", loginName);
        return data;

    }
}
