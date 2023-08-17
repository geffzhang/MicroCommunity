package com.java110.dto.oweFeeCallable;

import com.java110.dto.PageDto;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName FloorDto
 * @Description 催缴记录数据层封装
 * @Author wuxw
 * @Date 2019/4/24 8:52
 * @Version 1.0
 * add by wuxw 2019/4/24
 **/
public class OweFeeCallableDto extends PageDto implements Serializable {

    private String remark;
private String ofcId;
private String ownerId;
private String callableWay;
private String feeId;
private String payerObjName;
private String ownerName;
private String configId;
private String feeName;
private String staffName;
private String amountdOwed;
private String state;
private String communityId;
private String payerObjType;
private String staffId;
private String payerObjId;


    private Date createTime;

    private String statusCd = "0";


    public String getRemark() {
        return remark;
    }
public void setRemark(String remark) {
        this.remark = remark;
    }
public String getOfcId() {
        return ofcId;
    }
public void setOfcId(String ofcId) {
        this.ofcId = ofcId;
    }
public String getOwnerId() {
        return ownerId;
    }
public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }
public String getCallableWay() {
        return callableWay;
    }
public void setCallableWay(String callableWay) {
        this.callableWay = callableWay;
    }
public String getFeeId() {
        return feeId;
    }
public void setFeeId(String feeId) {
        this.feeId = feeId;
    }
public String getPayerObjName() {
        return payerObjName;
    }
public void setPayerObjName(String payerObjName) {
        this.payerObjName = payerObjName;
    }
public String getOwnerName() {
        return ownerName;
    }
public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
public String getConfigId() {
        return configId;
    }
public void setConfigId(String configId) {
        this.configId = configId;
    }
public String getFeeName() {
        return feeName;
    }
public void setFeeName(String feeName) {
        this.feeName = feeName;
    }
public String getStaffName() {
        return staffName;
    }
public void setStaffName(String staffName) {
        this.staffName = staffName;
    }
public String getAmountdOwed() {
        return amountdOwed;
    }
public void setAmountdOwed(String amountdOwed) {
        this.amountdOwed = amountdOwed;
    }
public String getState() {
        return state;
    }
public void setState(String state) {
        this.state = state;
    }
public String getCommunityId() {
        return communityId;
    }
public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }
public String getPayerObjType() {
        return payerObjType;
    }
public void setPayerObjType(String payerObjType) {
        this.payerObjType = payerObjType;
    }
public String getStaffId() {
        return staffId;
    }
public void setStaffId(String staffId) {
        this.staffId = staffId;
    }
public String getPayerObjId() {
        return payerObjId;
    }
public void setPayerObjId(String payerObjId) {
        this.payerObjId = payerObjId;
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
