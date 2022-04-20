package com.clin.mp.controller;

import com.clin.core.result.ListResult;
import com.clin.core.result.ResultBuilder;
import com.clin.system.service.SysUserService;
import com.clin.system.vo.SysUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <pre>
 * Title :  TODO
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2022-04-01
 **/

@RestController
@RequestMapping("/mp/sysuser")
public class MpSysUserController  extends BaseMpController{

    @Autowired
    private SysUserService sysUserService;



    /**
     * 查询用户分页
     *
     * @param sysUserVO 用户前端参数
     * @return
     */
    @GetMapping(path = "/querySysUser")
    public ListResult<Object> querySysUser(SysUserVO sysUserVO) {
        VerificationAuth();
        Map<String, Object> data = sysUserService.queryMpSysUser(sysUserVO,GetRoleCode());
        return ResultBuilder.buildListSuccess(data);
    }
}
