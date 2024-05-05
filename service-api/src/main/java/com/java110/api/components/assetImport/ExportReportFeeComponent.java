package com.java110.api.components.assetImport;

import com.java110.api.smo.assetExport.IExportReportFeeSMO;
import com.java110.core.context.IPageData;
import com.java110.core.context.PageData;
import com.java110.dto.app.AppDto;
import org.slf4j.Logger;
import com.java110.core.log.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * 添加应用组件
 */
@Component("exportReportFee")
public class ExportReportFeeComponent {

    private final static Logger logger = LoggerFactory.getLogger(ExportReportFeeComponent.class);

    @Autowired
    private IExportReportFeeSMO exportExcelData;

    /**
     * 添加应用数据
     *
     * @param pd 页面数据封装
     * @return ResponseEntity 对象
     */
    public ResponseEntity<Object> exportData(IPageData pd) throws Exception {
        IPageData newPd = PageData.newInstance().builder(pd.getUserId(), pd.getUserName(), pd.getToken(), pd.getReqData(), pd.getComponentCode(), pd.getComponentMethod(), "",
                pd.getSessionId(), AppDto.WEB_APP_ID, pd.getHeaders());
        return exportExcelData.exportExcelData(newPd);
    }

}
