package com.java110.user.dao.impl;

import com.alibaba.fastjson.JSONObject;
import com.java110.core.base.dao.BaseServiceDao;
import com.java110.user.dao.IQuestionAnswerTitleValueServiceDao;
import com.java110.utils.constant.ResponseConstant;
import com.java110.utils.exception.DAOException;
import org.slf4j.Logger;
import com.java110.core.log.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 答卷选项服务 与数据库交互
 * Created by wuxw on 2017/4/5.
 */
@Service("questionAnswerTitleValueServiceDaoImpl")
//@Transactional
public class QuestionAnswerTitleValueServiceDaoImpl extends BaseServiceDao implements IQuestionAnswerTitleValueServiceDao {

    private static Logger logger = LoggerFactory.getLogger(QuestionAnswerTitleValueServiceDaoImpl.class);


    /**
     * 保存答卷选项信息 到 instance
     *
     * @param info bId 信息
     * @throws DAOException DAO异常
     */
    @Override
    public void saveQuestionAnswerTitleValueInfo(Map info) throws DAOException {
        logger.debug("保存答卷选项信息Instance 入参 info : {}", info);

        int saveFlag = sqlSessionTemplate.insert("questionAnswerTitleValueServiceDaoImpl.saveQuestionAnswerTitleValueInfo", info);

        if (saveFlag < 1) {
            throw new DAOException(ResponseConstant.RESULT_PARAM_ERROR, "保存答卷选项信息Instance数据失败：" + JSONObject.toJSONString(info));
        }
    }


    /**
     * 查询答卷选项信息（instance）
     *
     * @param info bId 信息
     * @return List<Map>
     * @throws DAOException DAO异常
     */
    @Override
    public List<Map> getQuestionAnswerTitleValueInfo(Map info) throws DAOException {
        logger.debug("查询答卷选项信息 入参 info : {}", info);

        List<Map> businessQuestionAnswerTitleValueInfos = sqlSessionTemplate.selectList("questionAnswerTitleValueServiceDaoImpl.getQuestionAnswerTitleValueInfo", info);

        return businessQuestionAnswerTitleValueInfos;
    }


    /**
     * 修改答卷选项信息
     *
     * @param info 修改信息
     * @throws DAOException DAO异常
     */
    @Override
    public void updateQuestionAnswerTitleValueInfo(Map info) throws DAOException {
        logger.debug("修改答卷选项信息Instance 入参 info : {}", info);

        int saveFlag = sqlSessionTemplate.update("questionAnswerTitleValueServiceDaoImpl.updateQuestionAnswerTitleValueInfo", info);

        if (saveFlag < 1) {
            throw new DAOException(ResponseConstant.RESULT_PARAM_ERROR, "修改答卷选项信息Instance数据失败：" + JSONObject.toJSONString(info));
        }
    }

    /**
     * 查询答卷选项数量
     *
     * @param info 答卷选项信息
     * @return 答卷选项数量
     */
    @Override
    public int queryQuestionAnswerTitleValuesCount(Map info) {
        logger.debug("查询答卷选项数据 入参 info : {}", info);

        List<Map> businessQuestionAnswerTitleValueInfos = sqlSessionTemplate.selectList("questionAnswerTitleValueServiceDaoImpl.queryQuestionAnswerTitleValuesCount", info);
        if (businessQuestionAnswerTitleValueInfos.size() < 1) {
            return 0;
        }

        return Integer.parseInt(businessQuestionAnswerTitleValueInfos.get(0).get("count").toString());
    }

    @Override
    public List<Map> queryQuestionAnswerTitleValueResult(Map info) {
        List<Map> businessQuestionAnswerTitleValueInfos
                = sqlSessionTemplate.selectList("questionAnswerTitleValueServiceDaoImpl.queryQuestionAnswerTitleValueResult", info);
        return businessQuestionAnswerTitleValueInfos;
    }

    @Override
    public List<Map> queryQuestionAnswerTitleValueResultCount(Map info) {
        List<Map> businessQuestionAnswerTitleValueInfos
                = sqlSessionTemplate.selectList("questionAnswerTitleValueServiceDaoImpl.queryQuestionAnswerTitleValueResultCount", info);
        return businessQuestionAnswerTitleValueInfos;
    }
}
