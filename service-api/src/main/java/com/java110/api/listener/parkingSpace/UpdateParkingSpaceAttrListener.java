package com.java110.api.listener.parkingSpace;

import com.alibaba.fastjson.JSONObject;
import com.java110.api.bmo.parkingSpaceAttr.IParkingSpaceAttrBMO;
import com.java110.api.listener.AbstractServiceApiPlusListener;
import com.java110.core.annotation.Java110Listener;
import com.java110.core.context.DataFlowContext;
import com.java110.core.event.service.api.ServiceDataFlowEvent;
import com.java110.utils.constant.ServiceCodeParkingSpaceAttrConstant;
import com.java110.utils.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;


/**
 * 保存车位属性侦听
 * add by wuxw 2019-06-30
 */
@Java110Listener("updateParkingSpaceAttrListener")
public class UpdateParkingSpaceAttrListener extends AbstractServiceApiPlusListener {

    @Autowired
    private IParkingSpaceAttrBMO parkingSpaceAttrBMOImpl;

    @Override
    protected void validate(ServiceDataFlowEvent event, JSONObject reqJson) {

        Assert.hasKeyAndValue(reqJson, "attrId", "attrId不能为空");
        Assert.hasKeyAndValue(reqJson, "specCd", "请求报文中未包含specCd");

    }

    @Override
    protected void doSoService(ServiceDataFlowEvent event, DataFlowContext context, JSONObject reqJson) {

        parkingSpaceAttrBMOImpl.updateParkingSpaceAttr(reqJson, context);
    }

    @Override
    public String getServiceCode() {
        return ServiceCodeParkingSpaceAttrConstant.UPDATE_PARKINGSPACEATTR;
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.POST;
    }
}
