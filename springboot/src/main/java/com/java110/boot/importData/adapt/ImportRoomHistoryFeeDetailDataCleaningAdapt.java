package com.java110.boot.importData.adapt;

import com.alibaba.fastjson.JSONObject;
import com.java110.boot.importData.DefaultImportDataAdapt;
import com.java110.boot.importData.IImportDataCleaningAdapt;
import com.java110.core.factory.GenerateCodeFactory;
import com.java110.dto.fee.FeeDto;
import com.java110.dto.importData.ImportRoomFee;
import com.java110.dto.payFee.PayFeeBatchDto;
import com.java110.dto.system.ComponentValidateResult;
import com.java110.dto.user.UserDto;
import com.java110.intf.fee.IPayFeeBatchV1InnerServiceSMO;
import com.java110.intf.user.IUserInnerServiceSMO;
import com.java110.po.payFee.PayFeeBatchPo;
import com.java110.utils.util.Assert;
import com.java110.utils.util.DateUtil;
import com.java110.utils.util.ImportExcelUtils;
import com.java110.utils.util.StringUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 历史缴费数据导入
 * <p>
 * 导入请求参数中必须包含
 * param.append('importAdapt', "importRoomHistoryFeeDetail");
 * <p>
 * vc.http.upload(
 * 'assetImport',
 * 'importData',
 * param, {
 * emulateJSON: true,
 * //添加请求头
 * headers: {
 * "Content-Type": "multipart/form-data"
 * }
 * },
 * function(json, res) {
 * //vm.menus = vm.refreshMenuActive(JSON.parse(json),0);
 * let _json = JSON.parse(json);
 * if (_json.code == 0) {
 * //关闭model
 * // vc.toast(_json.data);
 * $('#importOwnerRoomModel').modal('hide');
 * $that.clearAddFeeConfigInfo();
 * <p>
 * vc.jumpToPage('/#/pages/property/assetImportLogDetail?logId=' + _json.data.logId + '&logType=importRoomHistoryFeeDetail');
 * return;
 * }
 * vc.toast(_json.msg, 10000);
 * },
 * function(errInfo, error) {
 * console.log('请求失败处理');
 * vc.toast(errInfo, 10000);
 * });
 */
@Service("importRoomHistoryFeeDetailDataCleaning")
public class ImportRoomHistoryFeeDetailDataCleaningAdapt extends DefaultImportDataAdapt implements IImportDataCleaningAdapt {


    @Autowired
    private IPayFeeBatchV1InnerServiceSMO payFeeBatchV1InnerServiceSMOImpl;

    @Autowired
    private IUserInnerServiceSMO userInnerServiceSMOImpl;


    @Override
    public List analysisExcel(Workbook workbook, JSONObject paramIn, ComponentValidateResult result) throws Exception {

        generatorBatch(paramIn);
        List<ImportRoomFee> rooms = new ArrayList<ImportRoomFee>();
        //获取楼信息
        getRooms(workbook, rooms);

        for (ImportRoomFee importRoomFee : rooms) {
            importRoomFee.setBatchId(paramIn.getString("batchId"));
            importRoomFee.setUserId(paramIn.getString("userId"));
            importRoomFee.setStoreId(paramIn.getString("storeId"));
            importRoomFee.setObjType(paramIn.getString("objType"));
            importRoomFee.setCommunityId(paramIn.getString("communityId"));
        }
        return rooms;
    }

