package com.clin.mp.service.impl;

import com.clin.api.mapper.AnalyDataMapper;
import com.clin.mp.service.MpAnalyDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Title :  TODO
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2022-03-22
 **/
@Service("MpAnalyDataService")
public class MpAnalyDataServiceImpl implements MpAnalyDataService {

    @Autowired
    private AnalyDataMapper analyDataMapper;

    @Override
    public List<Map> getAgeData() {
        return analyDataMapper.getAgeData();
    }

    @Override
    public List<Map> getSexData() {
        return analyDataMapper.getSexData();
    }
}
