package org.example.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkerPendingVO {

    private Long id;

    private BigDecimal totalAmount;

    private String serviceItemName;

    private LocalDateTime appointmentTime;

    private String address;

    private String remark;

    private Integer distance;


}
