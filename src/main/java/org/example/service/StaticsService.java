package org.example.service;

import org.example.vo.OrderCountVO;
import org.example.vo.OrderStatusCountVO;
import org.example.vo.WorkerRankVO;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public interface StaticsService {

    /**
     * 订单趋势图
     * @return
     */
    Map<String,Object> showPage();

    /**
     * 订单状态图
     * @return
     */
    List<OrderStatusCountVO> getStatusPic();

    /**
     * 工人排行
     * @return
     */
    List<WorkerRankVO> getWorkerRank();
}
