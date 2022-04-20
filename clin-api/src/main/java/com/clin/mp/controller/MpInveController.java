package com.clin.mp.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.clin.api.service.ExamineService;
import com.clin.api.service.InvestigationService;
import com.clin.api.service.PdcService;
import com.clin.api.service.QuestionService;
import com.clin.api.vo.MpProDataVO;
import com.clin.api.vo.ProDataVO;
import com.clin.auth.utils.CurrentUserUtils;
import com.clin.core.result.ActionResult;
import com.clin.core.result.ListResult;
import com.clin.core.result.ResultBuilder;
import com.clin.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * Title :  TODO
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2022-03-20
 **/
@RestController
@RequestMapping("/mp/inv")
public class MpInveController  extends BaseMpController{


    @Autowired
    private InvestigationService investigationService;

    @Autowired
    private PdcService pdcService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ExamineService examineService;

    @Autowired
    private SysUserService sysUserService;
    /**
     *获取启用问卷信息
     * */
    @GetMapping("/findDetailByEnable")
    public ListResult<Object> findDetailByEnable(){
        VerificationAuth();
        Map<String ,Object> showMap=new HashMap<>();
        showMap.put("groupInfo",questionService.getGroupByEnable());
        showMap.put("questionList",questionService.getQuestionListByEnable());
        showMap.put("pdcItems",pdcService.getPdcItems());
        return ResultBuilder.buildListSuccess(showMap);
    }

    /**
     * 分页显示信息
     * */
    @GetMapping("/queryPdcInfos")
    public ListResult<Object> queryProData(MpProDataVO mpProDataVO){
        VerificationAuth();
        ProDataVO proDataVO=new ProDataVO();
        proDataVO.setName(mpProDataVO.getName());
        proDataVO.setAgestart(mpProDataVO.getAgestart());
        proDataVO.setAgeend(mpProDataVO.getAgeend());
        proDataVO.setCurrentPage(mpProDataVO.getCurrentPage());
        proDataVO.setPageSize(mpProDataVO.getPageSize());
        proDataVO.setRoleCode(mpProDataVO.getRoleCode());
        proDataVO.setUserId(Long.parseLong(mpProDataVO.getUserId()));
        Map<String, Object> data =investigationService.queryMpProData(proDataVO);
        return ResultBuilder.buildListSuccess(data);
    }



    @PostMapping("/InsertData")
    public ActionResult InsertData(@RequestBody JSONObject groupSetting){
        VerificationAuth();
        investigationService.InsertData(groupSetting);
        return ResultBuilder.buildActionSuccess();
    }


    /**
     *获取问卷详情
     * */
    @GetMapping("/findDetailById")
    public ListResult<Object> findDetailById(@RequestParam(name = "id", required = true) long id){
        VerificationAuth();
        Map<String ,Object> showMap=new HashMap<>();
        Map<String ,Object> group=investigationService.findById(id);
        showMap.put("answer", JSONArray.parseArray(group.get("config_json")+""));
        long groupId=Long.parseLong(group.get("group_id")+"");
        showMap.put("groupInfo",questionService.getGroupById(groupId));
        showMap.put("questionList",questionService.getQuestionList(groupId));
        long pdcId=Long.parseLong(group.get("pdc_id")+"");
        showMap.put("pdcItems",pdcService.getPdcItems());
        showMap.put("pdcInfo",pdcService.GetPdcById(pdcId));
        showMap.put("pdId",group.get("id")+"");
        showMap.put("userId",group.get("user_id")+"");
        return ResultBuilder.buildListSuccess(showMap);
    }




    /**
     * 启动审批流程并改变问卷结果状态
     * */
    @PostMapping("/createExamine")
    public ActionResult createExamine(@RequestBody JSONObject groupSetting){
        VerificationAuth();
        long userId=groupSetting.getLong("userId");
//        String roleCode=sysUserService.getRoleCodeByUserId(userId);
        String roleCode=GetRoleCode();
        long bakId=examineService.CreateExamine(groupSetting);
        if(StrUtil.equals(roleCode,"ROLE_EAC_ADMIN")||StrUtil.equals(roleCode,"ROLE_MANAGER")){
            System.out.println("管理员跳过审核，直接通过验证");
            long pdcId=groupSetting.getJSONObject("pdc").getLong("id");
            examineService.adopt(bakId,pdcId);
        }
        return ResultBuilder.buildActionSuccess();
    }
}
