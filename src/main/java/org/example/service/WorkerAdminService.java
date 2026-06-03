package org.example.service;

import org.example.Result.PageResult;
import org.example.dto.AdminDTO.WorkerAdminPageQueryDTO;
import org.example.entity.Worker;

import java.util.List;

public interface WorkerAdminService {

    /**
     * 服务人员管理
     * @param queryDTO
     * @return
     */
    PageResult getWorker(WorkerAdminPageQueryDTO queryDTO);
    /**
     * 工人账号状态的设置
     * @param id
     * @return
     */
    void workerStatus(Long id,WorkerAdminPageQueryDTO queryDTO);
    /**
     * 筛选合适的师傅
     * @return
     */
    List<Worker> selectWorker();


}
