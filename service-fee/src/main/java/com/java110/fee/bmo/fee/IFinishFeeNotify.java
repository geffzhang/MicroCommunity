package com.java110.fee.bmo.fee;

import com.alibaba.fastjson.JSONObject;

public interface IFinishFeeNotify {

    /**
     * 修改 车辆结束时间
     * @param feeId
     * @param communityId
     */
    void updateCarEndTime(String feeId,String communityId);

    /**
     * 修改报修
     * @param feeId
     * @param communityId
     */
    void updateRepair(String feeId,String communityId,String receivedAmount);


    /**
     * 账户扣款
     * @param paramObj
     * @param feeId
     * @param communityId
     */
    void withholdAccount(JSONObject paramObj, String feeId, String communityId);
}