    /**
     * 获取 房屋信息
     *
     * @param workbook
     * @param rooms
     */
    private void getRooms(Workbook workbook, List<ImportRoomFee> rooms) {
        Sheet sheet = null;
        sheet = ImportExcelUtils.getSheet(workbook, "房屋缴费历史");
        List<Object[]> oList = ImportExcelUtils.listFromSheet(sheet);
        ImportRoomFee importRoomFee = null;
        for (int osIndex = 0; osIndex < oList.size(); osIndex++) {
            Object[] os = oList.get(osIndex);
            if (osIndex == 0) { // 第一行是 头部信息 直接跳过
                continue;
            }
            if (StringUtil.isNullOrNone(os[0])) {
                continue;
            }

            //费用名称没有填写，默认跳过
            if (StringUtil.isNullOrNone(os[4])) {
                continue;
            }
            Assert.hasValue(os[0], (osIndex + 1) + "行楼栋编号不能为空");
            Assert.hasValue(os[1], (osIndex + 1) + "行单元编号不能为空");
            Assert.hasValue(os[2], (osIndex + 1) + "行房屋编号不能为空");
            Assert.hasValue(os[3], (osIndex + 1) + "行收费项目不能为空");
            Assert.hasValue(os[4], (osIndex + 1) + "行缴费周期不能为空");
            Assert.hasValue(os[5], (osIndex + 1) + "行开始时间不能为空");
            Assert.hasValue(os[6], (osIndex + 1) + "行结束时间不能为空");
            Assert.hasValue(os[7], (osIndex + 1) + "行缴费时间不能为空");
            Assert.hasValue(os[8], (osIndex + 1) + "行缴费金额不能为空");

//

            String startTime = excelDoubleToDate(os[5].toString());
            String endTime = excelDoubleToDate(os[6].toString());
            String createTime = excelDoubleToDate(os[7].toString());
            Assert.isDate(startTime, DateUtil.DATE_FORMATE_STRING_B, (osIndex + 1) + "行开始时间格式错误 请填写YYYY-MM-DD 文本格式");
            Assert.isDate(endTime, DateUtil.DATE_FORMATE_STRING_B, (osIndex + 1) + "行结束时间格式错误 请填写YYYY-MM-DD 文本格式");
            Assert.isDate(createTime, DateUtil.DATE_FORMATE_STRING_B, (osIndex + 1) + "行结束时间格式错误 请填写YYYY-MM-DD 文本格式");


            importRoomFee = new ImportRoomFee();
            importRoomFee.setFloorNum(os[0].toString());
            importRoomFee.setUnitNum(os[1].toString());
            importRoomFee.setRoomNum(os[2].toString());
            importRoomFee.setFeeName(os[3].toString());
            importRoomFee.setCycle(os[4].toString());
            importRoomFee.setStartTime(startTime);
            importRoomFee.setEndTime(endTime);
            importRoomFee.setCreateTime(createTime);
            importRoomFee.setAmount(os[8].toString());
            importRoomFee.setRemark(!StringUtil.isNullOrNone(os[9]) ? os[9].toString() : "");
            rooms.add(importRoomFee);
        }
    }

    /**
     * 生成批次号
     *
     * @param reqJson
     */
    private void generatorBatch(JSONObject reqJson) {
        PayFeeBatchPo payFeeBatchPo = new PayFeeBatchPo();
        payFeeBatchPo.setBatchId(GenerateCodeFactory.getGeneratorId("12"));
        payFeeBatchPo.setCommunityId(reqJson.getString("communityId"));
        payFeeBatchPo.setCreateUserId(reqJson.getString("userId"));
        UserDto userDto = new UserDto();
        userDto.setUserId(reqJson.getString("userId"));
        List<UserDto> userDtos = userInnerServiceSMOImpl.getUsers(userDto);

        Assert.listOnlyOne(userDtos, "用户不存在");
        payFeeBatchPo.setCreateUserName(userDtos.get(0).getUserName());
        payFeeBatchPo.setState(PayFeeBatchDto.STATE_NORMAL);
        payFeeBatchPo.setMsg("正常");
        int flag = payFeeBatchV1InnerServiceSMOImpl.savePayFeeBatch(payFeeBatchPo);

        if (flag < 1) {
            throw new IllegalArgumentException("生成批次失败");
        }

        reqJson.put("batchId", payFeeBatchPo.getBatchId());
    }

}
