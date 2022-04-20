package com.clin.mp.service.impl;

import cn.hutool.core.util.StrUtil;
import com.clin.auth.mapper.AuthMapper;
import com.clin.mp.service.MpUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <pre>
 * Title :  TODO
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2022-03-13
 **/
@Service("MpUserService")
public class MpUserServiceImpl implements MpUserService {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private AuthMapper authMapper;

    @Override
    public LinkedHashMap<String, String> login(String username,String password) {
        LinkedHashMap<String, String> user = authMapper.getSysUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("找不到此用户名%s。", username));
        }
        if(!encoder.matches(password, user.get("PASSWORD"))){
            throw new UsernameNotFoundException(String.format("密码错误！"));
        }
        return user;
    }
}
