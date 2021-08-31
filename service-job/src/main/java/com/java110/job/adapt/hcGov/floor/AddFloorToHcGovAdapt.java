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
package com.java110.job.adapt.hcGov.floor;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.java110.core.kafka.KafkaConsumerConfig;
import com.java110.dto.UnitDto;
import com.java110.dto.community.CommunityAttrDto;
import com.java110.dto.community.CommunityDto;
import com.java110.entity.order.Business;
import com.java110.intf.community.ICommunityInnerServiceSMO;
import com.java110.intf.community.IUnitInnerServiceSMO;
import com.java110.job.adapt.DatabusAdaptImpl;
import com.java110.job.adapt.hcGov.HcGovConstant;
import com.java110.po.floor.FloorPo;
import com.java110.utils.cache.MappingCache;
import com.java110.utils.kafka.KafkaFactory;
import com.java110.utils.util.Assert;
import com.java110.utils.util.BeanConvertUtil;
import com.java110.utils.util.DateUtil;
import com.java110.utils.util.PayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 新增楼栋同步HC政务接口
 * <p>
 * 接口协议地址： https://gitee.com/java110/microCommunityInformation/tree/master/info-doc#1%E6%A5%BC%E6%A0%8B%E4%B8%8A%E4%BC%A0
 *
 * @desc add by 吴学文 16:20
 */
@Component(value = "addFloorToHcGovAdapt")
public class AddFloorToHcGovAdapt extends DatabusAdaptImpl {

    @Autowired
    private ICommunityInnerServiceSMO communityInnerServiceSMOImpl;

    @Autowired
    private IUnitInnerServiceSMO unitInnerServiceSMOImpl;


    /**
     *
     * @param business   当前处理业务
     * @param businesses 所有业务信息
     */
    @Override
    public void execute(Business business, List<Business> businesses) {
        JSONObject data = business.getData();
        if (data.containsKey(FloorPo.class.getSimpleName())) {
            Object bObj = data.get(FloorPo.class.getSimpleName());
            JSONArray businessOwnerCars = null;
            if (bObj instanceof JSONObject) {
                businessOwnerCars = new JSONArray();
                businessOwnerCars.add(bObj);
            } else if (bObj instanceof List) {
                businessOwnerCars = JSONArray.parseArray(JSONObject.toJSONString(bObj));
            } else {
                businessOwnerCars = (JSONArray) bObj;
            }
            //JSONObject businessOwnerCar = data.getJSONObject("businessOwnerCar");
            for (int bOwnerCarIndex = 0; bOwnerCarIndex < businessOwnerCars.size(); bOwnerCarIndex++) {
                JSONObject businessOwnerCar = businessOwnerCars.getJSONObject(bOwnerCarIndex);
                doAddFloor(business, businessOwnerCar);
            }
        }
    }

    private void doAddFloor(Business business, JSONObject businessFloor) {

        FloorPo floorPo = BeanConvertUtil.covertBean(businessFloor, FloorPo.class);
        CommunityDto communityDto = new CommunityDto();
        communityDto.setCommunityId(floorPo.getCommunityId());
        List<CommunityDto> communityDtos = communityInnerServiceSMOImpl.queryCommunitys(communityDto);

        Assert.listOnlyOne(communityDtos, "未包含小区信息");
        CommunityDto tmpCommunityDto = communityDtos.get(0);
        String extCommunityId = "";

        for (CommunityAttrDto communityAttrDto : tmpCommunityDto.getCommunityAttrDtos()) {
            if (HcGovConstant.EXT_COMMUNITY_ID.equals(communityAttrDto.getSpecCd())) {
                extCommunityId = communityAttrDto.getValue();
            }
        }
        UnitDto unitDto = new UnitDto();
        unitDto.setFloorId(floorPo.getFloorId());
        unitDto.setCommunityId(floorPo.getCommunityId());
        List<UnitDto> unitDtos = unitInnerServiceSMOImpl.queryUnits(unitDto);
        Assert.listOnlyOne(unitDtos, "未包含单元信息");


        JSONObject body = new JSONObject();
        body.put("floorNum", floorPo.getFloorNum());
        body.put("floorName", floorPo.getName());
        body.put("floorType", MappingCache.getValue(HcGovConstant.GOV_DOMAIN,HcGovConstant.FLOOR_TYPE));
        body.put("floorArea", floorPo.getFloorArea());
        body.put("layerCount", unitDtos.get(0).getLayerCount());
        body.put("unitCount", unitDtos.size());
        body.put("floorUse", tmpCommunityDto.getName()+"_住宅");
        body.put("personName", "HC小区管理系统");
        body.put("personLink", "18909711234");

        JSONObject heard = new JSONObject();
        heard.put("serviceCode",HcGovConstant.ADD_FLOOR_ACTION);
        heard.put("extCommunityId",extCommunityId);
        heard.put("tranId", PayUtil.makeUUID(15));
        heard.put("reqTime",DateUtil.getNow(DateUtil.DATE_FORMATE_STRING_A));
        HcGovConstant.generatorProducerSign(heard,body,HcGovConstant.COMMUNITY_SECURE);
        JSONObject kafkaData = new JSONObject();
        kafkaData.put("header",heard);
        kafkaData.put("body",body);
        try {
            KafkaFactory.sendKafkaMessage(HcGovConstant.GOV_TOPIC,kafkaData.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
