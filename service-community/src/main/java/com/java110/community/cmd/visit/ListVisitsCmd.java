package com.java110.community.cmd.visit;

import com.alibaba.fastjson.JSONObject;
import com.google.protobuf.Api;
import com.java110.core.annotation.Java110Cmd;
import com.java110.core.context.ICmdDataFlowContext;
import com.java110.core.event.cmd.Cmd;
import com.java110.core.event.cmd.CmdEvent;
import com.java110.core.factory.CommunitySettingFactory;
import com.java110.doc.annotation.*;
import com.java110.dto.visit.VisitDto;
import com.java110.dto.visitSetting.VisitSettingDto;
import com.java110.intf.community.IVisitInnerServiceSMO;
import com.java110.intf.community.IVisitSettingV1InnerServiceSMO;
import com.java110.utils.exception.CmdException;
import com.java110.utils.util.Assert;
import com.java110.utils.util.BeanConvertUtil;
import com.java110.utils.util.StringUtil;
import com.java110.vo.api.visit.ApiVisitDataVo;
import com.java110.vo.api.visit.ApiVisitVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Java110CmdDoc(title = "查询访客",
        description = "供pc端查询访客人员",
        httpMethod = "get",
        url = "http://{ip}:{port}/app/visit.listVisits",
        resource = "communityDoc",
        author = "吴学文",
        serviceCode = "visit.listVisits"
)

@Java110ParamsDoc(params = {
        @Java110ParamDoc(name = "page", type = "int", length = 11, remark = "分页信息"),
        @Java110ParamDoc(name = "row", type = "int", length = 11, remark = "行数"),
        @Java110ParamDoc(name = "communityId", length = 30, remark = "小区ID"),
})

@Java110ResponseDoc(
        params = {
                @Java110ParamDoc(name = "code", type = "int", length = 11, defaultValue = "0", remark = "返回编号，0 成功 其他失败"),
                @Java110ParamDoc(name = "msg", type = "String", length = 250, defaultValue = "成功", remark = "描述"),
                @Java110ParamDoc(name = "visits", type = "Array", remark = "有效数据"),
                @Java110ParamDoc(parentNodeName = "visits", name = "vId", type = "String", remark = "访客ID"),
                @Java110ParamDoc(parentNodeName = "visits", name = "vName", type = "String", remark = "访客名称"),
                @Java110ParamDoc(parentNodeName = "visits", name = "visitGender", type = "String", remark = "访客性别"),
                @Java110ParamDoc(parentNodeName = "visits", name = "phoneNumber", type = "String", remark = "手机号"),
                @Java110ParamDoc(parentNodeName = "visits", name = "visitTime", type = "String", remark = "访问时间"),
        }
)

@Java110ExampleDoc(
        reqBody = "ttp://localhost:3000/app/visit.listVisits?page=1&row=10&communityId=2022121921870161",
        resBody = "{\"page\":0,\"records\":0,\"rows\":0,\"total\":0,\"visits\":[]}"
)
@Java110Cmd(serviceCode = "visit.listVisits")
public class ListVisitsCmd extends Cmd {

    @Autowired
    private IVisitInnerServiceSMO visitInnerServiceSMOImpl;

    @Autowired
    private IVisitSettingV1InnerServiceSMO visitSettingV1InnerServiceSMOImpl;

    //键
    public static final String CAR_FREE_TIME = "CAR_FREE_TIME";

    //键
    public static final String VISIT_NUMBER = "VISIT_NUMBER";

    @Override
    public void validate(CmdEvent event, ICmdDataFlowContext context, JSONObject reqJson) throws CmdException {
        super.validatePageInfo(reqJson);

        Assert.hasKeyAndValue(reqJson, "communityId", "未包含小区");

    }

    @Override
    public void doCmd(CmdEvent event, ICmdDataFlowContext context, JSONObject reqJson) throws CmdException {


        ResponseEntity<String> responseEntity = new ResponseEntity<String>("", HttpStatus.OK);
        if (reqJson.containsKey("addVisitType") && !StringUtil.isEmpty(reqJson.getString("addVisitType"))
                && "initAddVisitParameter".equals(reqJson.getString("addVisitType"))) {
            //预约车免费时长
            String freeTime = CommunitySettingFactory.getValue(reqJson.getString("communityId"), CAR_FREE_TIME);
            String numStr = CommunitySettingFactory.getValue(reqJson.getString("communityId"), VISIT_NUMBER);
            int number = 999;
            if (StringUtil.isInteger(numStr)) {
                number = Integer.parseInt(numStr);
            }
            Map initAddVisitParameter = new HashMap();
            initAddVisitParameter.put("freeTime", freeTime);
            initAddVisitParameter.put("freeTimes", number);
            //业主端获取访客登记相关配置参数
            responseEntity = new ResponseEntity<String>(JSONObject.toJSONString(initAddVisitParameter), HttpStatus.OK);
        } else {
            VisitDto visitDto = BeanConvertUtil.covertBean(reqJson, VisitDto.class);
            if (reqJson.containsKey("channel") && !StringUtil.isEmpty(reqJson.getString("channel"))
                    && "PC".equals(reqJson.getString("channel"))) {
                visitDto.setUserId("");
            }
            int count = visitInnerServiceSMOImpl.queryVisitsCount(visitDto);
            List<ApiVisitDataVo> visits = new ArrayList<>();
            if (count > 0) {
                List<VisitDto> visitDtos = visitInnerServiceSMOImpl.queryVisits(visitDto);
                for (VisitDto visit : visitDtos) {
                    ApiVisitDataVo apiVisitDataVo = BeanConvertUtil.covertBean(visit, ApiVisitDataVo.class);
                    if (!StringUtil.isEmpty(visit.getFileSaveName())) {
                        apiVisitDataVo.setUrl(visit.getFileSaveName());
                    }
                    visits.add(apiVisitDataVo);
                }
            } else {
                visits = new ArrayList<>();
            }

            //刷入流程ID
            refreshSetting(visits, reqJson);

            ApiVisitVo apiVisitVo = new ApiVisitVo();
            apiVisitVo.setTotal(count);
            apiVisitVo.setRecords((int) Math.ceil((double) count / (double) reqJson.getInteger("row")));
            apiVisitVo.setVisits(visits);

            responseEntity = new ResponseEntity<String>(JSONObject.toJSONString(apiVisitVo), HttpStatus.OK);
        }
        context.setResponseEntity(responseEntity);

    }

    private void refreshSetting(List<ApiVisitDataVo> visits, JSONObject reqJson) {
        VisitSettingDto visitSettingDto = new VisitSettingDto();
        visitSettingDto.setCommunityId(reqJson.getString("communityId"));
        List<VisitSettingDto> visitSettingDtos = visitSettingV1InnerServiceSMOImpl.queryVisitSettings(visitSettingDto);

        if (visitSettingDtos == null || visitSettingDtos.size() < 1) {
            return;
        }

        if(visits == null || visits.size() < 1){
            return;
        }

        for(ApiVisitDataVo visitDataVo:visits){
            visitDataVo.setFlowId(visitSettingDtos.get(0).getFlowId());
        }

    }
}
