package com.clin.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.clin.api.service.AnalyDataService;
import com.clin.api.service.ReportDataService;
import com.clin.core.result.ListResult;
import com.clin.core.result.ResultBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Title :  TODO
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2022-03-15
 **/
@RestController
@RequestMapping("/pro/analy")
public class BigscreenAnalyController {


    @Autowired
    private AnalyDataService analyDataService;

    @PostMapping("/getScoreData")
    public ListResult<Object> getScoreData(@RequestBody JSONObject param){
        ArrayList<String> time= (ArrayList<String>) param.get("time");
        List<Map> show=analyDataService.getScoreData(time.get(0),time.get(1),param.getLong("age1"),param.getLong("age2"),param.getIntValue("sex"));
        return ResultBuilder.buildListSuccess(show);
    }

    @PostMapping("/getAgeData")
    public ListResult<Object> getAgeData(@RequestBody JSONObject param){
        ArrayList<String> time= (ArrayList<String>) param.get("time");
        List<Map> show=analyDataService.getAgeData(time.get(0),time.get(1),param.getLong("score1"),param.getLong("score2"),param.getIntValue("sex"));
        return ResultBuilder.buildListSuccess(show);
    }
    @PostMapping("/getSexData")
    public ListResult<Object> getSexData(@RequestBody JSONObject param){
        ArrayList<String> time= (ArrayList<String>) param.get("time");
        List<Map> show=analyDataService.getSexData(time.get(0),time.get(1),param.getLong("age1"),param.getLong("age2"),param.getLong("score1"),param.getLong("score2"));
        return ResultBuilder.buildListSuccess(show);
    }
}
