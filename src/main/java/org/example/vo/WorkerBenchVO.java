package org.example.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.ServiceOrder;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkerBenchVO {


    //待处理数
    private Integer todayPending;
    //服务中的数
    private Integer todayServing;
    //今日完成数
    private Integer todayDone;

   private List<ServiceOrder> activeOrders;








}
