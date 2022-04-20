package com.clin.mp.controller;

import cn.hutool.core.util.StrUtil;
import com.clin.auth.service.SysUserDetailService;
import com.clin.core.result.ActionResult;
import com.clin.core.result.ListResult;
import com.clin.core.result.ResultBuilder;
import com.clin.system.entity.SysUser;
import com.clin.system.service.SysRoleService;
import com.clin.system.service.SysUserService;
import com.clin.system.vo.SysUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * <pre>
 * Title :  TODO
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2022-03-13
 **/
@RestController
@RequestMapping("/mp/sysuserdetail")
public class UserController  extends BaseMpController{


    @Autowired
    private SysUserDetailService sysUserDetailService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysUserService sysUserService;
    /**
     * 修改用户密码
     *
     * @param password    原密码
     * @param newPassword 新密码
     * @param id          用户IDli
     * @return
     */
    @PostMapping(path = "/updatePassword")
    public ActionResult updatePassword(@RequestParam(name = "password", required = true) String password,
                                       @RequestParam(name = "newPassword", required = true) String newPassword, @RequestParam(name = "id", required = true) Long id) {
        VerificationAuth();
        sysUserDetailService.updatePassword(password, newPassword, id);
        return ResultBuilder.buildActionSuccess();
    }



    /**
     * 查询角色的多选框数据
     *
     * @return
     */
    @GetMapping(path = "/queryRoleNameCheckbox")
    public ListResult<Object> queryRoleNameCheckbox() {
        VerificationAuth();
        if(StrUtil.equals(GetRoleCode(),"ROLE_MANAGER") ||StrUtil.equals(GetRoleCode(),"ROLE_EAC_ADMIN")){
            List<LinkedHashMap<String, Object>> data = sysRoleService.queryRoleNameCheckbox();
            return ResultBuilder.buildListSuccess(data);
        }else{
            throw new UsernameNotFoundException(String.format("用户没有权限查看角色！"));
        }
    }

    /**
     * 停用启用用户
     *
     * @param id 用户ID
     * @return
     */
    @PostMapping(path = "/deleteSysUser")
    public ActionResult deleteSysUser(@RequestParam(name = "id", required = true) Long[] id) {
        VerificationAuth();
        sysUserService.statusSysUser(id);
        return ResultBuilder.buildActionSuccess();
    }

    /**
     * 将对应的角色授予用户
     *
     * @param sysUserVO 用户前端参数
     * @return
     */
    @PostMapping(path = "/authorizeRoleToUser")
    public ActionResult authorizeRoleToUser( @RequestBody SysUserVO sysUserVO) {
        VerificationAuth();
        sysUserService.insertRoleIdUserId(sysUserVO.getRoleId(), sysUserVO.getUserId());
        return ResultBuilder.buildActionSuccess();
    }

    /**
     * 编辑用户
     *
     * @param sysUser 用户对象
     * @return
     */
    @PostMapping(path = "/updateSysUser")
    public ActionResult updateSysUser(@RequestBody SysUser sysUser) {
        VerificationAuth();
        sysUserService.updateSysUser(sysUser);
        return ResultBuilder.buildActionSuccess();
    }


    /**
     * 新增用户并赋值角色
     *
     * @param sysUser 用户对象
     * @return
     */
    @PostMapping(path = "/insertSysUserAndRole")
    public ActionResult insertSysUserAndRole(@RequestBody SysUser sysUser) {
        VerificationAuth();
        sysUserService.insertSysUserAndRole(sysUser);
        return ResultBuilder.buildActionSuccess();
    }
}
