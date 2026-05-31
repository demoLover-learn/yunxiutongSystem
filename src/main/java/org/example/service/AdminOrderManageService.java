package org.example.service;

import org.example.Result.PageResult;
import org.example.dto.OrderManageDTO;

import org.example.entity.ServiceOrder;


public interface AdminOrderManageService {
    /**
     * 工单管理分页查询
     * @param orderManageDTO
     * @return
     */
    PageResult pageQuery(OrderManageDTO orderManageDTO);
    /**
     * 获取订单的详细信息
     * @param id
     * @return
     */
    ServiceOrder getOrderDetailById(Long id);
    /**
     * 派单接口
     * @param id
     */
    void getOrder(Long id, Long workId);

    /**
     * 取消订单
     * @param id
     */
    void cancelOrder(Long id);
}
