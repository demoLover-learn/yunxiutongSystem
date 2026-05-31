package org.example.controller.admin;

import jakarta.annotation.Resource;
import org.example.Result.PageResult;
import org.example.Result.Result;
import org.example.dto.WorkerAdminPageQueryDTO;
import org.example.entity.Worker;
import org.example.service.WorkerAdminService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PutMapping("/workers/{id}/status")
    public Result workStatus(@PathVariable Long id,@RequestBody WorkerAdminPageQueryDTO queryDTO) {
        workerAdminService.workerStatus(id,queryDTO);
        return Result.success();
    }

    /**
     * 筛选合适的师傅
     * @return
     */
    @GetMapping("/workers/available")
    public Result<List<Worker>> getAvailableWorker(){
        List<Worker> list=workerAdminService.selectWorker();
            return Result.success(list);
    }

}
