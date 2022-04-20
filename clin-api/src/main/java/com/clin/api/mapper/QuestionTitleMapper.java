package com.clin.api.mapper;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
 * @since : 2022-03-07
 **/
@Mapper
public interface QuestionTitleMapper {


   List<LinkedHashMap> getQuestionList(Map<String, Object> m);


   List<Map> getTitleList();

   List<Map> getTitleListById(long groupId );


   void saveQuestionGroup(Map group);
   void saveQuestionTitle(Map title);
   void saveQuestionItems(JSONArray chooseArr);

   @Update("UPDATE t_question_group SET is_enable=IF(id=#{id},1,0)")
   void updateGroupEnable(long id);

   @Select("select CAST(id AS CHAR) AS id,question_name,is_enable,pdc_items from t_question_group where id=#{id}")
   Map<String ,Object> getGroupById(long id);


   @Select("select CAST(id AS CHAR) AS id,question_name,is_enable,pdc_items from t_question_group where is_enable=1")
   Map<String, Object> getGroupByEnable();

}
