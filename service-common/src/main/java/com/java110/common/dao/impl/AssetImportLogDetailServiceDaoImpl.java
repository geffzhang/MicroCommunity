package com.java110.common.dao.impl;

import com.alibaba.fastjson.JSONObject;
import com.java110.common.dao.IAssetImportLogDetailServiceDao;
import com.java110.core.base.dao.BaseServiceDao;
import com.java110.utils.constant.ResponseConstant;
import com.java110.utils.exception.DAOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 批量操作日志详情服务 与数据库交互
 * Created by wuxw on 2017/4/5.
 */
@Service("assetImportLogDetailServiceDaoImpl")
//@Transactional
public class AssetImportLogDetailServiceDaoImpl extends BaseServiceDao implements IAssetImportLogDetailServiceDao {

    private static Logger logger = LoggerFactory.getLogger(AssetImportLogDetailServiceDaoImpl.class);


    /**
     * 保存批量操作日志详情信息 到 instance
     *
     * @param info bId 信息
     * @throws DAOException DAO异常
     */
    @Override
    public void saveAssetImportLogDetailInfo(Map info) throws DAOException {
        logger.debug("保存批量操作日志详情信息Instance 入参 info : {}", info);

        int saveFlag = sqlSessionTemplate.insert("assetImportLogDetailServiceDaoImpl.saveAssetImportLogDetailInfo", info);

        if (saveFlag < 1) {
            throw new DAOException(ResponseConstant.RESULT_PARAM_ERROR, "保存批量操作日志详情信息Instance数据失败：" + JSONObject.toJSONString(info));
        }
    }


    /**
     * 查询批量操作日志详情信息（instance）
     *
     * @param info bId 信息
     * @return List<Map>
     * @throws DAOException DAO异常
     */
    @Override
    public List<Map> getAssetImportLogDetailInfo(Map info) throws DAOException {
        logger.debug("查询批量操作日志详情信息 入参 info : {}", info);

        List<Map> businessAssetImportLogDetailInfos = sqlSessionTemplate.selectList("assetImportLogDetailServiceDaoImpl.getAssetImportLogDetailInfo", info);

        return businessAssetImportLogDetailInfos;
    }


    /**
     * 修改批量操作日志详情信息
     *
     * @param info 修改信息
     * @throws DAOException DAO异常
     */
    @Override
    public void updateAssetImportLogDetailInfo(Map info) throws DAOException {
        logger.debug("修改批量操作日志详情信息Instance 入参 info : {}", info);

        int saveFlag = sqlSessionTemplate.update("assetImportLogDetailServiceDaoImpl.updateAssetImportLogDetailInfo", info);

        if (saveFlag < 1) {
            throw new DAOException(ResponseConstant.RESULT_PARAM_ERROR, "修改批量操作日志详情信息Instance数据失败：" + JSONObject.toJSONString(info));
        }
    }

    /**
     * 查询批量操作日志详情数量
     *
     * @param info 批量操作日志详情信息
     * @return 批量操作日志详情数量
     */
    @Override
    public int queryAssetImportLogDetailsCount(Map info) {
        logger.debug("查询批量操作日志详情数据 入参 info : {}", info);

        List<Map> businessAssetImportLogDetailInfos = sqlSessionTemplate.selectList("assetImportLogDetailServiceDaoImpl.queryAssetImportLogDetailsCount", info);
        if (businessAssetImportLogDetailInfos.size() < 1) {
            return 0;
        }

        return Integer.parseInt(businessAssetImportLogDetailInfos.get(0).get("count").toString());
    }

    @Override
    public int saveAssetImportLogDetails(Map info) {
        int saveFlag = sqlSessionTemplate.insert("assetImportLogDetailServiceDaoImpl.saveAssetImportLogDetails", info);
        return saveFlag;
    }


}
