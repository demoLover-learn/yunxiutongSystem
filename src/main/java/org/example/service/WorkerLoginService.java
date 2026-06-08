package org.example.service;

import org.example.dto.WorkerDTO.WorkerLoginDTO;
import org.example.dto.WorkerDTO.WorkerRegisterDTO;
import org.example.entity.Worker;

public interface WorkerLoginService {

    /**
     * 工人登录
     * @param workerLoginDTO
     * @return
     */
    Worker login(WorkerLoginDTO workerLoginDTO);
    /**
     * 工人注册
     * @param workerRegisterDTO
     * @return
     */
    void register(WorkerRegisterDTO workerRegisterDTO);
}
