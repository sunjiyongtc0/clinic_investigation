package com.clin.api.service;

import com.alibaba.fastjson.JSONObject;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Title :  TODO
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2022-03-03
 **/
public interface QuestionService {


    void CreateQuestion(JSONObject jo);


    //获取问卷组信息
    List<LinkedHashMap> getQuestionList(String name, long code);


    //启用问卷
    void setQuestionEnable(long id);


    //获取问卷选项详情
    List<Map> getQuestionList(long groupId);

    Map<String,Object> getGroupById(long id);


    /**
     * 小程序接口用
     * */
    Map<String,Object>  getGroupByEnable();

    List<Map> getQuestionListByEnable();

}
