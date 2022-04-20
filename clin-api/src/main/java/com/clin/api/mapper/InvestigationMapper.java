package com.clin.api.mapper;

import com.alibaba.fastjson.JSONObject;
import com.clin.api.vo.ProDataVO;
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
 * @since : 2022-03-04
 **/
@Mapper
public interface InvestigationMapper {

    //分页显示数据
    List<LinkedHashMap<String, Object>>  queryProData(ProDataVO proDataVO );

    List<LinkedHashMap<String, Object>>  queryMpProData(ProDataVO proDataVO );

    //逻辑删除填报问卷表
//    @Delete(" delete from t_pdc_question where id=#{id}")
    @Update("update t_pdc_question set delete_flag=1 where id=#{id}")
    void deleteInv(long id);

    //删除填报流程表
    @Delete(" delete from t_pdc_question_bak where id=#{id}")
    void deleteInvBak(long id);

    //获取填报信息
    @Select("select * from t_pdc_question where id=#{id}")
    Map  findById(long id);


    int saveData(Map m);

    @Select("select * from t_pdc_question where id=#{id}")
    List<Map>  findByPdc(long id);

    List<LinkedHashMap<String, Object>> queryProDataExcel(Map<String, Object> paramMap);


    void changeStatusByPdc(long status,long pdcId);

     void changeStatusById(long status,long id);

     @Insert("insert into t_pdc_question(pdc_id,group_id,score,age,config_json,user_id,status,create_time,delete_flag) value(#{pdcId},#{groupId},#{score},#{age},#{config},#{userId},0,now(),0)")
     void InsertData(JSONObject question);

}
