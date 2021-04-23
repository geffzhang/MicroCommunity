package com.java110.front.smo.inspection;

import com.java110.core.context.IPageData;
import org.springframework.http.ResponseEntity;

/**
 * 修改巡检计划接口
 *
 * add by wuxw 2019-06-30
 */
public interface IEditInspectionPlanSMO {

    /**
     * 修改小区
     * @param pd 页面数据封装
     * @return ResponseEntity 对象
     */
    ResponseEntity<String> updateInspectionPlan(IPageData pd);
}
