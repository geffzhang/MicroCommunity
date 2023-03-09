package com.java110.common.charge;

import com.java110.dto.chargeMachine.ChargeMachineDto;
import com.java110.dto.chargeMachineOrder.NotifyChargeOrderDto;
import com.java110.dto.chargeMachinePort.ChargeMachinePortDto;
import com.java110.vo.ResultVo;
import org.springframework.http.ResponseEntity;

/**
 * 充电适配器
 */
public interface IChargeCore {

    public static final String CHARGE_TYPE_HOURS = "hours";
    public static final String CHARGE_TYPE_ENERGY = "energy";

    /**
     * 开始充电接口
     * @param chargeMachineDto
     * @param chargeType
     * @param duration
     * @return
     */
    ResultVo startCharge(ChargeMachineDto chargeMachineDto, ChargeMachinePortDto chargeMachinePortDto, String chargeType, double duration,String orderId);


    /**
     * 结束充电接口
     * @param chargeMachineDto
     * @return
     */
    ResultVo stopCharge(ChargeMachineDto chargeMachineDto, ChargeMachinePortDto chargeMachinePortDto);

    /**
     * 查询充电端口状态
     * @param chargeMachineDto
     * @param chargeMachinePortDto
     * @return
     */
    ChargeMachinePortDto getChargePortState(ChargeMachineDto chargeMachineDto, ChargeMachinePortDto chargeMachinePortDto);

    ResponseEntity<String> finishCharge(NotifyChargeOrderDto notifyChargeOrderDto);

    ResponseEntity<String> heartbeat(NotifyChargeOrderDto notifyChargeOrderDto);

    ResponseEntity<String> chargeHeartBeat(NotifyChargeOrderDto notifyChargeOrderDto);
}
