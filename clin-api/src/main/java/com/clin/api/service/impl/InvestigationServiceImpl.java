package com.clin.api.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.clin.api.entity.Pdc;
import com.clin.api.mapper.InvestigationMapper;
import com.clin.api.mapper.PdcMapper;
import com.clin.api.service.InvestigationService;
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

import java.util.*;

/**
 * <pre>
 * Title :  TODO
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2022-03-04
 **/
@Service("InvestigationService")
public class InvestigationServiceImpl implements InvestigationService {

    @Autowired
    private InvestigationMapper investigationMapper;
    @Autowired
    private PdcMapper pdcMapper;

    @Override
    public Map<String, Object> queryProData(ProDataVO proDataVO) {
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
        resultList = investigationMapper.queryProData(proDataVO);
        return PaginationBuilder.buildResult(resultList, page.getTotal(), proDataVO.getCurrentPage(), proDataVO.getPageSize());
    }

    @Override
    public Map<String, Object> queryMpProData(ProDataVO proDataVO) {
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
        resultList = investigationMapper.queryMpProData(proDataVO);
        return PaginationBuilder.buildResult(resultList, page.getTotal(), proDataVO.getCurrentPage(), proDataVO.getPageSize());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteInv(long id) {
        investigationMapper.deleteInv(id);
        investigationMapper.deleteInvBak(id);
    }

    @Override
    public Map<String, Object> findById(long id) {
        return investigationMapper.findById(id);
    }



    @Override
    public int saveData(Map<String, String> m) {
        Map param = new HashMap();
        param.put("pdc_id", m.get("pdc_id"));
        param.put("age", m.get("age"));
        List<Map> countItem = new ArrayList<>();
        for (Map.Entry<String, String> e : m.entrySet()) {
            if (e.getKey().contains("item")) {
                Map Item = new HashMap();
                Item.put("idx", e.getKey().split("_")[1]);
                Item.put("value", e.getValue());
                countItem.add(Item);
            }
        }
        param.put("countItem", countItem);
        return investigationMapper.saveData(param);
    }

    @Override
    public long InsertData(JSONObject groupSetting) {
        long pdcId=0l;
        String birth="";
        JSONObject pdc =groupSetting.getJSONObject("pdc");
        Pdc pdcEntity=new Pdc();
        pdcEntity.setName(pdc.getString("name"));
        pdcEntity.setSex(pdc.getInteger("sex"));
        pdcEntity.setBirth(pdc.getString("birth"));
        int isPdc= pdcMapper.ExistsPdc(pdcEntity);
        if(isPdc>0){
            Map Pdc=pdcMapper.getPdcByItems(pdc);
            pdcId=(long) Pdc.get("id");
            birth=Pdc.get("birth")+"";
        }else{
            pdcMapper.InsertGetId(pdc);
            pdcId=pdc.getLong("id");
            birth=pdc.getString("birth");

        }
        JSONObject question=new JSONObject();
        question.put("pdcId",pdcId);
        question.put("age", AgeUtils.getAgeFromBirthTime(birth));
        question.put("config",groupSetting.getJSONArray("config").toJSONString());
        question.put("score",groupSetting.getLong("score"));
        question.put("groupId",groupSetting.getLong("groupId"));
        question.put("userId",groupSetting.getLong("userId"));
        investigationMapper.InsertData(question);
        return 0;
    }

    @Override
    public List<Map> findByPdc(long pdcId) {
        return investigationMapper.findByPdc(pdcId);
    }

    @Override
    public List<LinkedHashMap<String, Object>> queryProDataExcel(Map<String, Object> paramMap) {
        return investigationMapper.queryProDataExcel(paramMap);
    }


//    @Override
//    public List<LinkedHashMap<String, Object>> queryProDataExcel(Map<String, Object> paramMap) {
//        ProDataVO proDataVO=new ProDataVO();
//
//        if(!paramMap.containsKey("agestart")){
//            proDataVO.setAgestart(null);
//        }
//        if(!paramMap.containsKey("ageend")){
//            proDataVO.setAgeend(null);
//        }
//        if(!paramMap.containsKey("name")){
//            proDataVO.setName(null);
//        }
//        if(!paramMap.containsKey("telephone")){
//            proDataVO.setTelephone(null);
//        }
//        if(!paramMap.containsKey("status")){
//            proDataVO.setStatus(null);
//        }
//        List<LinkedHashMap<String, Object>> dataList = investigationMapper.queryProData(proDataVO);
//        return dataList;
//    }
}
