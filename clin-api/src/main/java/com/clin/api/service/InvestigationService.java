package com.clin.api.service;

import com.alibaba.fastjson.JSONObject;
import com.clin.api.vo.ProDataVO;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Title :  TODO
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2022-03-04
 **/
public interface InvestigationService {

    Map<String, Object>  queryProData(ProDataVO proDataVO);


    Map<String, Object>  queryMpProData(ProDataVO proDataVO);

    void deleteInv(long id);

    Map<String, Object>  findById(long id);

    int saveData(Map<String,String> m);

    long InsertData(JSONObject groupSetting);

    List<Map> findByPdc(long id);




    List<LinkedHashMap<String, Object>> queryProDataExcel(Map<String, Object> paramMap);
}
