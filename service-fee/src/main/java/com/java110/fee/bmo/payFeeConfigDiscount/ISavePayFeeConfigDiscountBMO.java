package com.java110.fee.bmo.payFeeConfigDiscount;

import com.java110.po.payFee.PayFeeConfigDiscountPo;
import org.springframework.http.ResponseEntity;

public interface ISavePayFeeConfigDiscountBMO {


    /**
     * 添加费用项折扣
     * add by wuxw
     *
     * @param payFeeConfigDiscountPo
     * @return
     */
    ResponseEntity<String> save(PayFeeConfigDiscountPo payFeeConfigDiscountPo);


}
