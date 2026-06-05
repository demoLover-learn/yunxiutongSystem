package org.example.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserOrderVO {

    private Long id;

    private String orderNo;

    private Integer status;

    private String serviceItemName;

    private String workerName;

    private BigDecimal totalAmount;

    private LocalDateTime appointmentTime;

    private String address;

    private LocalDateTime createTime;
    // 状态中文名
    private String orderStatusName;

    private LocalDateTime payExpireTime;
    //支付成功后 payTime 设了值
    private LocalDateTime payTime;

}
