package org.example.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceOrderComment {
    //评价id
    private Long id;
    //工单id
    private Long orderId;
    //用户id
    private Long userId;
    //服务人员id
    private Long workerId;
    //评分
    private Integer score;
    //评价内容
    private String content;
    //创建时间
    private LocalDateTime createTime;


    //非数据库字段，
    //工单编号
    private String orderNo;
    //用户名
    private String userName;
    //工人名称
    private String workerName;


}
