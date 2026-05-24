package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
//工单主表
public class ServiceOrder {
    //工单id
    private Long id;
    //订单编号
    private String orderNo;
    //用户id
    private Long userId;
    //服务项id
    private Long serviceItemId;
    //服务人员id
    private Long workerId;
    //服务地址id
    private Long addressId;
    //预约上门时间
    private LocalDateTime appointmentTime;
    //工单状态
    private Integer status;
    //支付状态
    private  Integer payStatus;
    //订单金额
    private BigDecimal totalAmount;
    //用户备注
    private String remark;
    //取消原因
    private String cancelReason;
    //下单时间
    private LocalDateTime createTime;
    //支付时间
    private LocalDateTime payTime;
    //接单时间
    private LocalDateTime receiveTime;
    //开始服务时间
    private LocalDateTime startServiceTime;
    //完成服务时间
    private LocalDateTime finishServiceTime;
    //取消服务时间
    private LocalDateTime cancelTime;
    //更新时间
    private LocalDateTime updateTime;

}
