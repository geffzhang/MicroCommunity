package com.java110.fee.bmo.feeManualCollection;

import com.java110.po.fee.FeeManualCollectionPo;
import org.springframework.http.ResponseEntity;

public interface IDeleteFeeManualCollectionBMO {


    /**
     * 修改人工托收
     * add by wuxw
     *
     * @param feeManualCollectionPo
     * @return
     */
    ResponseEntity<String> delete(FeeManualCollectionPo feeManualCollectionPo);


}
