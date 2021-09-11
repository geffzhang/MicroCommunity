package com.java110.dev.cmd;

import com.alibaba.fastjson.JSONObject;
import com.java110.core.annotation.Java110Cmd;
import com.java110.core.context.ICmdDataFlowContext;
import com.java110.core.event.cmd.AbstractServiceCmdListener;
import com.java110.core.event.cmd.CmdEvent;
import com.java110.utils.exception.CmdException;

/**
 * 保存编码映射处理类
 */
@Java110Cmd(serviceCode = "mapping.saveMapping")
public class SaveMappingCmd extends AbstractServiceCmdListener {

    @Override
    protected void validate(CmdEvent event, ICmdDataFlowContext cmdDataFlowContext, JSONObject reqJson) {

    }

    @Override
    protected void doCmd(CmdEvent event, ICmdDataFlowContext cmdDataFlowContext, JSONObject reqJson) throws CmdException {

    }
}
