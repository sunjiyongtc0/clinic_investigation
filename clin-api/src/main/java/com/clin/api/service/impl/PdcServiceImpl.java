package com.clin.api.service.impl;

import com.clin.api.entity.Pdc;
import com.clin.api.mapper.PdcMapper;
import com.clin.api.service.PdcService;
import com.clin.api.vo.PdcVO;
import com.clin.core.result.PaginationBuilder;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


/**
 * <pre>
 * Title :  TODO
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2022-03-03
 **/
@Service("PdcService")
public class PdcServiceImpl implements PdcService {

    @Autowired
    private PdcMapper pdcMapper;

    @Override
    public List<Map> getPdcItems() {
        return pdcMapper.getPdcItems();
    }





    @Override
    public int SavePdc(Pdc pdc) {
        return pdcMapper.SavePdc(pdc);
    }

    @Override
    public Map GetPdcById(Long id) {
        return pdcMapper.GetPdcById(id);
    }

    @Override
    public int ExistsPdc(Pdc pdc) {
        return pdcMapper.ExistsPdc(pdc);
    }

    @Override
    public Map<String, Object> queryPdcInfos(Integer currentPage, Integer pageSize, String sorter, String name, Integer sex, String telephone) {
        List<LinkedHashMap<String, Object>> resultList = new ArrayList<>();
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("name", name);
        paramMap.put("sex", sex);
        paramMap.put("telephone", telephone);
        Page<Object> page = PageHelper.startPage(currentPage, pageSize);
        resultList = pdcMapper.queryPdcInfos(paramMap);

        return PaginationBuilder.buildResult(resultList,  page.getTotal(), currentPage, pageSize);
    }


}
