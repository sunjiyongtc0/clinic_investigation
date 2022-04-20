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
 * @since : 2022-03-15
 **/
@Mapper
public interface ReportDataMapper {

    @Select("SELECT COUNT(id) AS allsum, SUM(IF(TO_DAYS(create_time) = TO_DAYS(NOW()),1,0)) AS daysum FROM  t_pdc_question WHERE  delete_flag=0")
    Map getDaySum();//获取全部问卷数和今日问卷数

    @Select(" SELECT COUNT( DISTINCT u.id) AS alluser,SUM(IF( ISNULL(q.group_id),0,1) ) AS allquest FROM   " +
        " sys_user u LEFT JOIN t_pdc_question q ON u.id=q.user_id  INNER JOIN  sys_role_user ru ON u.id=ru.user_id INNER JOIN  sys_role r ON r.id=ru.role_id " +
        " WHERE  u.status=1 and role_code= 'ROLE_VOLUNTEER'")
    Map getUserQuest();//获取用户问卷数

    @Select("SELECT ${type}  FROM t_pdc_question WHERE delete_flag=0 and create_time>#{starTime} AND create_time< #{endTime}")
    List<Long> getPieListData(String type, String starTime, String endTime);

    @Select("  SELECT a.value as title,COUNT(*) as num  FROM (" +
            " SELECT CASE WHEN age>=40 AND age<50 THEN '40-50' WHEN age>=50 AND age<60 THEN '50-60' WHEN age>=60 AND age<70 THEN '60-70' WHEN age>=70 AND age<80 THEN '70-80' WHEN age>=80 AND age<90 THEN '80-90' WHEN age>=90  THEN '90以上' ELSE  '小于40'  END AS VALUE FROM t_pdc_question WHERE delete_flag=0 " +
            "   )a GROUP BY a.value order by a.value asc")
    List<Map> getAgeData( );

    @Select("  SELECT a.value as title,COUNT(*) as num FROM (" +
            " SELECT CASE WHEN score>=40 AND score<50 THEN '40-50' WHEN score>=50 AND score<60 THEN '50-60' WHEN score>=60 AND score<70 THEN '60-70' WHEN score>=70 AND score<80 THEN '70-80' WHEN score>=80 AND score<90 THEN '80-90' WHEN score>=90  THEN '90以上' ELSE  '小于40'  END AS VALUE FROM t_pdc_question WHERE delete_flag=0 " +
            "  and create_time>#{starTime} and create_time<=#{endTime}  ) a GROUP BY a.value  order by a.value asc")
    List<Map> getScoreData( String starTime, String endTime);

    @Select("   SELECT COUNT(*) as con,SUM(smoke) AS smoke,SUM(alcohol)  AS alcohol   FROM (      SELECT q.id,IF((ISNULL(p.smoke) OR p.smoke='无' OR p.smoke='没有'),0,1) AS smoke, IF((ISNULL(p.alcohol) OR p.alcohol='无' OR p.alcohol='没有'),0,1)  AS alcohol FROM t_pdc_question q LEFT JOIN t_pdc_info p ON p.id=q.pdc_id  " +
            "              WHERE q.create_time>#{starTime} AND q.create_time<=#{endTime}  AND delete_flag=0) a")
    Map getHealthyData(String starTime, String endTime);

    @Select("SELECT nickname,COUNT(id) AS con FROM ( " +
        " SELECT  u.nickname,q.id FROM t_pdc_question q INNER JOIN sys_user u ON q.user_id=u.id " +
        " INNER JOIN (SELECT  user_id AS uid ,r.role_code FROM sys_role_user  ru INNER JOIN  sys_role r ON ru.role_id= r.id) ur " +
        " ON ur.uid=u.id WHERE  q.create_time>#{starTime} AND q.create_time<=#{endTime}  AND q.delete_flag=0 AND ur.role_code='ROLE_VOLUNTEER' )a GROUP BY  nickname ORDER BY con DESC")
    List<Map> getUserRankData(String starTime, String endTime);

    @Select("SELECT ttype  ,COUNT(id) AS con FROM (SELECT  DATE_FORMAT( create_time, #{type} ) AS ttype,id  FROM t_pdc_question q where q.create_time>#{starTime} AND q.create_time<=#{endTime}  AND q.delete_flag=0)a GROUP BY ttype")
    List<Map> getDistributionData(String type,String starTime, String endTime);
}
