package com.clin.api.mapper;

import com.alibaba.fastjson.JSONObject;
import com.clin.api.entity.Pdc;
import com.clin.api.vo.PdcVO;
import org.apache.ibatis.annotations.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Title :  TODO
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2022-03-03
 **/
@Mapper
public interface PdcMapper {

    /**
     * 获取pdc表字段对应关系
     * */
    @Select("SELECT COLUMN_NAME AS value ,COLUMN_COMMENT AS label  FROM information_schema.columns WHERE table_name='t_pdc_info'  ")
    List<Map> getPdcItems();


    @Select("select CAST(id AS CHAR) AS id,name,if(sex=0,'男','女') as sex ,DATE_FORMAT( birth ,'%Y-%m-%d') as birth,occupation,telephone,pressure,sugar,alcohol,smoke,diet,weight,height from t_pdc_info where id=#{id, jdbcType=BIGINT}")
    Map GetPdcById(@Param("id") Long id);

    @Insert("insert into  t_pdc_info(name,birth,sex,occupation,telephone,pressure,sugar,alcohol,smoke,diet,weight,height,creat_time)" +
            " value(#{name, jdbcType=VARCHAR},#{birth, jdbcType=VARCHAR},#{sex, jdbcType=BIGINT},#{occupation, jdbcType=VARCHAR},#{telephone, jdbcType=VARCHAR}," +
            "#{pressure, jdbcType=VARCHAR},#{sugar, jdbcType=VARCHAR},#{alcohol, jdbcType=VARCHAR},#{smoke, jdbcType=VARCHAR},#{diet, jdbcType=VARCHAR},#{weight, jdbcType=BIGINT},#{height, jdbcType=BIGINT},NOW())")
    int SavePdc(Pdc pdc);



    @Select("select count(*) from t_pdc_info where sex=#{sex, jdbcType=BIGINT} and birth=#{birth, jdbcType=VARCHAR} and name=#{name, jdbcType=VARCHAR}")
    int ExistsPdc(Pdc pdc);

    @Select("select * from t_pdc_info where sex=#{sex, jdbcType=BIGINT} and birth=#{birth, jdbcType=VARCHAR} and name=#{name, jdbcType=VARCHAR} limit 1")
    Map getPdcByItems(JSONObject pdc);

    List<LinkedHashMap<String, Object>>  queryPdcInfos(Map<String, Object> paramMap);


    long InsertGetId(JSONObject info);
}
