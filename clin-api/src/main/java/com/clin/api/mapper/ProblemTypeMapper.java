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
 * @since : 2022-03-03
 **/
@Mapper
public interface ProblemTypeMapper {

    @Select("SELECT * FROM  t_problem_type order by id")
    List<Map> getTypes();
}
