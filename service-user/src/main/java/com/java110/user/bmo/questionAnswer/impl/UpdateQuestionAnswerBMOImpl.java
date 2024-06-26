package com.java110.user.bmo.questionAnswer.impl;

import com.java110.core.annotation.Java110Transactional;
import com.java110.core.factory.GenerateCodeFactory;
import com.java110.dto.file.FileRelDto;
import com.java110.intf.common.IFileRelInnerServiceSMO;
import com.java110.intf.user.IQuestionAnswerInnerServiceSMO;
import com.java110.po.file.FileRelPo;
import com.java110.po.questionAnswer.QuestionAnswerPo;
import com.java110.user.bmo.questionAnswer.IUpdateQuestionAnswerBMO;
import com.java110.utils.exception.CmdException;
import com.java110.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("updateQuestionAnswerBMOImpl")
public class UpdateQuestionAnswerBMOImpl implements IUpdateQuestionAnswerBMO {

    @Autowired
    private IQuestionAnswerInnerServiceSMO questionAnswerInnerServiceSMOImpl;

    @Autowired
    private IFileRelInnerServiceSMO fileRelInnerServiceSMOImpl;

    /**
     * @param questionAnswerPo
     * @return 订单服务能够接受的报文
     */
    @Java110Transactional
    public ResponseEntity<String> update(QuestionAnswerPo questionAnswerPo) {

        int flag = questionAnswerInnerServiceSMOImpl.updateQuestionAnswer(questionAnswerPo);

        return ResultVo.createResponseEntity(ResultVo.CODE_ERROR, "保存失败");
    }
}