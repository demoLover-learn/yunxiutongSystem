package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
//工单日志表
public class ServiceOrderLog {
    //日志id
    private Long id;
    //工单id
    private Long orderId;
    //操作人类型,user/worker/admin/system
    private Long operatorType;
    //操作人id
    private Long operatorId;
    //原状态
    private Integer fromStatus;
    //新状态
    private Integer toStatus;
    //操作说明
    private String content;
    //创建时间
    private LocalDateTime createTime;



}
