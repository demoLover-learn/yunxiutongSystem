package org.example.dto.UserDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class UserOrderDTO {

    private int page;

    private int pageSize;

    private String status;

    //服务项目id
    private Long serviceItemId;
    //预约时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime appointmentTime;
    //地址id
    private Long addressId;
    //备注
    private String remark;
    //总金额
    private Long totalAmount;


    //ai返回所需字段外加一个appointment
    private String orderNo;
    private String statusName;
    private String serviceItemName;
    private String workerName;
    private BigDecimal amount;

}
