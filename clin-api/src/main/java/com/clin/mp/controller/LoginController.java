package com.clin.mp.controller;

import cn.hutool.core.util.StrUtil;
import com.clin.auth.service.AuthService;
import com.clin.core.result.ActionResult;
import com.clin.core.result.ListResult;
import com.clin.core.result.ResultBuilder;
import com.clin.log.common.annotation.Log;
import com.clin.log.common.enums.BusinessType;
import com.clin.mp.service.MpUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * Title :  TODO
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2022-03-13
 **/

@RestController
@RequestMapping("/mp/users")
public class LoginController  extends BaseMpController{


@Autowired
private MpUserService mpUserService;

    //用户登录操作
    @Log(title = "小程序端用户登录", businessType = BusinessType.LOGIN)
    @PostMapping("/loginin")
    public ListResult<Object> login(@RequestParam(name = "username", required = true) String username, @RequestParam(name = "password", required = true) String password){
        VerificationAuthOnly();
        Map m=mpUserService.login(username,password);
        return ResultBuilder.buildListSuccess(m);
    }

}
