package com.clin.api.service;

import java.text.ParseException;
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
public interface ReportDataService {

    Map  getStatisticsData();

    List<Map> getAgeData( );

    Map getScoreData( String starTime, String endTime);


    Map getCountData( String starTime, String endTime) ;

}
