package org.example.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    //工单状态 0'待接单',1'已接单' 2,'服务中' 3,'已完成' 4,'已取消'
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
    //支付超时时间
    private LocalDateTime payExpireTime;

    // 非数据库字段，仅用于列表/详情展示
    private String userName;         // 用户昵称
    private String userPhone;        // 用户手机号
    private String serviceItemName;  // 服务项目名
    private String workerName;       // 师傅姓名
    private String address;          // 拼接后的完整地址
    private String orderStatusName;  // 状态中文名

}
