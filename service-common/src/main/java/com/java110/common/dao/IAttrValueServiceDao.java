package com.java110.common.dao;


import com.java110.utils.exception.DAOException;


import java.util.List;
import java.util.Map;

/**
 * 属性值组件内部之间使用，没有给外围系统提供服务能力
 * 属性值服务接口类，要求全部以字符串传输，方便微服务化
 * 新建客户，修改客户，删除客户，查询客户等功能
 *
 * Created by wuxw on 2016/12/27.
 */
public interface IAttrValueServiceDao {


    /**
     * 保存 属性值信息
     * @param info
     * @throws DAOException DAO异常
     */
    void saveAttrValueInfo(Map info) throws DAOException;




    /**
     * 查询属性值信息（instance过程）
     * 根据bId 查询属性值信息
     * @param info bId 信息
     * @return 属性值信息
     * @throws DAOException DAO异常
     */
    List<Map> getAttrValueInfo(Map info) throws DAOException;



    /**
     * 修改属性值信息
     * @param info 修改信息
     * @throws DAOException DAO异常
     */
    void updateAttrValueInfo(Map info) throws DAOException;


    /**
     * 查询属性值总数
     *
     * @param info 属性值信息
     * @return 属性值数量
     */
    int queryAttrValuesCount(Map info);

}
