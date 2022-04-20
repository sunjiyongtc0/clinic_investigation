package com.clin.api.mapper;

import com.alibaba.fastjson.JSONObject;
import com.clin.api.vo.ProDataVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Title :  TODO
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2022-03-11
 **/
@Mapper
public interface ExamineMapper {

//    @Insert(" insert into t_pdc_question_bak(pd_id,pdc_id,group_id,age,score,config_json,user_id,status,create_time) values(" +
//            "  #{pdId},#{pdcId},#{groupId},#{age},#{score},#{config},#{userId},0,now())")
    long InsertExwait(JSONObject jo);

    @Insert(" insert into t_pdc_info_bak(pdc_id,name,sex,birth,occupation,telephone,pressure,sugar,alcohol,smoke,diet,weight,height,status,create_time,bak_id) values(" +
            "  #{id},#{name},IF(#{sex}='男',0,1),#{birth},#{occupation},#{telephone},#{pressure},#{sugar},#{alcohol},#{smoke},#{diet},#{weight},#{height},0,now(),#{bakId})")
    void InsertPdc(JSONObject jo);

    /**
     * 根据用户id修改相关的所有问卷状态
     * */
    @Update("update t_pdc_question set status=#{status} where pdc_id=#{id}")
    void UpdatePQByPdcId(long id,long status);

    /**
     * 根据id修改问卷状态
     * */
    @Update("update t_pdc_question set status=#{status} where id=#{id}")
    void UpdatePQById(long id,long status);


    /**
     * 根据流程id修改问卷状态
     * */
    @Update("update t_pdc_question set status=0 where id in (select pd_id from t_pdc_question_bak where id=#{id})")
    void UpdatePQByBakId(long id);

    /**
     * 根据id修改流程问卷表
     * */
    @Update("update t_pdc_question_bak set status=#{status} where id=#{id}")
    void setStatusById(long id,long status);


    /**
     * 根据id修改流程问卷表
     * */
    @Update("update t_pdc_info_bak set status=#{status} where bak_id=#{id} and id=#{pdcBakId}")
    void setPdcStatusById(long id,long pdcBakId,long status);



    //分页待审核显示数据
    List<LinkedHashMap<String, Object>> queryExwaitList(ProDataVO proDataVO );


    List<LinkedHashMap<String, Object>> queryMpExwaitList(ProDataVO proDataVO );

    //分页待审核显示数据
    List<LinkedHashMap<String, Object>> queryExconfirmList(ProDataVO proDataVO );


    //更新患者表
    @Update(" UPDATE t_pdc_info p ,(SELECT * FROM t_pdc_info_bak WHERE bak_id=#{id} ) pb " +
            " SET p.name=pb.name,p.sex=pb.sex,p.birth=pb.birth,p.occupation=pb.occupation,p.telephone=pb.telephone,p.pressure=pb.pressure,p.sugar=pb.sugar,p.alcohol=pb.alcohol,p.smoke=pb.smoke,p.diet=pb.diet,p.weight=pb.weight,p.height=pb.height " +
            " WHERE p.id=pb.pdc_id")
    void refreshPdc(long id);

    //更新问卷结果表
    @Update(" UPDATE t_pdc_question q,(SELECT * FROM t_pdc_question_bak WHERE id=#{id}) qb set q.age=qb.age ,q.score=qb.score ,q.config_json=qb.config_json\n" +
            "  WHERE q.id=qb.pd_id")
    void refreshPQ(long id);


    /**
     *详情信息----------------------------------
     */
    @Select("select * from t_pdc_question_bak where id=#{id}")
    Map<String, Object> findBakById(long id);

    @Select("select CAST(pdc_id AS CHAR) AS id,name,if(sex=0,'男','女') as sex ,DATE_FORMAT( birth ,'%Y-%m-%d') as birth,occupation,telephone,pressure,sugar,alcohol,smoke,diet,weight,height from t_pdc_info_bak where pdc_id=#{pdcId} and bak_id=#{bakId}")
    Map<String, Object> GetBakPdcById(long pdcId,long bakId);
}
