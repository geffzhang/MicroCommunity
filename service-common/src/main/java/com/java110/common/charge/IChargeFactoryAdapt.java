package com.java110.common.charge;

import com.java110.dto.chargeMachine.ChargeMachineDto;
import com.java110.dto.chargeMachineOrder.NotifyChargeOrderDto;
import com.java110.dto.chargeMachineOrder.NotifyChargePortDto;
import com.java110.dto.chargeMachinePort.ChargeMachinePortDto;
import com.java110.vo.ResultVo;

import java.util.List;

/**
 * 充电厂家适配器
 */
public interface IChargeFactoryAdapt {

    /**
     * 开始充电
     *
     * @param chargeMachineDto
     * @param chargeMachinePortDto
     * @param chargeType
     * @param duration
     * @return
     */
    ResultVo startCharge(ChargeMachineDto chargeMachineDto, ChargeMachinePortDto chargeMachinePortDto, String chargeType, double duration,String orderId);

    /**
     * 结束充电
     *
     * @param chargeMachineDto
     * @param chargeMachinePortDto
     * @return
     */
    ResultVo stopCharge(ChargeMachineDto chargeMachineDto, ChargeMachinePortDto chargeMachinePortDto);

    /**
     * 查询充电桩状态
     * @param chargeMachineDto
     * @param chargeMachinePortDto
     * @return
     */
    ChargeMachinePortDto getChargePortState(ChargeMachineDto chargeMachineDto, ChargeMachinePortDto chargeMachinePortDto);

    /**
     * 解析充电心跳参数
     * @param notifyChargeOrderDto
     * @return
     */
    List<NotifyChargePortDto> getChargeHeartBeatParam(NotifyChargeOrderDto notifyChargeOrderDto);
}
