package org.example.service;

import org.example.dto.WorkerDTO.WorkerProfileDTO;
import org.example.vo.WorkerPendingVO;
import org.example.vo.WorkerProfileVO;

import java.util.List;

public interface WorkerProfileService {

    /**
     * 查看个人主页信息
     * @return
     */
    WorkerProfileVO profile();
    /**
     * 完善个人信息
     * @param workerProfileDTO
     * @return
     */
    WorkerProfileVO update(WorkerProfileDTO workerProfileDTO);
    /**
     * 更改工人接单状态
     * @return
     */
    void modifyStatus();

}
