package com.java110.api.bmo.task;

import com.alibaba.fastjson.JSONObject;
import com.java110.api.bmo.IApiBaseBMO;
import com.java110.core.context.DataFlowContext;

public interface ITaskAttrBMO extends IApiBaseBMO {


    /**
     * 添加定时任务属性
     * @param paramInJson
     * @param dataFlowContext
     * @return
     */
     void addTaskAttr(JSONObject paramInJson, DataFlowContext dataFlowContext);

    /**
     * 添加定时任务属性信息
     *
     * @param paramInJson     接口调用放传入入参
     * @param dataFlowContext 数据上下文
     * @return 订单服务能够接受的报文
     */
     void updateTaskAttr(JSONObject paramInJson, DataFlowContext dataFlowContext);

    /**
     * 删除定时任务属性
     *
     * @param paramInJson     接口调用放传入入参
     * @param dataFlowContext 数据上下文
     * @return 订单服务能够接受的报文
     */
     void deleteTaskAttr(JSONObject paramInJson, DataFlowContext dataFlowContext);



}
