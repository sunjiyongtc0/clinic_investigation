package com.clin.mp.controller;

import com.alibaba.fastjson.JSONObject;
import com.clin.api.service.ReportDataService;
import com.clin.core.result.ListResult;
import com.clin.core.result.ResultBuilder;
import com.clin.mp.service.MpAnalyDataService;
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
 * @since : 2022-03-22
 **/
@RestController
@RequestMapping("/mp/analy")
public class MpAnalyController extends BaseMpController{

    @Autowired
    private ReportDataService reportDataService;

    @Autowired
    private MpAnalyDataService mpAnalyDataService;


    @GetMapping("/getStatisticsData")
    public ListResult<Object> getStatisticsData(){
        VerificationAuth();
        Map showMap=reportDataService.getStatisticsData();
        if(showMap.isEmpty()){
            return ResultBuilder.buildListSuccess("");
        }else{
            List<Map> ageMap=reportDataService.getAgeData();
            if(ageMap.size()!=0){
                showMap.put("age",ageMap);
            }
            return ResultBuilder.buildListSuccess(showMap);
        }


    }

    @PostMapping("/getAgeData")
    public ListResult<Object> getAgeData(){
        VerificationAuth();
        List<Map> show=mpAnalyDataService.getAgeData();
        return ResultBuilder.buildListSuccess(show);
    }
    @PostMapping("/getSexData")
    public ListResult<Object> getSexData(){
        VerificationAuth();
        List<Map> show=mpAnalyDataService.getSexData();
        return ResultBuilder.buildListSuccess(show);
    }
}
