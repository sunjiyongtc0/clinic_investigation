package com.clin.api.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.clin.api.service.ExamineService;
import com.clin.api.service.InvestigationService;
import com.clin.api.service.PdcService;
import com.clin.api.service.QuestionService;
import com.clin.api.vo.ProDataVO;
import com.clin.auth.utils.CurrentUserUtils;
import com.clin.core.result.ActionResult;
import com.clin.core.result.ListResult;
import com.clin.core.result.ResultBuilder;
import com.clin.core.utils.ExcelUtils;
import com.clin.log.common.annotation.Log;
import com.clin.log.common.enums.BusinessType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * <pre>
 * Title :  调查结果信息
 * </pre>
 *
 * @author : SunJiYong
 * @since : 2022-03-04
 **/
@RestController
@RequestMapping("/pro/inv")
public class InvestigationController {

    @Autowired
    private InvestigationService investigationService;

    @Autowired
    private PdcService pdcService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private ExamineService examineService;


    /**
     * 分页显示信息
     * */
    @GetMapping("/queryPdcInfos")
    public ListResult<Object> queryProData(ProDataVO proDataVO){
        proDataVO.setRoleCode(CurrentUserUtils.getOAuth2AuthenticationDetailsInfo().get("role_code"));
        proDataVO.setUserId( Long.parseLong(CurrentUserUtils.getOAuth2AuthenticationDetailsInfo().get("user_id")));
        Map<String, Object> data =investigationService.queryProData(proDataVO);
        return ResultBuilder.buildListSuccess(data);
    }

    /**
     * 删除义诊信息
     * */
    @PostMapping("/deleteInv")
    public ActionResult deleteInv(@RequestParam(name = "id", required = true) long id){
        investigationService.deleteInv(id);
        return ResultBuilder.buildActionSuccess();
    }

    /**
     *获取问卷详情
     * */
    @GetMapping("/findDetailById")
    public ListResult<Object> findDetailById(@RequestParam(name = "id", required = true) long id){
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

    @Log(title = "导出义诊数据", businessType = BusinessType.EXPORT)
    @PostMapping(path = "/exportInv")
    public void exportInv(HttpServletResponse response, @RequestParam Map<String, Object> paramMap) throws IOException {
            paramMap.put("roleCode",CurrentUserUtils.getOAuth2AuthenticationDetailsInfo().get("role_code"));
            paramMap.put( "userId",Long.parseLong(CurrentUserUtils.getOAuth2AuthenticationDetailsInfo().get("user_id")));
            List<String> headList = Arrays.asList("问卷编号", "患者姓名", "患者手机号", "患者年龄", "患者性别", "录入时间", "健康值", "志愿者账号");
            List<LinkedHashMap<String, Object>> dataList = investigationService.queryProDataExcel(paramMap);
            ExcelUtils.exportExcel(headList, dataList, "义诊列表", response);
    }



    @GetMapping("/saveData")
    public ActionResult saveData(@RequestBody Map<String,String> m){
        investigationService.saveData(m);
        return ResultBuilder.buildActionSuccess();
    }





    /**
     * 启动审批流程并改变问卷结果状态
     * */
    @PostMapping("/createExamine")
    public ActionResult createExamine(@RequestBody JSONObject groupSetting){
        String roleCode=CurrentUserUtils.getOAuth2AuthenticationDetailsInfo().get("role_code");
        long bakId=examineService.CreateExamine(groupSetting);
        if(StrUtil.equals(roleCode,"ROLE_EAC_ADMIN")||StrUtil.equals(roleCode,"ROLE_MANAGER")){
            System.out.println("管理员跳过审核，直接通过验证");
            long pdcId=groupSetting.getJSONObject("pdc").getLong("id");
            examineService.adopt(bakId,pdcId);
        }
        return ResultBuilder.buildActionSuccess();
    }

}
