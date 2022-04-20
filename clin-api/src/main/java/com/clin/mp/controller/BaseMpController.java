package com.clin.mp.controller;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * <pre>
 * Title :  TODO
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2022-03-13
 **/
@RestController
public class BaseMpController {

    private  static final String AUTHCODE="N5401NkkZmdooLrn6kTSxKxGyQc";

    @Autowired
    private HttpServletRequest request;


    public  void VerificationAuth(){
//        Enumeration<String> es=request.getHeaderNames();
//        while (es.hasMoreElements()){
//            System.out.println(es.nextElement());
//        }
        String authorization=request.getHeader("authorization");
        String ROLE_CODE=request.getHeader("rolecode");
        if(!StrUtil.equals(AUTHCODE,authorization)){
            throw new UsernameNotFoundException(String.format("用户信息未认证！拒绝访问"));
        }
        if(StrUtil.isBlank(ROLE_CODE)){
            throw new UsernameNotFoundException(String.format("该用户没有分配对应权限！"));
        }
    }

    public  void VerificationAuthOnly(){
        String authorization=request.getHeader("authorization");
        if(!StrUtil.equals(AUTHCODE,authorization)){
            throw new UsernameNotFoundException(String.format("用户未认证！"));
        }
    }

    public String GetRoleCode(){
        return request.getHeader("rolecode");
    }
}
