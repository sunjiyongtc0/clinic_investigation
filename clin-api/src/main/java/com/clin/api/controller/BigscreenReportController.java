package com.clin.api.controller;

import com.clin.api.service.ReportDataService;
import com.clin.core.result.ListResult;
import com.clin.core.result.ResultBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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
@RequestMapping("/pro/reprot")
public class BigscreenReportController {


    @Autowired
    private ReportDataService reportDataService;

    @GetMapping("/getStatisticsData")
    public ListResult<Object> getStatisticsData(){
        Map showMap=reportDataService.getStatisticsData();
        List<Map> ageMap=reportDataService.getAgeData();
        showMap.put("age",ageMap);
        return ResultBuilder.buildListSuccess(showMap);
    }


    @GetMapping("/getScoreData")
    public ListResult<Object> getScoreData(@RequestParam(name = "startTime", required = true) String startTime,@RequestParam(name = "endTime", required = true) String endTime){
        Map showMap=reportDataService.getScoreData(startTime +" 00:00:00",endTime+" 23:59:59");
        return ResultBuilder.buildListSuccess(showMap);
    }


    @GetMapping("/getCountData")
    public ListResult<Object> getCountData(@RequestParam(name = "startTime", required = true) String startTime,@RequestParam(name = "endTime", required = true) String endTime){
        Map showMap=reportDataService.getCountData(startTime +" 00:00:00",endTime+" 23:59:59");
        return ResultBuilder.buildListSuccess(showMap);
    }
}
