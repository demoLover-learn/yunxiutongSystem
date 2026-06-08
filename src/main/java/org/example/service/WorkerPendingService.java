package org.example.service;

import org.example.vo.WorkerDetailVO;
import org.example.vo.WorkerPendingVO;

import java.util.List;

public interface WorkerPendingService {

    /**
     * 待接单查询
     * @return
     */
    List<WorkerPendingVO> pendingOrder();

    /**
     * 查看订单的详细信息
     * @param id
     * @return
     */
   WorkerDetailVO orderDetail(Long id);
}
