package com.clin.api.service;

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
public interface AnalyDataService {


    List<Map> getScoreData(String starTime, String endTime, long ageStart , long ageEnd , int sex);


    List<Map> getAgeData( String starTime, String endTime,long scoreStart , long scoreEnd ,int sex);


    List<Map> getSexData( String starTime, String endTime,long ageStart , long ageEnd ,long scoreStart , long scoreEnd );
}
