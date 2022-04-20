package com.clin.log.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.clin.core.result.PaginationBuilder;
import com.clin.core.utils.IpUtils;
import com.clin.core.utils.ServletUtils;
import com.clin.core.vo.CommonVO;
import com.clin.log.entity.SysOperLog;
import com.clin.log.mapper.SysOperLogMapper;
import com.clin.log.service.SysOperLogService;
import com.clin.log.vo.SysLogVO;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Title :  操作日志 服务层处理
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2022-02-17
 **/
@Service("SysOperLogService")
public class SysOperLogServiceImpl  implements SysOperLogService {

    @Autowired
    private SysOperLogMapper operLogMapper;


    @Override
    public int insertOperlog(SysOperLog operLog) {
            return operLogMapper.insertOperlog(operLog);
    }


    public void recordLogininfor(String username, int status,String msg){
        SysOperLog logininfor = new SysOperLog();
        logininfor.setOperName(username);
        logininfor.setTitle("用户登录");
        logininfor.setOperUrl("login");
        logininfor.setOperIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
        logininfor.setMethod("loadUserByUsername");
        logininfor.setStatus(status);
        logininfor.setErrorMsg(msg);
        operLogMapper.insertOperlog(logininfor);
    }

    @Override
    public Map<String, Object> queryLogData(SysLogVO sysLogVO ) {
        List<LinkedHashMap<String, Object>> resultList = new ArrayList<>();
        String sorter=sysLogVO.getSorter();
        if (StringUtils.isNotBlank(sorter)) {
            String sort = sorter.substring(0, sorter.lastIndexOf('_'));
            String sequence = "ascend".equals(sorter.substring(sorter.lastIndexOf('_') + 1)) ? "ASC" : "DESC";
            sysLogVO.setSort(sort);
            sysLogVO.setSequence(sequence);
        } else {
            sysLogVO.setSort("oper_time");
            sysLogVO.setSequence("DESC");

        }

        Page<Object> page = PageHelper.startPage(sysLogVO.getCurrentPage(), sysLogVO.getPageSize());
        resultList = operLogMapper.queryLogData(sysLogVO);
        return PaginationBuilder.buildResult(resultList, page.getTotal(), sysLogVO.getCurrentPage(), sysLogVO.getPageSize());
    }

    @Override
    public void deleteSysLog(String ids) {
        operLogMapper.deleteSysLog(ids);
    }
}
