package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.vo.OrderCountVO;
import org.example.vo.OrderStatusCountVO;
import org.example.vo.WorkerRankVO;

import java.util.List;

@Mapper
public interface StaticsMapper {
    /**
     * 查询月份和订单数量
     * @return
     */
    List<OrderCountVO> getMonthAndAmount();
    /**
     * 订单状态图
     * @return
     */
    List<OrderStatusCountVO> getStatusAndCount();

    /**
     * 工人排行
     * @return
     */
    List<WorkerRankVO> getWorkerRank();
}
