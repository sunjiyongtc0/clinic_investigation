package com.clin.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.clin.core.result.ActionResult;
import com.clin.core.vo.CommonVO;
import com.clin.core.result.ListResult;
import com.clin.core.result.ResultBuilder;
import com.clin.log.service.SysOperLogService;
import com.clin.log.vo.SysLogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <pre>
 * Title :  TODO
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2022-03-18
 **/
@RestController
@RequestMapping("/syslog")
public class SysLogController {

    @Autowired
    private SysOperLogService sysOperLogService;

    /**
     * 分页显示信息
     * */
    @GetMapping("/queryLogData")
    public ListResult<Object> queryLogData(SysLogVO sysLogVO){
        Map<String, Object> data =sysOperLogService.queryLogData(sysLogVO);
        return ResultBuilder.buildListSuccess(data);
    }

      @GetMapping("/deleteSysLog")
    public ActionResult deleteSysLog(String  ids){
        sysOperLogService.deleteSysLog(ids);
        return ResultBuilder.buildActionSuccess();
    }


}
