package org.example.controller.admin;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.Result.Result;
import org.example.service.StaticsService;
import org.example.vo.OrderCountVO;
import org.example.vo.OrderStatusCountVO;
import org.example.vo.WorkerRankVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@Slf4j
@RequestMapping("/api/admin/statistics")
public class StaticsController {
    @Resource
    private StaticsService staticsService;

    /**
     * 订单趋势图
     * @return
     */
    @GetMapping("/order-count")
        public Result<Map<String, Object>> showPage(){
        return  Result.success(staticsService.showPage());
    }

    /**
     * 订单状态图
     * @return
     */
    @GetMapping("/order-status-count")
    public Result<List<OrderStatusCountVO>> showStatus(){
        return Result.success(staticsService.getStatusPic());
    }

    /**
     * 工人排行
     * @return
     */
    @GetMapping("/worker-rank")
    public Result<List<WorkerRankVO>> showWorkerRank(){
        return Result.success(staticsService.getWorkerRank());
    }


}
