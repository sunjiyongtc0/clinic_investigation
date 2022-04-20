package com.clin.mp.controller;

import com.alibaba.fastjson.JSONArray;
import com.clin.api.service.ExamineService;
import com.clin.api.service.PdcService;
import com.clin.api.service.QuestionService;
import com.clin.api.vo.MpProDataVO;
import com.clin.api.vo.ProDataVO;
import com.clin.auth.utils.CurrentUserUtils;
import com.clin.core.result.ActionResult;
import com.clin.core.result.ListResult;
import com.clin.core.result.ResultBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * Title :  TODO
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2022-03-22
 **/
@RestController
@RequestMapping("/mp/examine")
public class MpExamineController extends BaseMpController{

    @Autowired
    private PdcService pdcService;

    @Autowired
    private ExamineService examineService;

    @Autowired
    private QuestionService questionService;

    /**
     * 分页显示待审核信息
     * */
    @GetMapping("/queryExwaitList")
    public ListResult<Object> queryExwaitList(MpProDataVO mpProDataVO){
        VerificationAuth();
        ProDataVO proDataVO=new ProDataVO();
        proDataVO.setName(mpProDataVO.getName());
        proDataVO.setStatus(mpProDataVO.getStatus());
        proDataVO.setCurrentPage(mpProDataVO.getCurrentPage());
        proDataVO.setPageSize(mpProDataVO.getPageSize());
        proDataVO.setRoleCode(mpProDataVO.getRoleCode());
        proDataVO.setUserId(Long.parseLong(mpProDataVO.getUserId()));
        Map<String, Object> data =examineService.queryMpExwaitList(proDataVO);
        return ResultBuilder.buildListSuccess(data);
    }


    /**
     *获取修改流程的问卷详情
     * */
    @GetMapping("/findBakDetailById")
    public ListResult<Object> findBakDetailById(@RequestParam(name = "id", required = true) long id){
        VerificationAuth();
        Map<String ,Object> showMap=new HashMap<>();
        Map<String ,Object> groupBak=examineService.findBakById(id);
        showMap.put("answer", JSONArray.parseArray(groupBak.get("config_json")+""));
        long groupId=Long.parseLong(groupBak.get("group_id")+"");
        showMap.put("groupInfo",questionService.getGroupById(groupId));
        showMap.put("questionList",questionService.getQuestionList(groupId));
        long pdcId=Long.parseLong(groupBak.get("pdc_id")+"");
        showMap.put("pdcItems",pdcService.getPdcItems());
        showMap.put("pdcInfo",examineService.GetBakPdcById(pdcId,id));
        showMap.put("pdId",groupBak.get("pd_id")+"");
        showMap.put("userId",groupBak.get("user_id")+"");
        return ResultBuilder.buildListSuccess(showMap);
    }


    //拒绝修改操作
    @GetMapping("/refuse")
    public ActionResult refuse(@RequestParam(name = "id", required = true) long id, @RequestParam(name = "pdcBakId", required = true) long pdcBakId){
        VerificationAuth();
        examineService.refuse(id,pdcBakId);
        return ResultBuilder.buildActionSuccess();
    }

    //通过审核操作
    @GetMapping("/adopt")
    public ActionResult Adopt(@RequestParam(name = "id", required = true) long id,@RequestParam(name = "pdcBakId", required = true) long pdcBakId){
        VerificationAuth();
        examineService.adopt(id,pdcBakId);
        return ResultBuilder.buildActionSuccess();
    }
}
