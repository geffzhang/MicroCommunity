package com.java110.user.dao;


import com.java110.utils.exception.DAOException;


import java.util.List;
import java.util.Map;

/**
 * 租赁预约组件内部之间使用，没有给外围系统提供服务能力
 * 租赁预约服务接口类，要求全部以字符串传输，方便微服务化
 * 新建客户，修改客户，删除客户，查询客户等功能
 *
 * Created by wuxw on 2016/12/27.
 */
public interface IRentingAppointmentServiceDao {


    /**
     * 保存 租赁预约信息
     * @param info
     * @throws DAOException DAO异常
     */
    void saveRentingAppointmentInfo(Map info) throws DAOException;




    /**
     * 查询租赁预约信息（instance过程）
     * 根据bId 查询租赁预约信息
     * @param info bId 信息
     * @return 租赁预约信息
     * @throws DAOException DAO异常
     */
    List<Map> getRentingAppointmentInfo(Map info) throws DAOException;



    /**
     * 修改租赁预约信息
     * @param info 修改信息
     * @throws DAOException DAO异常
     */
    void updateRentingAppointmentInfo(Map info) throws DAOException;


    /**
     * 查询租赁预约总数
     *
     * @param info 租赁预约信息
     * @return 租赁预约数量
     */
    int queryRentingAppointmentsCount(Map info);

}
