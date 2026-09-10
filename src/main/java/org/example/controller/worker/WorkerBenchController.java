package org.example.controller.worker;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.Result.Result;
import org.example.service.WorkerBenchService;
import org.example.vo.WorkerBenchVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@Tag(name="工人端工作台接口")
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
    @Operation(summary = "工作台展示")
    @GetMapping("/workbench")
    public Result<WorkerBenchVO> workerBench(){
       WorkerBenchVO bench=workerBenchService.showWorkbench();
        return Result.success(bench);
    }





}
