package com.java110.dto.paymentPoolValue;

import com.java110.dto.PageDto;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName FloorDto
 * @Description 支付参数数据层封装
 * @Author wuxw
 * @Date 2019/4/24 8:52
 * @Version 1.0
 * add by wuxw 2019/4/24
 **/
public class PaymentPoolValueDto extends PageDto implements Serializable {

    private String valueId;
private String columnValue;
private String communityId;
private String columnKey;
private String ppId;


    private Date createTime;

    private String statusCd = "0";


    public String getValueId() {
        return valueId;
    }
public void setValueId(String valueId) {
        this.valueId = valueId;
    }
public String getColumnValue() {
        return columnValue;
    }
public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
    }
public String getCommunityId() {
        return communityId;
    }
public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }
public String getColumnKey() {
        return columnKey;
    }
public void setColumnKey(String columnKey) {
        this.columnKey = columnKey;
    }
public String getPpId() {
        return ppId;
    }
public void setPpId(String ppId) {
        this.ppId = ppId;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }
}
