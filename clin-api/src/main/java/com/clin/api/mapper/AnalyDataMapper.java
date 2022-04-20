package com.clin.api.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Title :  TODO
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2022-03-16
 **/
@Mapper
public interface AnalyDataMapper {


    //健康值条件查询
    @Select("  SELECT a.value as title,COUNT(*) as num FROM (" +
            " SELECT CASE WHEN score>=40 AND score<50 THEN '40-50' WHEN score>=50 AND score<60 THEN '50-60' WHEN score>=60 AND score<70 THEN '60-70' WHEN score>=70 AND score<80 THEN '70-80' WHEN score>=80 AND score<90 THEN '80-90' WHEN score>=90  THEN '90以上' ELSE  '小于40'  END AS VALUE FROM t_pdc_question q INNER JOIN t_pdc_info p ON  q.pdc_id=p.id WHERE delete_flag=0 " +
            "  and q.create_time>#{starTime} and q.create_time<=#{endTime} and q.age>=#{ageStart} and q.age<=#{ageEnd} and if(#{sex}=-1,1=1,sex=#{sex})) a GROUP BY a.value  order by a.value asc")
    List<Map> getScoreAnalyData(String starTime, String endTime,long ageStart , long ageEnd ,int sex);

    //年龄值条件查询
    @Select("  SELECT a.value as title,COUNT(*) as num  FROM (" +
            " SELECT CASE WHEN age>=40 AND age<50 THEN '40-50' WHEN age>=50 AND age<60 THEN '50-60' WHEN age>=60 AND age<70 THEN '60-70' WHEN age>=70 AND age<80 THEN '70-80' WHEN age>=80 AND age<90 THEN '80-90' WHEN age>=90  THEN '90以上' ELSE  '小于40'  END AS VALUE FROM t_pdc_question q INNER JOIN t_pdc_info p ON  q.pdc_id=p.id  WHERE delete_flag=0 " +
            "   and q.create_time>#{starTime} and q.create_time<=#{endTime}  and q.score>=#{scoreStart} and q.score<=#{scoreEnd} and if(#{sex}=-1,1=1,sex=#{sex})   ) a GROUP BY a.value order by a.value asc")
    List<Map> getAgeAnalyData( String starTime, String endTime,long scoreStart , long scoreEnd ,int sex);

    //性别条件查询
    @Select("  SELECT a.value as title,COUNT(*) as num  FROM (" +
            "  SELECT  IF(sex=0,'男','女') AS VALUE FROM t_pdc_question q INNER JOIN t_pdc_info p ON  q.pdc_id=p.id  WHERE delete_flag=0 " +
            "   and q.create_time>#{starTime} and q.create_time<=#{endTime}  and q.score>=#{scoreStart} and q.score<=#{scoreEnd} and q.age>=#{ageStart} and q.age<=#{ageEnd}   ) a GROUP BY a.value order by a.value asc")
    List<Map> getSexAnalyData( String starTime, String endTime,long ageStart , long ageEnd ,long scoreStart , long scoreEnd );



    //年龄值默认查询
    @Select("  SELECT a.value as title,COUNT(*) as num  FROM (" +
            " SELECT CASE WHEN age>=40 AND age<50 THEN '40-50' WHEN age>=50 AND age<60 THEN '50-60' WHEN age>=60 AND age<70 THEN '60-70' WHEN age>=70 AND age<80 THEN '70-80' WHEN age>=80 AND age<90 THEN '80-90' WHEN age>=90  THEN '90以上' ELSE  '小于40'  END AS VALUE FROM t_pdc_question q INNER JOIN t_pdc_info p ON  q.pdc_id=p.id  WHERE delete_flag=0 " +
            "     )a GROUP BY a.value order by a.value asc")
    List<Map> getAgeData();

    //性别默认查询
    @Select("  SELECT a.value as title,COUNT(*) as num  FROM (" +
            "  SELECT  IF(sex=0,'男','女') AS VALUE FROM t_pdc_question q INNER JOIN t_pdc_info p ON  q.pdc_id=p.id  WHERE delete_flag=0 " +
            "    )a GROUP BY a.value order by a.value asc")
    List<Map> getSexData();



}
