package com.clin.api.service.impl;

import com.clin.api.mapper.AnalyDataMapper;
import com.clin.api.service.AnalyDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Title :  TODO
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2022-03-17
 **/
@Service("AnalyDataService")
public class AnalyDataServiceImpl  implements AnalyDataService {


    @Autowired
    private AnalyDataMapper analyDataMapper;

    @Override
    public List<Map> getScoreData(String starTime, String endTime, long ageStart, long ageEnd, int sex) {

        return analyDataMapper.getScoreAnalyData(starTime +" 00:00:00",endTime+" 23:59:59",ageStart,ageEnd,sex);
    }

    @Override
    public List<Map> getAgeData(String starTime, String endTime, long scoreStart, long scoreEnd, int sex) {
        return analyDataMapper.getAgeAnalyData(starTime +" 00:00:00",endTime+" 23:59:59",scoreStart,scoreEnd,sex);
    }

    @Override
    public List<Map> getSexData(String starTime, String endTime, long ageStart, long ageEnd, long scoreStart, long scoreEnd) {
        return analyDataMapper.getSexAnalyData(starTime +" 00:00:00",endTime+" 23:59:59",ageStart,ageEnd,scoreStart,scoreEnd);
    }
}
