package com.java110.job.adapt.fee.asyn;

import com.alibaba.fastjson.JSONObject;
import com.java110.dto.system.Business;

public interface IPayFeeDetailToMonth {

    void doPayFeeDetail(Business business, JSONObject businessPayFeeDetail);
}
