package com.java110.user.cmd.owner;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.java110.core.annotation.Java110Cmd;
import com.java110.core.annotation.Java110Transactional;
import com.java110.core.context.ICmdDataFlowContext;
import com.java110.core.event.cmd.Cmd;
import com.java110.core.event.cmd.CmdEvent;
import com.java110.core.factory.AuthenticationFactory;
import com.java110.core.factory.GenerateCodeFactory;
import com.java110.core.factory.SendSmsFactory;
import com.java110.core.smo.IPhotoSMO;
import com.java110.dto.community.CommunityDto;
import com.java110.dto.msg.SmsDto;
import com.java110.dto.owner.OwnerDto;
import com.java110.intf.common.IFileInnerServiceSMO;
import com.java110.intf.common.IFileRelInnerServiceSMO;
import com.java110.intf.common.ISmsInnerServiceSMO;
import com.java110.intf.community.ICommunityInnerServiceSMO;
import com.java110.intf.community.ICommunityV1InnerServiceSMO;
import com.java110.intf.user.*;
import com.java110.po.owner.OwnerAppUserPo;
import com.java110.po.owner.OwnerAttrPo;
import com.java110.po.owner.OwnerPo;
import com.java110.po.user.UserPo;
import com.java110.utils.cache.MappingCache;
import com.java110.utils.constant.MappingConstant;
import com.java110.utils.constant.UserLevelConstant;
import com.java110.utils.exception.CmdException;
import com.java110.utils.util.Assert;
import com.java110.utils.util.BeanConvertUtil;
import com.java110.utils.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.List;

/**
 * 房屋添加业主
 */
@Java110Cmd(serviceCode = "owner.saveRoomOwner")
public class SaveRoomOwnerCmd extends Cmd {


    @Autowired
    private IOwnerInnerServiceSMO ownerInnerServiceSMOImpl;

    @Autowired
    private IFileRelInnerServiceSMO fileRelInnerServiceSMOImpl;

    @Autowired
    private IFileInnerServiceSMO fileInnerServiceSMOImpl;

    @Autowired
    private IOwnerV1InnerServiceSMO ownerV1InnerServiceSMOImpl;

    @Autowired
    private IOwnerAttrInnerServiceSMO ownerAttrInnerServiceSMOImpl;


    @Autowired
    private ICommunityV1InnerServiceSMO communityV1InnerServiceSMOImpl;

    @Autowired
    private IPhotoSMO photoSMOImpl;

    @Autowired
    private IOwnerAppUserV1InnerServiceSMO ownerAppUserV1InnerServiceSMOImpl;

    @Autowired
    private ICommunityInnerServiceSMO communityInnerServiceSMOImpl;
    @Autowired
    private IUserV1InnerServiceSMO userV1InnerServiceSMOImpl;


    @Override
    public void validate(CmdEvent event, ICmdDataFlowContext context, JSONObject reqJson) throws CmdException, ParseException {
        Assert.jsonObjectHaveKey(reqJson, "name", "请求报文中未包含name");
        Assert.jsonObjectHaveKey(reqJson, "userId", "请求报文中未包含userId");
        Assert.jsonObjectHaveKey(reqJson, "link", "请求报文中未包含link");
        Assert.jsonObjectHaveKey(reqJson, "roomName", "请求报文中未包含房屋");

        Assert.jsonObjectHaveKey(reqJson, "communityId", "请求报文中未包含communityId");

        //todo 校验手机号重复
        String userValidate = MappingCache.getValue("USER_VALIDATE");
        if ("ON".equals(userValidate)) {
            String link = reqJson.getString("link");
            OwnerDto ownerDto = new OwnerDto();
            ownerDto.setLink(link);
            ownerDto.setCommunityId(reqJson.getString("communityId"));
            List<OwnerDto> ownerDtos = ownerInnerServiceSMOImpl.queryAllOwners(ownerDto);
            Assert.listIsNull(ownerDtos, "手机号重复，请重新输入");
        }

        //todo 属性校验
        Assert.judgeAttrValue(reqJson);

    }

