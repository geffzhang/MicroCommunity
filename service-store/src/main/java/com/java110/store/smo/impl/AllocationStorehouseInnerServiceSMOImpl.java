package com.java110.store.smo.impl;


import com.java110.core.base.smo.BaseServiceSMO;
import com.java110.dto.PageDto;
import com.java110.dto.file.FileRelDto;
import com.java110.dto.purchase.AllocationStorehouseDto;
import com.java110.dto.resource.ResourceStoreDto;
import com.java110.dto.resource.ResourceStoreTimesDto;
import com.java110.dto.user.UserDto;
import com.java110.intf.store.IAllocationStorehouseInnerServiceSMO;
import com.java110.intf.store.IResourceStoreTimesV1InnerServiceSMO;
import com.java110.store.dao.IAllocationStorehouseServiceDao;
import com.java110.utils.util.BeanConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName FloorInnerServiceSMOImpl
 * @Description 仓库调拨内部服务实现类
 * @Author wuxw
 * @Date 2019/4/24 9:20
 * @Version 1.0
 * add by wuxw 2019/4/24
 **/
@RestController
public class AllocationStorehouseInnerServiceSMOImpl extends BaseServiceSMO implements IAllocationStorehouseInnerServiceSMO {

    @Autowired
    private IAllocationStorehouseServiceDao allocationAllocationStorehousehouseServiceDaoImpl;

    @Autowired
    private IResourceStoreTimesV1InnerServiceSMO resourceStoreTimesV1InnerServiceSMOImpl;


    @Override
    public List<AllocationStorehouseDto> queryAllocationStorehouses(@RequestBody AllocationStorehouseDto allocationAllocationStorehousehouseDto) {

        //校验是否传了 分页信息

        int page = allocationAllocationStorehousehouseDto.getPage();

        if (page != PageDto.DEFAULT_PAGE) {
            allocationAllocationStorehousehouseDto.setPage((page - 1) * allocationAllocationStorehousehouseDto.getRow());
        }

        List<AllocationStorehouseDto> allocationStorehouseDtos = BeanConvertUtil.covertBeanList(
                allocationAllocationStorehousehouseServiceDaoImpl.getAllocationStorehouseInfo(BeanConvertUtil.beanCovertMap(allocationAllocationStorehousehouseDto)),
                AllocationStorehouseDto.class);

        if(allocationStorehouseDtos == null || allocationStorehouseDtos.size() < 1){
            return allocationStorehouseDtos;
        }

        //todo 计算times
        for (AllocationStorehouseDto allocationStorehouseDto : allocationStorehouseDtos) {
            ResourceStoreTimesDto resourceStoreTimesDto = new ResourceStoreTimesDto();
            resourceStoreTimesDto.setStoreId(allocationStorehouseDto.getStoreId());
            resourceStoreTimesDto.setResCode(allocationStorehouseDto.getResCode());
            resourceStoreTimesDto.setShId(allocationStorehouseDto.getShIda());
            List<ResourceStoreTimesDto> resourceStoreTimesDtos = resourceStoreTimesV1InnerServiceSMOImpl.queryResourceStoreTimess(resourceStoreTimesDto);
            allocationStorehouseDto.setTimes(resourceStoreTimesDtos);
        }


        return allocationStorehouseDtos;
    }

    /**
     * 从用户列表中查询用户，将用户中的信息 刷新到 floor对象中
     *
     * @param allocationAllocationStorehousehouse 小区仓库调拨信息
     * @param users                               用户列表
     */
    private void refreshAllocationStorehouse(AllocationStorehouseDto allocationAllocationStorehousehouse, List<UserDto> users) {
        for (UserDto user : users) {
            if (allocationAllocationStorehousehouse.getAsId().equals(user.getUserId())) {
                BeanConvertUtil.covertBean(user, allocationAllocationStorehousehouse);
            }
        }
    }

    /**
     * 获取批量userId
     *
     * @param allocationAllocationStorehousehouses 小区楼信息
     * @return 批量userIds 信息
     */
    private String[] getUserIds(List<AllocationStorehouseDto> allocationAllocationStorehousehouses) {
        List<String> userIds = new ArrayList<String>();
        for (AllocationStorehouseDto allocationAllocationStorehousehouse : allocationAllocationStorehousehouses) {
            userIds.add(allocationAllocationStorehousehouse.getAsId());
        }

        return userIds.toArray(new String[userIds.size()]);
    }

    @Override
    public int queryAllocationStorehousesCount(@RequestBody AllocationStorehouseDto allocationAllocationStorehousehouseDto) {
        return allocationAllocationStorehousehouseServiceDaoImpl.queryAllocationStorehousesCount(BeanConvertUtil.beanCovertMap(allocationAllocationStorehousehouseDto));
    }

    /**
     * 新增调拨记录
     *
     * @param allocationStorehouseDto
     */
    @Override
    public void saveAllocationStorehouses(@RequestBody AllocationStorehouseDto allocationStorehouseDto) {
        allocationAllocationStorehousehouseServiceDaoImpl.saveAllocationStorehouseInfo(BeanConvertUtil.beanCovertMap(allocationStorehouseDto));
    }

    public IAllocationStorehouseServiceDao getAllocationStorehouseServiceDaoImpl() {
        return allocationAllocationStorehousehouseServiceDaoImpl;
    }

    public void setAllocationStorehouseServiceDaoImpl(IAllocationStorehouseServiceDao allocationAllocationStorehousehouseServiceDaoImpl) {
        this.allocationAllocationStorehousehouseServiceDaoImpl = allocationAllocationStorehousehouseServiceDaoImpl;
    }

}
