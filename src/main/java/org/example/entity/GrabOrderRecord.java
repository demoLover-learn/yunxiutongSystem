package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
//抢单记录表
public class GrabOrderRecord {
    //记录id
    private Long id;
    //工单id
    private Long orderId;
    //服务人员id
    private Long workerId;
    //抢单结果：1成功 0失败
    private Integer grabResult;
    //创建时间
    private LocalDateTime createTime;



}
