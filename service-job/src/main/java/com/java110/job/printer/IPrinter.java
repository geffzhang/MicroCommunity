package com.java110.job.printer;

import com.java110.dto.machinePrinter.MachinePrinterDto;
import com.java110.vo.ResultVo;

/**
 * 打印 接口类
 *
 * 云打印机厂家 需要实现
 *
 */
public interface IPrinter {

    /**
     * 打印交费单
     *
     * @param detailIds
     */
    ResultVo printPayFeeDetail(String[] detailIds, String communityId, int quantity,MachinePrinterDto machinePrinterDto,String staffName);

    /**
     * 打印报修
     *
     * @param repairUserId
     */
    ResultVo printRepair(String repairUserId,String communityId,int quantity, MachinePrinterDto machinePrinterDto);
}
