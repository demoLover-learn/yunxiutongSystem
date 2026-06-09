package org.example.controller.worker;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.Result.Result;
import org.example.service.WorkerBenchService;
import org.example.vo.WorkerBenchVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/worker")
@Slf4j
public class WorkerBenchController {
    @Resource
    private WorkerBenchService workerBenchService;

    /**
     * 工作台展示
     * @return
     */
    @GetMapping("/workbench")
    public Result<WorkerBenchVO> workerBench(){
       WorkerBenchVO bench=workerBenchService.showWorkbench();
        return Result.success(bench);
    }





}
