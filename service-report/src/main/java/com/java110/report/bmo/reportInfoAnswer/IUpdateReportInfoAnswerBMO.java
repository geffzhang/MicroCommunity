package com.java110.report.bmo.reportInfoAnswer;
import com.java110.po.reportInfoAnswer.ReportInfoAnswerPo;
import org.springframework.http.ResponseEntity;

public interface IUpdateReportInfoAnswerBMO {


    /**
     * 修改批量操作日志详情
     * add by wuxw
     * @param reportInfoAnswerPo
     * @return
     */
    ResponseEntity<String> update(ReportInfoAnswerPo reportInfoAnswerPo);


}
