package org.example.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkerLoginVO {
    private Long id;

    private String phone;

    private String avatar;

    private String workerName;

    private Integer gender;

    private String token;




}
