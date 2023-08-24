package com.java110.po.store;

import java.io.Serializable;
import java.util.Date;

public class StorehousePo implements Serializable {

    private String shDesc;
    private String shType;
    private String shObjId;
    private String shId;
    private String shName;
    private String storeId;
    private String isShow;

    private String statusCd = "0";

    private String purchaseSwitch;
    private String purchaseRafId;
    private String useSwitch;
    private String useRafId;
    private String allocationSwitch;
    private String allocationRafId;

    public String getShDesc() {
        return shDesc;
    }

    public void setShDesc(String shDesc) {
        this.shDesc = shDesc;
    }

    public String getShType() {
        return shType;
    }

    public void setShType(String shType) {
        this.shType = shType;
    }

    public String getShObjId() {
        return shObjId;
    }

    public void setShObjId(String shObjId) {
        this.shObjId = shObjId;
    }

    public String getShId() {
        return shId;
    }

    public void setShId(String shId) {
        this.shId = shId;
    }

    public String getShName() {
        return shName;
    }

    public void setShName(String shName) {
        this.shName = shName;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }

    public String getPurchaseSwitch() {
        return purchaseSwitch;
    }

    public void setPurchaseSwitch(String purchaseSwitch) {
        this.purchaseSwitch = purchaseSwitch;
    }

    public String getPurchaseRafId() {
        return purchaseRafId;
    }

    public void setPurchaseRafId(String purchaseRafId) {
        this.purchaseRafId = purchaseRafId;
    }

    public String getUseSwitch() {
        return useSwitch;
    }

    public void setUseSwitch(String useSwitch) {
        this.useSwitch = useSwitch;
    }

    public String getUseRafId() {
        return useRafId;
    }

    public void setUseRafId(String useRafId) {
        this.useRafId = useRafId;
    }

    public String getAllocationSwitch() {
        return allocationSwitch;
    }

    public void setAllocationSwitch(String allocationSwitch) {
        this.allocationSwitch = allocationSwitch;
    }

    public String getAllocationRafId() {
        return allocationRafId;
    }

    public void setAllocationRafId(String allocationRafId) {
        this.allocationRafId = allocationRafId;
    }
}
