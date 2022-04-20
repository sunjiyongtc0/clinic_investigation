package com.clin.api.controller;

import com.clin.api.entity.Pdc;
import com.clin.api.service.PdcService;
import com.clin.api.vo.PdcVO;
import com.clin.core.result.ListResult;
import com.clin.core.result.ResultBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <pre>
 * Title :  被调查人信息
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2022-03-03
 **/
@RestController
@RequestMapping("/pro/pdc")
public class PdcController {

    @Autowired
    private PdcService pdcService;

    /**
     * 保存信息
     * */
    @GetMapping("/save")
    public ListResult<Object> savePdc(@RequestBody Pdc pdc){
        if(pdcService.ExistsPdc(pdc)>0){
            return new ListResult(100,"用户已存在！","fail");
        }else{
            pdcService.SavePdc(pdc);
            return ResultBuilder.buildListSuccess("保存成功");
        }
    }

    /**
     * 获取信息
     * */
    @GetMapping("/getPdcById")
    public ListResult<Object> getPdcById(@RequestParam(name = "id", required = true) long id){
       Map m=pdcService.GetPdcById(id);
        return ResultBuilder.buildListSuccess(m);
    }


    /**
     * 分页显示信息
     * */
    @GetMapping("/queryPdcInfos")
    public ListResult<Object> queryPdcInfos(PdcVO pdcVO){
        Map<String, Object> data =pdcService.queryPdcInfos(pdcVO.getCurrentPage(),pdcVO.getPageSize(),pdcVO.getSorter()
        ,pdcVO.getName(),pdcVO.getSex(),pdcVO.getTelephone());
        return ResultBuilder.buildListSuccess(data);
    }

}
