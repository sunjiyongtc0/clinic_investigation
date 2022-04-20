package com.clin.auth.controller;


import com.clin.auth.service.AuthService;
import com.clin.core.result.ActionResult;
import com.clin.core.result.ListResult;
import com.clin.core.result.ResultBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.LinkedHashMap;

/**
 * <pre>
 * Title : 用户认证授权信息的控制层
 * </pre>
 *
 * @author xiashilong
 * @since 2021-11-18
 **/
@RestController
@RequestMapping("/users")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * 查询当前用户信息
     */
    @GetMapping(path = "/getSysUser")
    public ListResult<Object> getSysUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LinkedHashMap<String, Object> data = authService.getSysUser(authentication.getName());
        return ResultBuilder.buildListSuccess(data);
    }

    @GetMapping(value = "/current")
    public Principal getSysUser(Principal principal) {
        return principal;
    }


    @Autowired
    private SessionRegistry sessionRegistry;

    /**
     * 注册一个新的会话
     *
     * @param request 请求对象
     * @return
     */
    @PostMapping(path = "/registerNewSession")
    public ActionResult registerNewSession(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        sessionRegistry.registerNewSession(request.getSession().getId(), authentication.getPrincipal());
        return ResultBuilder.buildActionSuccess();
    }

}
