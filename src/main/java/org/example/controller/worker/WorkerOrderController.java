package org.example.controller.worker;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.Result.Result;
import org.example.dto.WorkerDTO.WorkerOrderDTO;
import org.example.service.WorkerOrderService;
import org.example.vo.WorkerDetailVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/worker")
@Slf4j
public class WorkerOrderController {

    @Resource
    private WorkerOrderService workerOrderService;

    /**
     * 工人端工单查询
     * @param vo
     * @return
     */
    @GetMapping("/orders")
    public Result<List<WorkerDetailVO>> orderList(WorkerOrderDTO vo){
      List<WorkerDetailVO> workerDetailVOS=workerOrderService.orderList(vo);
      return Result.success(workerDetailVOS);
    }

    /**
     * 开始服务
     * @param id
     * @return
     */
    @PutMapping("/orders/{id}/start")
    public Result startServer(@PathVariable Long id){
        workerOrderService.startServer(id);
        return Result.success();
    }

    /**
     * 完成服务
     * @param id
     * @return
     */
    @PutMapping("/orders/{id}/complete")
    public Result completeOrder(@PathVariable Long id){
        workerOrderService.completeOrder(id);
        return Result.success();
    }








}
