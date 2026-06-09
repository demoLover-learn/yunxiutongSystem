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
public class WorkerDetailVO {


    private Long id;

    private BigDecimal totalAmount;

    private String serviceItemName;

    private LocalDateTime appointmentTime;

    private String address;

    private String remark;

    private Integer distance;

    private String orderNo;

    private LocalDateTime createTime;

    private String userName;

    private String userPhone;

    private Integer orderStatus;
    private String orderStatusName;

    private LocalDateTime receiveTime;

    private LocalDateTime startServiceTime;
    private LocalDateTime finishServiceTime;
    private LocalDateTime cancelTime;
    private String cancelReason;


}
