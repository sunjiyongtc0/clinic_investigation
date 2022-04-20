package com.clin.controller;

import cn.hutool.core.util.IdUtil;
import com.clin.core.result.ListResult;
import com.clin.core.result.ResultBuilder;
import com.clin.system.entity.SysUser;
import com.clin.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * <pre>
 * Title :  TODO
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2022-03-13
 **/
@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${web.upload.img.path}")
    private String UploadPath;

    @Autowired
    private SysUserService sysUserService;



    @PostMapping("/uploadImg")
    public ListResult<Object> uploadImg(@RequestParam(name = "file",required=false) MultipartFile file, @RequestParam(name = "id", required = true) long id , @RequestParam(name = "nickname", required = true) String nickname) throws IOException {
        SysUser sysUser=new SysUser();
        String imgName="";
        if(file!=null&&!file.isEmpty()){
            // 上传文件路径
            String filePath = UploadPath;
            String fileId=IdUtil.objectId();
            imgName=fileId+".jpg";
            File f=new File(filePath+imgName);
            file.transferTo(f);
            sysUser.setAvatar(imgName);
        }
        sysUser.setId(id);
        sysUser.setNickname(nickname);
        sysUserService.updateNickName(sysUser);
        return ResultBuilder.buildListSuccess(imgName);
    }



}
