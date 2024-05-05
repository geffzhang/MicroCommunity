package com.java110.store.cmd.resourceStore;

import com.alibaba.fastjson.JSONObject;
import com.java110.core.annotation.Java110Cmd;
import com.java110.core.context.ICmdDataFlowContext;
import com.java110.core.event.cmd.Cmd;
import com.java110.core.event.cmd.CmdEvent;
import com.java110.dto.oaWorkflow.OaWorkflowDto;
import com.java110.dto.oaWorkflow.WorkflowDto;
import com.java110.dto.oaWorkflow.WorkflowStepStaffDto;
import com.java110.dto.purchase.AllocationStorehouseApplyDto;
import com.java110.dto.audit.AuditUser;
import com.java110.dto.purchase.PurchaseApplyDto;
import com.java110.intf.common.IAllocationStorehouseUserInnerServiceSMO;
import com.java110.intf.common.IOaWorkflowActivitiInnerServiceSMO;
import com.java110.intf.common.IWorkflowStepStaffInnerServiceSMO;
import com.java110.intf.oa.IOaWorkflowInnerServiceSMO;
import com.java110.intf.store.IAllocationStorehouseApplyInnerServiceSMO;
import com.java110.utils.exception.CmdException;
import com.java110.utils.util.Assert;
import com.java110.utils.util.BeanConvertUtil;
import com.java110.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * 查询调拨
 */
@Java110Cmd(serviceCode = "resourceStore.listAllocationStoreAuditOrders")
public class ListAllocationStoreAuditOrdersCmd extends Cmd {

    @Autowired
    private IAllocationStorehouseUserInnerServiceSMO allocationStorehouseUserInnerServiceSMOImpl;


    @Autowired
    private IWorkflowStepStaffInnerServiceSMO workflowStepStaffInnerServiceSMOImpl;

    @Autowired
    private IOaWorkflowActivitiInnerServiceSMO oaWorkflowUserInnerServiceSMOImpl;

    @Autowired
    private IOaWorkflowInnerServiceSMO oaWorkflowInnerServiceSMOImpl;

    @Autowired
    private IOaWorkflowActivitiInnerServiceSMO oaWorkflowActivitiInnerServiceSMOImpl;

    @Autowired
    private IAllocationStorehouseApplyInnerServiceSMO allocationStorehouseApplyInnerServiceSMOImpl;

    @Override
    public void validate(CmdEvent event, ICmdDataFlowContext context, JSONObject reqJson) throws CmdException {

        Assert.hasKeyAndValue(reqJson, "row", "必填，请填写每页显示数");
        Assert.hasKeyAndValue(reqJson, "page", "必填，请填写页数");

        super.validatePageInfo(reqJson);
    }

    @Override
    public void doCmd(CmdEvent event, ICmdDataFlowContext context, JSONObject reqJson) throws CmdException, ParseException {

        String userId = context.getReqHeaders().get("user-id");
        String storeId = context.getReqHeaders().get("store-id");

        OaWorkflowDto oaWorkflowDto = new OaWorkflowDto();
        oaWorkflowDto.setState(OaWorkflowDto.STATE_COMPLAINT);
        oaWorkflowDto.setFlowType(OaWorkflowDto.FLOW_TYPE_ALLOCATION);
        List<OaWorkflowDto> oaWorkflowDtos = oaWorkflowInnerServiceSMOImpl.queryOaWorkflows(oaWorkflowDto);

        if (oaWorkflowDtos == null || oaWorkflowDtos.size() < 1) {
            return;
        }
        List<String> flowIds = new ArrayList<>();
        for (OaWorkflowDto tmpOaWorkflowDto : oaWorkflowDtos) {
            flowIds.add(WorkflowDto.DEFAULT_PROCESS + tmpOaWorkflowDto.getFlowId());
        }

        AuditUser auditUser = new AuditUser();
        auditUser.setProcessDefinitionKeys(flowIds);
        auditUser.setUserId(userId);
        auditUser.setStoreId(storeId);
        auditUser.setPage(reqJson.getInteger("page"));
        auditUser.setRow(reqJson.getInteger("row"));

        long count = oaWorkflowUserInnerServiceSMOImpl.getDefinitionKeysUserTaskCount(auditUser);

        List<JSONObject> datas = null;

        if (count > 0) {
            datas = oaWorkflowUserInnerServiceSMOImpl.getDefinitionKeysUserTasks(auditUser);
            //刷新 表单数据
            refreshFormData(datas, reqJson, storeId,oaWorkflowDtos.get(0).getFlowId());
        } else {
            datas = new ArrayList<>();
        }

        ResultVo resultVo = new ResultVo((int) Math.ceil((double) count / (double) reqJson.getInteger("row")), count, datas);

        ResponseEntity<String> responseEntity = new ResponseEntity<String>(resultVo.toString(), HttpStatus.OK);
        context.setResponseEntity(responseEntity);

    }

    private void refreshFormData(List<JSONObject> datas, JSONObject paramIn, String storeId,String flowId) {

        List<String> ids = new ArrayList<>();
        for (JSONObject data : datas) {
            data.put("flowId", flowId);
            ids.add(data.getString("id"));
        }
        if (ids.size() < 1) {
            return;
        }

        //查询 投诉信息
        AllocationStorehouseApplyDto allocationStorehouseApplyDto = new AllocationStorehouseApplyDto();
        allocationStorehouseApplyDto.setStoreId(storeId);
        allocationStorehouseApplyDto.setApplyIds(ids.toArray(new String[ids.size()]));
        List<AllocationStorehouseApplyDto> tmpAllocationStorehouseApplyDtos
                = allocationStorehouseApplyInnerServiceSMOImpl.queryAllocationStorehouseApplys(allocationStorehouseApplyDto);


        if (tmpAllocationStorehouseApplyDtos == null || tmpAllocationStorehouseApplyDtos.size() < 1) {
            return;
        }

        for (AllocationStorehouseApplyDto tmpAllocationStorehouseApplyDto : tmpAllocationStorehouseApplyDtos) {
            switch (tmpAllocationStorehouseApplyDto.getState()) {
                case "1000":
                    tmpAllocationStorehouseApplyDto.setStateName("待审核");
                    break;
                case "1001":
                    tmpAllocationStorehouseApplyDto.setStateName("审核中");
                    break;
                case "1002":
                    tmpAllocationStorehouseApplyDto.setStateName("已审核");
                    break;
            }
        }
        JSONObject curTaskNode = null;
        for (JSONObject data : datas) {

            //todo 计算 当前节点名称
            curTaskNode = new JSONObject();
            curTaskNode.put("taskId", data.getString("taskId"));
            curTaskNode = oaWorkflowActivitiInnerServiceSMOImpl.getCurrentNodeTask(curTaskNode);
            data.put("curTaskName", curTaskNode.getString("curTaskName"));

            for (AllocationStorehouseApplyDto form : tmpAllocationStorehouseApplyDtos) {
                if (data.getString("id").equals(form.getApplyId())) {
                    data.putAll(BeanConvertUtil.beanCovertJson(form));
                }
            }
        }
    }


}
