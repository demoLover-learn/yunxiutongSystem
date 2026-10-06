package org.example.aitools;


import org.example.context.BaseContext;
import org.example.dto.UserDTO.UserOrderDTO;
import org.example.entity.ServiceOrder;
import org.example.mapper.AdminOrderManageMapper;
import org.example.vo.UserOrderVO;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserOrderTools {

    @Autowired
    private  AdminOrderManageMapper orderMapper;



    /**
     * 工具1：用户说"我的订单/最近下了什么单"时调
     */
    @Tool(description = "查询当前登录用户最近的订单列表。" +
            "当用户问'我的订单/我下过的单/最近有什么单'时调用。")
    public List<UserOrderDTO> getMyRecentOrders() {
        Long userId = BaseContext.getCurrentId();   // 登录态里拿，不让 LLM 传

        List<UserOrderVO> orders = orderMapper.listByUserId(userId);
        return orders.stream()
                .map(order->{
                    UserOrderDTO dto = new UserOrderDTO();
                    dto.setOrderNo(order.getOrderNo());
                    dto.setStatusName(order.getOrderStatusName());
                    dto.setServiceItemName(order.getServiceItemName());
                    dto.setAppointmentTime(order.getAppointmentTime());
                    return dto;
                }).collect(Collectors.toList());
    }

    /**
     * 工具2：用户提到具体订单号，问"我那单到哪了/师傅几点来"时调
     */
    @Tool(description = "查询当前登录用户某一笔订单的最新状态。" +
            "当用户提到具体订单号、问'我的单怎么样了/师傅几点到/到哪一步了'时调用。")
    public UserOrderDTO getMyOrder(
            @ToolParam(description = "用户在对话里提到的订单号，形如 YXT 开头") String orderNo) {
        Long userId = BaseContext.getCurrentId();
        ServiceOrder order = orderMapper.getByOrderNo(orderNo);

        if (order == null || !order.getUserId().equals(userId)) {
            throw  new RuntimeException("您还没有下过订单");
        }

        return UserOrderDTO.builder()
                .orderNo(order.getOrderNo())
                .serviceItemName(order.getServiceItemName())
                .workerName(order.getWorkerName())
                .statusName(toStatusName(order.getStatus()))
                .amount(order.getTotalAmount())
                .appointmentTime(order.getAppointmentTime())
                .build();
    }
    private String toStatusName(Integer status) {
     return   switch (status) {
            case 0 ->"待接单";
            case 1->"已接单";
            case 2->"服务中";
            case 3-> "已完成";
            case 4-> "已取消";
            default -> "未知";
        };
    }




}
