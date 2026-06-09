package org.example.service;


import org.example.dto.WorkerDTO.WorkerOrderDTO;
import org.example.vo.WorkerDetailVO;

import java.util.List;

public interface WorkerOrderService {


    /**
     * 工人的工单查询
     * @param status
     * @return
     */
    List<WorkerDetailVO> orderList(WorkerOrderDTO order);

    /**
     * 开始服务
     * @param id
     * @return
     */
    void startServer(Long id);
    /**
     * 完成服务
     * @param id
     * @return
     */
    void completeOrder(Long id);
}
