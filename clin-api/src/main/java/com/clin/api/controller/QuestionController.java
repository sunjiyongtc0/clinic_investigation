package com.clin.api.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.clin.api.service.QuestionService;
import com.clin.api.vo.QuestionSetting;
import com.clin.core.result.ActionResult;
import com.clin.core.result.ListResult;
import com.clin.core.result.ResultBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Title :  调查问卷相关
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2022-03-03
 **/
@RestController
@RequestMapping("/pro/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    /**
     * 获取问卷组
     * */
    @GetMapping("/getQuestionList")
    public ListResult<Object> getQuestionList(@RequestParam(name = "name", required = true) String name,@RequestParam(name = "code", required = true) String code){
        long id= StrUtil.isNotBlank(code)?Long.valueOf(code):0l;
        List<LinkedHashMap> listInfo =questionService.getQuestionList( name,id );
        return ResultBuilder.buildListSuccess(listInfo);
    }

    /**
     * 保存一个调查问卷
     * */
    @PostMapping("/saveQuestion")
    public ActionResult saveQuestion(@RequestBody JSONObject questionSetting){
        questionService.CreateQuestion(questionSetting);
        return ResultBuilder.buildActionSuccess();
    }

    /**
     * 启用问卷
     * */
    @GetMapping("/setQuestionEnable")
    public ActionResult setQuestionEnable(@RequestParam(name = "id", required = true) long id){
        questionService.setQuestionEnable(id);
        return ResultBuilder.buildActionSuccess();
    }

    /**
     * 显示调查问卷信息
     * */
    @GetMapping("/findTitleById")
    public ListResult<Object> findTitleById(@RequestParam(name = "id", required = true) long id){
        List<Map> listTitles =questionService.getQuestionList(id);
        Map<String,Object> group=questionService.getGroupById(id);
        Map<String ,Object> showMap=new HashMap<>();
        showMap.put("name",group.get("question_name"));
        showMap.put("checkedInfo",group.get("pdc_items"));
        showMap.put("questionArr",listTitles);
        return ResultBuilder.buildListSuccess(showMap);
    }


//    /**
//     * 显示调查用户信息字段
//     * */
//    @GetMapping("/pdcItems")
//    public ListResult<Object> pdcItems(){
//        List<Map> listInfo =questionService.getPdcItems();
//        return ResultBuilder.buildListSuccess(listInfo);
//    }


//    /**
//     * 显示调查问卷信息
//     * */
//    @GetMapping("/listTitle")
//    public ListResult<Object> listTitle(){
//        List<Map> listTitles =questionService.getQuestionList(0);
//        return ResultBuilder.buildListSuccess(listTitles);
//    }



}
