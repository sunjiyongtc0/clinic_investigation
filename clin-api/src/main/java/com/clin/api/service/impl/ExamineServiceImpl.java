package com.clin.api.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.clin.api.mapper.ExamineMapper;
import com.clin.api.service.ExamineService;
import com.clin.api.vo.ProDataVO;
import com.clin.auth.utils.AgeUtils;
import com.clin.core.result.PaginationBuilder;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Title :  TODO
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2022-03-11
 **/
@Service("ExamineService")
public class ExamineServiceImpl implements ExamineService {

    @Autowired
    private ExamineMapper examineMapper;


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public long  CreateExamine(JSONObject jo) {
        long pdcId=jo.getJSONObject("pdc").getLong("id");
        jo.put("pdcId",pdcId);
        String birth=jo.getJSONObject("pdc").getString("birth");
        jo.put("age", AgeUtils.getAgeFromBirthTime(birth));
        jo.put("config",jo.getJSONArray("config").toJSONString());
        examineMapper.InsertExwait(jo);
        JSONObject pdc=jo.getJSONObject("pdc");
        pdc.put("bakId",jo.get("bakId"));
        examineMapper.InsertPdc(pdc);
        examineMapper.UpdatePQByPdcId(pdcId,2l);
        examineMapper.UpdatePQById(jo.getLong("pdId"),2l);
        return jo.getLong("bakId");
    }

    @Override
    public Map<String, Object> queryExwaitList(ProDataVO proDataVO) {
        List<LinkedHashMap<String, Object>> resultList = new ArrayList<>();
        String sorter=proDataVO.getSorter();
        if (StringUtils.isNotBlank(sorter)) {
            String sort = sorter.substring(0, sorter.lastIndexOf('_'));
            String sequence = "ascend".equals(sorter.substring(sorter.lastIndexOf('_') + 1)) ? "ASC" : "DESC";
            proDataVO.setSort(sort);
            proDataVO.setSequence(sequence);
        } else {
            proDataVO.setSort("create_time");
            proDataVO.setSequence("DESC");

        }
        Page<Object> page = PageHelper.startPage(proDataVO.getCurrentPage(), proDataVO.getPageSize());
        resultList = examineMapper.queryExwaitList(proDataVO);
        return PaginationBuilder.buildResult(resultList, page.getTotal(), proDataVO.getCurrentPage(), proDataVO.getPageSize());
    }


    @Override
    public Map<String, Object> queryMpExwaitList(ProDataVO proDataVO) {
        List<LinkedHashMap<String, Object>> resultList = new ArrayList<>();
        String sorter=proDataVO.getSorter();
        if (StringUtils.isNotBlank(sorter)) {
            String sort = sorter.substring(0, sorter.lastIndexOf('_'));
            String sequence = "ascend".equals(sorter.substring(sorter.lastIndexOf('_') + 1)) ? "ASC" : "DESC";
            proDataVO.setSort(sort);
            proDataVO.setSequence(sequence);
        } else {
            proDataVO.setSort("create_time");
            proDataVO.setSequence("DESC");

        }
        Page<Object> page = PageHelper.startPage(proDataVO.getCurrentPage(), proDataVO.getPageSize());
        resultList = examineMapper.queryMpExwaitList(proDataVO);
        return PaginationBuilder.buildResult(resultList, page.getTotal(), proDataVO.getCurrentPage(), proDataVO.getPageSize());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void refuse(long id,long pdcBakId) {
        examineMapper.setStatusById(id,1);
        examineMapper.setPdcStatusById(id,pdcBakId,1);
        examineMapper.UpdatePQByBakId(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void adopt(long id,long pdcBakId) {
        examineMapper.setStatusById(id,2);
        examineMapper.setPdcStatusById(id,pdcBakId,2);
        examineMapper.UpdatePQByBakId(id);
        examineMapper.refreshPdc(id);
        examineMapper.refreshPQ(id);
        //TODO 替换主问卷
    }

    @Override
    public Map<String, Object> queryExconfirmList(ProDataVO proDataVO) {
        List<LinkedHashMap<String, Object>> resultList = new ArrayList<>();
        String sorter=proDataVO.getSorter();
        if (StringUtils.isNotBlank(sorter)) {
            String sort = sorter.substring(0, sorter.lastIndexOf('_'));
            String sequence = "ascend".equals(sorter.substring(sorter.lastIndexOf('_') + 1)) ? "ASC" : "DESC";
            proDataVO.setSort(sort);
            proDataVO.setSequence(sequence);
        } else {
            proDataVO.setSort("create_time");
            proDataVO.setSequence("DESC");

        }
        Page<Object> page = PageHelper.startPage(proDataVO.getCurrentPage(), proDataVO.getPageSize());
        resultList = examineMapper.queryExconfirmList(proDataVO);
        return PaginationBuilder.buildResult(resultList, page.getTotal(), proDataVO.getCurrentPage(), proDataVO.getPageSize());
    }

    @Override
    public Map<String, Object> findBakById(long id) {
        return examineMapper.findBakById(id);
    }

    @Override
    public Map GetBakPdcById(Long pdcId, long bakId) {
        return examineMapper.GetBakPdcById(pdcId,bakId);
    }
}
