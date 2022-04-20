package com.clin.api.service.impl;

import cn.hutool.core.date.DateUtil;
import com.clin.api.mapper.ReportDataMapper;
import com.clin.api.service.ReportDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
@Service("ReportDataService")
public class ReportDataServiceImpl implements ReportDataService {
    private  static  SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private ReportDataMapper reportDataMapper;


    @Override
    public Map getStatisticsData() {
        Map m=new HashMap();
        try {
            Map day=reportDataMapper.getDaySum();
            Map user=reportDataMapper.getUserQuest();
            m.put("countNum",day.get("allsum"));
            m.put("todayNum",day.get("daysum"));
            long alluser=Long.parseLong(user.get("alluser")+"");
            long allquest=Long.parseLong(user.get("allquest")+"");
            m.put("userNum",alluser);
            m.put("avgNum",allquest/alluser);
        }catch (Exception e){
//            System.out.println(e.getMessage());
        }

        return m;
    }

    @Override
    public List<Map> getAgeData() {
        return reportDataMapper.getAgeData();
    }
    @Override
    public Map getScoreData(String starTime, String endTime) {
        Map m=new HashMap();
        m=reportDataMapper.getHealthyData(starTime,endTime);
        m.put("score",reportDataMapper.getScoreData(starTime,  endTime));
        return m;
    }

    @Override
    public Map getCountData(String starTime, String endTime) {
        Map Arr=new HashMap();
        Arr.put("rank",reportDataMapper.getUserRankData(starTime,  endTime));
        Map lmArr=new HashMap();
        try {
        long star=sdf.parse(starTime).getTime();
        long end=sdf.parse(endTime).getTime();
        long timeTep=1000*60*60*24l;//计量单位天
        List<Map> lm=new ArrayList<Map>();
        List<String> xData=new ArrayList<>();
        List<Long> yData=new ArrayList<>();
        long dif=(end-star)/timeTep;
        //小于7天用W;小于28 d;小于334 m ;剩余 y
        if(dif<=7) {
            lm = reportDataMapper.getDistributionData("%w", starTime, endTime);
            String[] week={"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
            for(int i=0;i<7;i++){
                xData.add(week[i]);
                long value=0l;
                for (Map m : lm) {
                    if((m.get("ttype")+ "").equals(i+"")){
                        value=Long.valueOf(m.get("con") + "");
                    }
                }
                yData.add(value);
            }
        }else if(dif>7  &&dif<=28){
            int s=Integer.parseInt(starTime.split(" ")[0].split("-")[2]);
            int e=Integer.parseInt(endTime.split(" ")[0].split("-")[2]);

            lm = reportDataMapper.getDistributionData("%d", starTime, endTime);
            if(s<e){
                for(int i=s;i<=e;i++) {
                    String d = i < 10 ? "0" + i : i + "";
                    xData.add(d);
                    long value = 0l;
                    for (Map m : lm) {
                        if ((m.get("ttype") + "").equals(d)) {
                            value = Long.valueOf(m.get("con") + "");
                        }
                    }
                    yData.add(value);
                }
            }else{
                for(int i=s;i<=31;i++) {
                    String d = i < 10 ? "0" + i : i + "";
                    xData.add(d);
                    long value = 0l;
                    for (Map m : lm) {
                        if ((m.get("ttype") + "").equals(d)) {
                            value = Long.valueOf(m.get("con") + "");
                        }
                    }
                    yData.add(value);
                }
                for(int i=1;i<=e;i++) {
                    String d = i < 10 ? "0" + i : i + "";
                    xData.add(d);
                    long value = 0l;
                    for (Map m : lm) {
                        if ((m.get("ttype") + "").equals(d)) {
                            value = Long.valueOf(m.get("con") + "");
                        }
                    }
                    yData.add(value);
                }
            }
        }else if(dif>28&&dif<=334 ){
            int s=Integer.parseInt(starTime.split(" ")[0].split("-")[1]);
            int e=Integer.parseInt(endTime.split(" ")[0].split("-")[1]);
            lm=reportDataMapper.getDistributionData("%m",starTime,endTime);
            if(s<e){
                for(int i=s;i<=e;i++) {
                    String month=i<10?"0"+i:i+"";
                    xData.add(month+"月");
                    long value = 0l;
                    for (Map m : lm) {
                        if ((m.get("ttype") + "").equals(month)) {
                            value = Long.valueOf(m.get("con") + "");
                        }
                    }
                    yData.add(value);
                }
            }else{
                for(int i=s+1;i<=12;i++) {
                    String month=i<10?"0"+i:i+"";
                    xData.add(month+"月");
                    long value = 0l;
                    for (Map m : lm) {
                        if ((m.get("ttype") + "").equals(month)) {
                            value = Long.valueOf(m.get("con") + "");
                        }
                    }
                    yData.add(value);
                }
                for(int i=1;i<=e;i++) {
                String month=i<10?"0"+i:i+"";
                xData.add(month+"月");
                    long value = 0l;
                    for (Map m : lm) {
                        if ((m.get("ttype") + "").equals(month)) {
                            value = Long.valueOf(m.get("con") + "");
                        }
                    }
                    yData.add(value);
                }
            }
        }else if(dif>334){
            lm=reportDataMapper.getDistributionData("%y",starTime,endTime);
            for(Map m:lm){
                xData.add("20"+m.get("ttype")+"年");
                yData.add(Long.valueOf(m.get("con")+""));
            }
        }
            lmArr.put("xData",xData.toArray());
            lmArr.put("yData",yData.toArray());

        }catch (Exception e){
        }
        Arr.put("distribution",lmArr);
        return Arr;
    }

}
