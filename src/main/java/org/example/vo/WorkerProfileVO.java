package org.example.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkerProfileVO {

    private  String workerName;

    private String phone;

    private String avatar;

    private Integer serviceStatus;

    private Integer gender;

    private String skillDesc;

    private BigDecimal rating;

    private Integer todayCompleted;

    private Integer totalCompleted;




}
