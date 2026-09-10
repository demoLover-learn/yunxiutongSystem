package org.example.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.example.Result.PageResult;
import org.example.Result.Result;
import org.example.dto.AdminDTO.WorkerAdminPageQueryDTO;
import org.example.entity.Worker;
import org.example.service.WorkerAdminService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "管理端工人管理")
@RestController
@RequestMapping("/api/admin/")
public class WorkerAdminController {
    @Resource
    private WorkerAdminService workerAdminService;
    /**
     * 服务人员管理
     * @param queryDTO
     * @return
     */
    @Operation(summary = "工人管理")
    @GetMapping("/workers")
    public Result<PageResult> getWorker(WorkerAdminPageQueryDTO queryDTO) {
        PageResult result=workerAdminService.getWorker(queryDTO);
        return Result.success(result);
    }

    /**
     * 工人账号状态的设置
     * @param id
     * @return
     */
    @Operation(summary = "账号状态设置")
    @PutMapping("/workers/{id}/status")
    public Result workStatus(@PathVariable Long id,@RequestBody WorkerAdminPageQueryDTO queryDTO) {
        workerAdminService.workerStatus(id,queryDTO);
        return Result.success();
    }

    /**
     * 筛选合适的师傅
     * @return
     */
    @Operation(summary = "工人查询")
    @GetMapping("/workers/available")
    public Result<List<Worker>> getAvailableWorker(){
        List<Worker> list=workerAdminService.selectWorker();
            return Result.success(list);
    }

}