    @Override
    @Java110Transactional
    public void doCmd(CmdEvent event, ICmdDataFlowContext context, JSONObject reqJson) throws CmdException, ParseException {

        //todo 生成memberId
        generateMemberId(reqJson);

        JSONObject businessOwner = new JSONObject();
        businessOwner.putAll(reqJson);
        businessOwner.put("state", "2000");
        OwnerPo ownerPo = BeanConvertUtil.covertBean(businessOwner, OwnerPo.class);
        if (reqJson.containsKey("age") && StringUtil.isEmpty(reqJson.getString("age"))) {
            ownerPo.setAge(null);
        }
        int flag = ownerV1InnerServiceSMOImpl.saveOwner(ownerPo);
        if (flag < 1) {
            throw new CmdException("保存业主失败");
        }

        //保存照片
        photoSMOImpl.savePhoto(reqJson.getString("ownerPhoto"),
                reqJson.getString("memberId"),
                reqJson.getString("communityId"),
                "10000");

        dealOwnerAttr(reqJson, context);

        String autoUser = MappingCache.getValue(MappingConstant.DOMAIN_SYSTEM_SWITCH, "AUTO_GENERATOR_OWNER_USER");

        if (!"ON".equals(autoUser)) {
            return;
        }

        CommunityDto communityDto = new CommunityDto();
        communityDto.setCommunityId(ownerPo.getCommunityId());
        List<CommunityDto> communityDtos = communityInnerServiceSMOImpl.queryCommunitys(communityDto);
        Assert.listNotNull(communityDtos, "未包含小区信息");
        CommunityDto tmpCommunityDto = communityDtos.get(0);

        UserPo userPo = new UserPo();
        userPo.setUserId(GenerateCodeFactory.getUserId());
        userPo.setName(ownerPo.getName());
        userPo.setTel(ownerPo.getLink());
        userPo.setPassword(AuthenticationFactory.passwdMd5(ownerPo.getLink()));
        userPo.setLevelCd(UserLevelConstant.USER_LEVEL_ORDINARY);
        userPo.setAge(ownerPo.getAge());
        userPo.setAddress(ownerPo.getAddress());
        userPo.setSex(ownerPo.getSex());
        flag = userV1InnerServiceSMOImpl.saveUser(userPo);
        if (flag < 1) {
            throw new CmdException("注册失败");
        }

        OwnerAppUserPo ownerAppUserPo = new OwnerAppUserPo();
        //状态类型，10000 审核中，12000 审核成功，13000 审核失败
        ownerAppUserPo.setState("12000");
        ownerAppUserPo.setAppTypeCd("10010");
        ownerAppUserPo.setAppUserId(GenerateCodeFactory.getGeneratorId(GenerateCodeFactory.CODE_PREFIX_appUserId));
        ownerAppUserPo.setMemberId(ownerPo.getMemberId());
        ownerAppUserPo.setCommunityName(tmpCommunityDto.getName());
        ownerAppUserPo.setCommunityId(ownerPo.getCommunityId());
        ownerAppUserPo.setAppUserName(ownerPo.getName());
        ownerAppUserPo.setIdCard(ownerPo.getIdCard());
        ownerAppUserPo.setAppType("WECHAT");
        ownerAppUserPo.setLink(ownerPo.getLink());
        ownerAppUserPo.setUserId(userPo.getUserId());
        ownerAppUserPo.setOpenId("-1");

        flag = ownerAppUserV1InnerServiceSMOImpl.saveOwnerAppUser(ownerAppUserPo);
        if (flag < 1) {
            throw new CmdException("添加用户业主关系失败");
        }

    }


    /**
     * 生成小区楼ID
     *
     * @param paramObj 请求入参数据
     */
    private void generateMemberId(JSONObject paramObj) {
        String memberId = GenerateCodeFactory.getGeneratorId(GenerateCodeFactory.CODE_PREFIX_ownerId);
        paramObj.put("memberId", memberId);

    }

    private void dealOwnerAttr(JSONObject paramObj, ICmdDataFlowContext cmdDataFlowContext) {

        if (!paramObj.containsKey("attrs")) {
            return;
        }

        JSONArray attrs = paramObj.getJSONArray("attrs");
        if (attrs.size() < 1) {
            return;
        }

        int flag = 0;
        JSONObject attr = null;
        for (int attrIndex = 0; attrIndex < attrs.size(); attrIndex++) {
            attr = attrs.getJSONObject(attrIndex);
            attr.put("communityId", paramObj.getString("communityId"));
            attr.put("memberId", paramObj.getString("memberId"));
            attr.put("attrId", GenerateCodeFactory.getGeneratorId(GenerateCodeFactory.CODE_PREFIX_attrId));
            OwnerAttrPo ownerAttrPo = BeanConvertUtil.covertBean(attr, OwnerAttrPo.class);
            flag = ownerAttrInnerServiceSMOImpl.saveOwnerAttr(ownerAttrPo);
            if (flag < 1) {
                throw new CmdException("保存业主房屋关系失败");
            }
        }

    }
}
