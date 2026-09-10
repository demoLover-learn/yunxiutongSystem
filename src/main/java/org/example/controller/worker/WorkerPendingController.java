package org.example.controller.worker;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.Result.Result;
import org.example.service.WorkerPendingService;
import org.example.service.WorkerProfileService;
import org.example.vo.WorkerDetailVO;
import org.example.vo.WorkerPendingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Tag(name="工人端订单查询")
@RestController
@RequestMapping("/api/worker")
@Slf4j
public class WorkerPendingController {

    @Resource
    private WorkerPendingService workerPendingService;


    /**
     * 待接单查询
     * @return
     */
    @Operation(summary = "待接单查询")
    @GetMapping("/orders/pending")
    public Result<List<WorkerPendingVO>> pending(){
       List<WorkerPendingVO> pending=workerPendingService.pendingOrder();
       return Result.success(pending);
    }

    /**
     * 查看订单的详细信息
     * @param id
     * @return
     */
    @Operation(summary = "订单详细信息")
    @GetMapping("/orders/{id}")
    public Result<WorkerDetailVO> orderDetail(@PathVariable Long id){
        WorkerDetailVO order=workerPendingService.orderDetail(id);
        return Result.success(order);
    }





}
