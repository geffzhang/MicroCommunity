package com.java110.user.bmo.rentingPoolFlow;

import com.java110.po.renting.RentingPoolFlowPo;
import org.springframework.http.ResponseEntity;

public interface IUpdateRentingPoolFlowBMO {


    /**
     * 修改出租流程
     * add by wuxw
     *
     * @param rentingPoolFlowPo
     * @return
     */
    ResponseEntity<String> update(RentingPoolFlowPo rentingPoolFlowPo);


}
