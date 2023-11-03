package com.java110.report.cmd.admin;

import com.alibaba.fastjson.JSONObject;
import com.java110.core.annotation.Java110Cmd;
import com.java110.core.context.CmdContextUtils;
import com.java110.core.context.ICmdDataFlowContext;
import com.java110.core.event.cmd.Cmd;
import com.java110.core.event.cmd.CmdEvent;
import com.java110.dto.community.CommunityDto;
import com.java110.dto.store.StoreDto;
import com.java110.intf.community.ICommunityV1InnerServiceSMO;
import com.java110.intf.report.IBaseDataStatisticsInnerServiceSMO;
import com.java110.intf.store.IStoreInnerServiceSMO;
import com.java110.intf.store.IStoreV1InnerServiceSMO;
import com.java110.utils.exception.CmdException;
import com.java110.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.util.*;

/**
 * 查询物业费台账
 */
@Java110Cmd(serviceCode = "admin.getPropertyFeeSummary")
public class GetPropertyFeeSummaryCmd extends Cmd {

    @Autowired
    private IStoreInnerServiceSMO storeInnerServiceSMOImpl;

    @Autowired
    private IBaseDataStatisticsInnerServiceSMO baseDataStatisticsInnerServiceSMOImpl;


    @Autowired
    private ICommunityV1InnerServiceSMO communityV1InnerServiceSMOImpl;

    @Autowired
    private IStoreV1InnerServiceSMO storeV1InnerServiceSMOImpl;


    @Override
    public void validate(CmdEvent event, ICmdDataFlowContext context, JSONObject reqJson) throws CmdException, ParseException {
        super.validatePageInfo(reqJson);
        String storeId = CmdContextUtils.getStoreId(context);

        StoreDto storeDto = new StoreDto();
        storeDto.setStoreId(storeId);
        storeDto.setStoreTypeCd(StoreDto.STORE_TYPE_ADMIN);
        int count = storeInnerServiceSMOImpl.getStoreCount(storeDto);
        if (count < 1) {
            throw new CmdException("非法操作，请用系统管理员账户操作");
        }
    }

    @Override
    public void doCmd(CmdEvent event, ICmdDataFlowContext context, JSONObject reqJson) throws CmdException, ParseException {

        int row = reqJson.getIntValue("row");
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        //todo 查询房屋物业费信息
        reqJson.put("startTime", year + "-01-01");
        reqJson.put("endTime", (year + 1) + "-01-01");

        // todo 查询总数量
        int total = baseDataStatisticsInnerServiceSMOImpl.getPropertyFeeSummaryDataCount(reqJson);

        List<Map> infos = null;
        if (total > 0) {
            infos = baseDataStatisticsInnerServiceSMOImpl.getPropertyFeeSummaryData(reqJson);
        } else {
            infos = new ArrayList<>();
        }

        //todo 计算每月情况
        computeEveryMonthFee(infos, reqJson);

        //todo 计算小区和物业公司信息
        computeCommunityAndProperty(infos);

        ResponseEntity<String> responseEntity = ResultVo.createResponseEntity((int) Math.ceil((double) total / (double) row), total, infos);
        context.setResponseEntity(responseEntity);

    }

    private void computeCommunityAndProperty(List<Map> infos) {

        CommunityDto communityDto = new CommunityDto();
        List<CommunityDto> communityDtos = communityV1InnerServiceSMOImpl.queryCommunityAndPropertys(communityDto);

        if (communityDtos == null || communityDtos.isEmpty()) {
            return;
        }

        //todo 查询 物业名称

        StoreDto storeDto = new StoreDto();
        storeDto.setStoreTypeCd(StoreDto.STORE_TYPE_PROPERTY);
        List<StoreDto> storeDtos = storeV1InnerServiceSMOImpl.queryStores(storeDto);

        for(CommunityDto tmpCommunityDto : communityDtos){
            for(StoreDto tmpStoreDto: storeDtos){
                if(tmpStoreDto.getStoreId().equals(tmpCommunityDto.getStoreId())){
                    tmpCommunityDto.setStoreName(tmpStoreDto.getName());
                }
            }
        }

        for(Map info: infos){
            for(CommunityDto tmpCommunityDto : communityDtos){
                if(info.get("communityId").equals(tmpCommunityDto.getCommunityId())){
                    info.put("communityName",tmpCommunityDto.getName());
                    info.put("storeId",tmpCommunityDto.getStoreId());
                    info.put("storeName",tmpCommunityDto.getStoreName());
                }
            }
        }
    }

    private void computeEveryMonthFee(List<Map> infos, JSONObject reqJson) {
        if (infos == null || infos.isEmpty()) {
            return;
        }

        List<String> roomIds = new ArrayList<>();
        for (Map info : infos) {
            roomIds.add(info.get("roomId").toString());
        }

        Map info = new HashMap();
        info.put("roomIds", roomIds.toArray(new String[roomIds.size()]));
        info.put("startTime", reqJson.getString("startTime"));
        info.put("endTime", reqJson.getString("endTime"));

        List<Map> monthDatas = baseDataStatisticsInnerServiceSMOImpl.computeEveryMonthFee(info);

        List<Map> months = null;
        for (Map roomInfo : infos) {
            months = new ArrayList<>();
            for (Map monthInfo : monthDatas) {
                if (roomInfo.get("roomId").equals(monthInfo.get("roomId"))
                        && roomInfo.get("configId").equals(monthInfo.get("configId"))) {
                    months.add(monthInfo);
                }
            }

            roomInfo.put("monthData", months);
        }
    }
}
