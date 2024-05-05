package com.java110.store.bmo.contractRoom;
import com.java110.po.contract.ContractRoomPo;
import org.springframework.http.ResponseEntity;

public interface IUpdateContractRoomBMO {


    /**
     * 修改合同房屋
     * add by wuxw
     * @param contractRoomPo
     * @return
     */
    ResponseEntity<String> update(ContractRoomPo contractRoomPo);


}
