package com.clin.api.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.clin.api.mapper.PdcMapper;
import com.clin.api.mapper.ProblemTypeMapper;
import com.clin.api.mapper.QuestionTitleMapper;
import com.clin.api.service.QuestionService;
import com.clin.core.utils.SequenceGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
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
@Service("questioneService")
public class QuestionServiceImpl implements QuestionService {

    private static SequenceGenerator sequenceGenerator = new SequenceGenerator();

    @Autowired
    private QuestionTitleMapper questionTitleMapper;

    @Autowired
    private PdcMapper pdcMapper;


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void CreateQuestion(JSONObject jo) {
        long groupId=sequenceGenerator.nextId();
        Map group=new HashMap();
        group.put("id",groupId);
        group.put("questionName",jo.get("questionName"));
        group.put("pdcItems",jo.get("pdcItems"));
        questionTitleMapper.saveQuestionGroup(group);
        JSONArray ja=jo.getJSONArray("questionTitle");
        for(int i=0;i<ja.size();i++){
            JSONObject questionTitle=ja.getJSONObject(i);
            Map title=new HashMap();
            long titleId=sequenceGenerator.nextId();
            title.put("id",titleId);
            title.put("groupId",groupId);
            title.put("questionTitle",questionTitle.get("questionTitle"));
            title.put("questionType",questionTitle.get("questionType"));
            questionTitleMapper.saveQuestionTitle(title);
            JSONArray chooseArr=questionTitle.getJSONArray("chooseArr");
            for(int j=0;j<chooseArr.size();j++){
                chooseArr.getJSONObject(j).put("parentId",titleId);
            }
            questionTitleMapper.saveQuestionItems(chooseArr);
        }
    }

    @Override
    public List<LinkedHashMap> getQuestionList(String name, long code) {
        Map<String,Object> m=new HashMap<>();
        m.put("name",name);
        m.put("code",code);
        return questionTitleMapper.getQuestionList(m);
    }




    @Override
    public void setQuestionEnable(long id) {
        questionTitleMapper.updateGroupEnable(id);
    }

    @Override
    public List<Map> getQuestionList(long groupId) {
        if(groupId==0){
            return questionTitleMapper.getTitleList();
        }else{
            return questionTitleMapper.getTitleListById(groupId);
        }

    }

    @Override
    public Map<String, Object> getGroupById(long id) {
        return questionTitleMapper.getGroupById(id);
    }

    @Override
    public Map<String, Object> getGroupByEnable() {
        return questionTitleMapper.getGroupByEnable();
    }

    @Override
    public List<Map> getQuestionListByEnable() {
        return questionTitleMapper.getTitleList();
    }
}
