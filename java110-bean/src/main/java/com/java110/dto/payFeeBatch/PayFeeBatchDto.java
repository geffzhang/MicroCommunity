package com.java110.dto.payFeeBatch;

import com.java110.dto.PageDto;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName FloorDto
 * @Description 费用批次数据层封装
 * @Author wuxw
 * @Date 2019/4/24 8:52
 * @Version 1.0
 * add by wuxw 2019/4/24
 **/
public class PayFeeBatchDto extends PageDto implements Serializable {

    private String msg;
private String createUserId;
private String createUserName;
private String remark;
private String state;
private String batchId;
private String communityId;


    private Date createTime;

    private String statusCd = "0";


    public String getMsg() {
        return msg;
    }
public void setMsg(String msg) {
        this.msg = msg;
    }
public String getCreateUserId() {
        return createUserId;
    }
public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }
public String getCreateUserName() {
        return createUserName;
    }
public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }
public String getRemark() {
        return remark;
    }
public void setRemark(String remark) {
        this.remark = remark;
    }
public String getState() {
        return state;
    }
public void setState(String state) {
        this.state = state;
    }
public String getBatchId() {
        return batchId;
    }
public void setBatchId(String batchId) {
        this.batchId = batchId;
    }
public String getCommunityId() {
        return communityId;
    }
public void setCommunityId(String communityId) {
        this.communityId = communityId;
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
