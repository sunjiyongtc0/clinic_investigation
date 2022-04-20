package com.clin.api.service;

import com.clin.api.entity.Pdc;
import com.clin.api.vo.PdcVO;

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
public interface PdcService {


    //获取用户信息指标
    List<Map> getPdcItems();


    int SavePdc(Pdc pdc);


    Map GetPdcById(Long id);

    int ExistsPdc(Pdc pdc);

    Map<String, Object> queryPdcInfos(Integer currentPage, Integer pageSize, String sorter,String name ,Integer sex,String telephone);


}
