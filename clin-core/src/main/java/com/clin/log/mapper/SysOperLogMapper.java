package com.clin.log.mapper;

import com.alibaba.fastjson.JSONObject;
import com.clin.core.vo.CommonVO;
import com.clin.log.entity.SysOperLog;
import com.clin.log.vo.SysLogVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Title :  操作日志 数据层
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2022-02-17
 **/
@Mapper
public interface SysOperLogMapper {
    /**
     * 新增操作日志
     *
     * @param operLog 操作日志对象
     */
    int insertOperlog(SysOperLog operLog);


    List<LinkedHashMap<String, Object>> queryLogData(SysLogVO sysLogVO);

    @Delete("delete from sys_oper_log  where oper_id in ( ${ids})")
    void deleteSysLog(String ids);
}
