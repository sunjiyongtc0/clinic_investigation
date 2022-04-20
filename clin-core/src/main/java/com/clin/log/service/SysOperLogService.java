package com.clin.log.service;

import com.alibaba.fastjson.JSONObject;
import com.clin.core.vo.CommonVO;
import com.clin.log.entity.SysOperLog;
import com.clin.log.vo.SysLogVO;

import java.util.Map;

/**
 * <pre>
 * Title :  操作日志 服务层
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2022-02-17
 **/
public interface SysOperLogService {


    /**
     * 新增操作日志
     *
     * @param operLog 操作日志对象
     * @return 结果
     */
     int insertOperlog(SysOperLog operLog);

     void recordLogininfor(String username, int status, String msg);

    Map<String, Object> queryLogData(SysLogVO sysLogVO);

    void deleteSysLog(String ids);
}
