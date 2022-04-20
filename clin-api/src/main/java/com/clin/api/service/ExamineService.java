package com.clin.api.service;

import com.alibaba.fastjson.JSONObject;
import com.clin.api.vo.ProDataVO;

import java.util.Map;

/**
 * <pre>
 * Title :  TODO
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2022-03-11
 **/
public interface ExamineService {

    long CreateExamine(JSONObject jo);

    Map<String, Object> queryExwaitList(ProDataVO proDataVO);


    Map<String, Object> queryMpExwaitList(ProDataVO proDataVO);

    void refuse(long id,long pdcBakId);

    void adopt(long id,long pdcBakId);

    Map<String, Object> queryExconfirmList(ProDataVO proDataVO);


    Map<String ,Object> findBakById(long id);


    Map GetBakPdcById(Long pdcId,long bakId);
}
