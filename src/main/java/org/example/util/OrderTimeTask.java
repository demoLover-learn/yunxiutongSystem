package org.example.util;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.ServiceOrder;
import org.example.mapper.AdminOrderManageMapper;
import org.example.service.OrderStatusService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.util.List;

@Component
@Slf4j
public class OrderTimeTask {
    @Resource
    private AdminOrderManageMapper adminOrderManageMapper;

    @Resource
    private OrderStatusService orderStatusService;

    @Scheduled(cron = "0 */1 * * * ?")
    public void cancelTimeoutOrders() {
        // 查询超过支付时间且仍是待支付的工单
        List<ServiceOrder> orders = adminOrderManageMapper.getTimeoutOrders();

            for (ServiceOrder order : orders) {
                try {
                    order.setCancelReason("支付超时自动取消");
                    orderStatusService.transition(order, 4, "system", null, "支付超时自动取消");
                }catch (Exception e) {
            log.warn("订单{}超时取消失败：{}", order.getId(), e.getMessage());
        }
        }

    }
}
