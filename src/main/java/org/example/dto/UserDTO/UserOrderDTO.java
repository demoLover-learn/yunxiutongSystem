package org.example.dto.UserDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
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
}
