/*
 * Copyright 2017-2020 吴学文 and java110 team.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.java110.po.storeShopCommunity;

import java.io.Serializable;
import java.util.Date;
/**
 * 类表述： Po 数据模型实体对象 基本保持与数据库模型一直 用于 增加修改删除 等时的数据载体
 * add by 吴学文 at 2022-10-11 01:12:22 mail: 928255095@qq.com
 * open source address: https://gitee.com/wuxw7/MicroCommunity
 * 官网：http://www.homecommunity.cn
 * 温馨提示：如果您对此文件进行修改 请不要删除原有作者及注释信息，请补充您的 修改的原因以及联系邮箱如下
 * // modify by 张三 at 2021-09-12 第10行在某种场景下存在某种bug 需要修复，注释10至20行 加入 20行至30行
 */
public class StoreShopCommunityPo implements Serializable {

    private String address;
private String cityCode;
private String codeName;
private String communityName;
private String startTime;
private String statusCd = "0";
private String shopId;
private String endTime;
private String state;
private String communityId;
private String message;
private String scId;
public String getAddress() {
        return address;
    }
public void setAddress(String address) {
        this.address = address;
    }
public String getCityCode() {
        return cityCode;
    }
public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
public String getCodeName() {
        return codeName;
    }
public void setCodeName(String codeName) {
        this.codeName = codeName;
    }
public String getCommunityName() {
        return communityName;
    }
public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }
public String getStartTime() {
        return startTime;
    }
public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
public String getStatusCd() {
        return statusCd;
    }
public void setStatusCd(String statusCd) {
        this.statusCd = statusCd;
    }
public String getShopId() {
        return shopId;
    }
public void setShopId(String shopId) {
        this.shopId = shopId;
    }
public String getEndTime() {
        return endTime;
    }
public void setEndTime(String endTime) {
        this.endTime = endTime;
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
public String getMessage() {
        return message;
    }
public void setMessage(String message) {
        this.message = message;
    }
public String getScId() {
        return scId;
    }
public void setScId(String scId) {
        this.scId = scId;
    }



}
