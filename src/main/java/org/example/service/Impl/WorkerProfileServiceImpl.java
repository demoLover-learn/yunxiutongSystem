package org.example.service.Impl;

import jakarta.annotation.Resource;
import org.example.context.BaseContext;
import org.example.dto.WorkerDTO.WorkerProfileDTO;
import org.example.entity.Worker;
import org.example.mapper.WorkerAdminMapper;

import org.example.service.WorkerProfileService;
import org.example.vo.WorkerProfileVO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class WorkerProfileServiceImpl implements WorkerProfileService {

    @Resource
    private WorkerAdminMapper workerAdminMapper;


    /**
     * 查看个人主页信息
     * @return
     */
    @Override
    public WorkerProfileVO profile() {
        //查询当前用户的id
        Long workerId = BaseContext.getCurrentId();
        //根据用户id查询信息
        Worker worker = workerAdminMapper.getMoreByWorkerId(workerId);
       //判空
        if (worker == null) {
            throw new RuntimeException("工人不存在");
        }
        //封装VO
      return WorkerProfileVO.builder()
                .phone(worker.getPhone())
                .avatar(worker.getAvatar())
                .serviceStatus(worker.getServiceStatus())
                .gender(worker.getGender() != null ? Integer.parseInt(worker.getGender()) : null)
                .rating(worker.getRating())
                .skillDesc(worker.getSkillDesc())
                .workerName(worker.getName())
                .todayCompleted(worker.getTodayCompleted())
                .totalCompleted(worker.getTotalCompleted())
                .build();

    }
    /**
     * 完善个人信息
     * @param workerProfileDTO
     * @return
     */
    @Transactional
    @Override
    public WorkerProfileVO update(WorkerProfileDTO workerProfileDTO) {
        //获取当前用户id
        Long workerId = BaseContext.getCurrentId();
        //新建实体类
        Worker worker = new Worker();
        //把对应的信息封装进去
        worker.setId(workerId);
        worker.setAvatar(workerProfileDTO.getAvatar());
        worker.setName(workerProfileDTO.getWorkerName());
        worker.setGender(workerProfileDTO.getGender());
        worker.setSkillDesc(workerProfileDTO.getSkillDesc());
        worker.setUpdateTime(LocalDateTime.now());
        //调用mapper更新数据库
        workerAdminMapper.updateWorker(worker);
        //重新查询数据库
        Worker worker1 = workerAdminMapper.getMoreByWorkerId(workerId);

        return WorkerProfileVO.builder()
                .phone(worker1.getPhone())
                .workerName(worker1.getName())
                .serviceStatus(worker1.getServiceStatus())
                .avatar(worker1.getAvatar())
                .rating(worker1.getRating())
                .gender(worker1.getGender()!=null ?Integer.parseInt(worker1.getGender()) : null)
                .skillDesc(worker1.getSkillDesc())
                .todayCompleted(worker1.getTodayCompleted())
                .totalCompleted(worker1.getTotalCompleted())
                .build();
    }
    /**
     * 更改工人接单状态
     * @return
     */
    @Transactional
    @Override
    public void modifyStatus() {
        //查出当前工人的id
        Long workerId = BaseContext.getCurrentId();
        //根据id查询出工人的全部信息
        Worker worker = workerAdminMapper.getByWorkerId(workerId);
        //查看接单状态
        if (worker == null) {
            throw new RuntimeException("用户不存在");
        }
        Integer serviceStatus = worker.getServiceStatus();
        if (serviceStatus == 2) {
            throw new RuntimeException("服务中不可更改工单状态");
        }
        //如果是接单的话//改成下线\
        if (serviceStatus == 1) {
            worker.setServiceStatus(0);
        } else if (serviceStatus == 0) {
            //如果是下线//改成上线状态
            worker.setServiceStatus(1);
        }
        workerAdminMapper.updateWorker(worker);
    }
}
