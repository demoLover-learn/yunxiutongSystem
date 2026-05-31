package org.example.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminOrderManagerVO {
    //工单号
    private String OrderNo;
    //用户名
    private String userName;
    //电话
    private String userPhone;
    //服务人员名称
    private String workerName;
    //服务项目名称
    private String serviceItemName;
    //地址
    private String address;
    //状态
    private String orderStatusName;
    //金额
    private BigDecimal totalAmount;
    //预约时间
    private LocalDateTime appointmentTime;
    //创建时间
    private LocalDateTime createTime;

}
