package org.example.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkerRankVO {
    //师傅名称
    private String workerName;
    //工单排行
    private Integer rating;
    //完成工单数
    private Integer orderCount;

}
