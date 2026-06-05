package org.example.mapper;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.example.dto.AdminDTO.OrderManageDTO;
import org.example.entity.ServiceOrder;

@Mapper
public interface AdminOrderManageMapper {

    /**
     * 工单管理分页查询
     * @param orderManageDTO
     * @return
     */
    Page<ServiceOrder> getData(OrderManageDTO orderManageDTO);

    /**
     * 根据id查询工单详细信息
     * @param id
     * @return
     */
    ServiceOrder getOrderDetailById(Long id);

    /**
     * 动态更新数据库
     * @param order
     */
    void update(ServiceOrder order);

    /**
     * 插入数据库
     * @param order
     */
    void insert(ServiceOrder order);

    /**
     * 用户端分页查询工单分类
     * @param userId
     * @param status
     * @return
     */
    Page<ServiceOrder> getDataByUserIdAndStatus(Long userId, String status);
}
