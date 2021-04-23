package com.java110.goods.bmo.groupBuyBatch.impl;

import com.java110.core.annotation.Java110Transactional;
import com.java110.goods.bmo.groupBuyBatch.IUpdateGroupBuyBatchBMO;
import com.java110.intf.goods.IGroupBuyBatchInnerServiceSMO;
import com.java110.po.groupBuyBatch.GroupBuyBatchPo;
import com.java110.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service("updateGroupBuyBatchBMOImpl")
public class UpdateGroupBuyBatchBMOImpl implements IUpdateGroupBuyBatchBMO {

    @Autowired
    private IGroupBuyBatchInnerServiceSMO groupBuyBatchInnerServiceSMOImpl;

    /**
     * @param groupBuyBatchPo
     * @return 订单服务能够接受的报文
     */
    @Java110Transactional
    public ResponseEntity<String> update(GroupBuyBatchPo groupBuyBatchPo) {

        int flag = groupBuyBatchInnerServiceSMOImpl.updateGroupBuyBatch(groupBuyBatchPo);

        if (flag > 0) {
            return ResultVo.createResponseEntity(ResultVo.CODE_OK, "保存成功");
        }

        return ResultVo.createResponseEntity(ResultVo.CODE_ERROR, "保存失败");
    }

}
