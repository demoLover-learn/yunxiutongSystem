package org.example.controller.worker;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.Result.Result;
import org.example.dto.WorkerDTO.WorkerProfileDTO;
import org.example.service.WorkerProfileService;
import org.example.vo.WorkerProfileVO;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/worker")
@Slf4j
public class WorkerProfileController {

    @Resource
    private WorkerProfileService workerProfileService;


    /**
     * 查看个人主页信息
     * @return
     */
    @GetMapping("/profile")
    public Result<WorkerProfileVO> getProfile(){
       WorkerProfileVO profile= workerProfileService.profile();
       return Result.success(profile);
    }

    /**
     * 完善个人信息
     * @param workerProfileDTO
     * @return
     */
    @PutMapping("/profile")
    public Result<WorkerProfileVO> updateProfile(@RequestBody WorkerProfileDTO workerProfileDTO){
        WorkerProfileVO profile=workerProfileService.update(workerProfileDTO);
        return Result.success(profile);
    }

    /**
     * 更改工人接单状态
     * @return
     */
    @PutMapping("/service-status")
    public Result statusOrStop(){
        workerProfileService.modifyStatus();
        return Result.success();
    }
}
