package org.example.service.Impl;

import jakarta.annotation.Resource;
import org.example.dto.WorkerDTO.WorkerLoginDTO;
import org.example.dto.WorkerDTO.WorkerRegisterDTO;
import org.example.entity.Worker;
import org.example.mapper.WorkerAdminMapper;
import org.example.service.WorkerLoginService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class WorkerLoginServiceImpl implements WorkerLoginService {


    @Resource
    private WorkerAdminMapper workerAdminMapper;
    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    /**
     * 工人登录
     * @param workerLoginDTO
     * @return
     */
    @Override
    public Worker login(WorkerLoginDTO workerLoginDTO) {
       //获取传来的账号根据账号查询工人
      Worker worker=workerAdminMapper.getByWorkerPhone(workerLoginDTO.getUsername());
        //判断工人是否存在
        if(worker==null){
            //不存在返回错误
            throw new RuntimeException("账号或者密码错误");
        }

        //验证密码是否错误
        String stored = worker.getPassword();
        if (stored.startsWith("$2a$")) {
            // 已是 BCrypt 密文，直接比对
            if (!bCryptPasswordEncoder.matches(workerLoginDTO.getPassword(), stored)) {
                throw new RuntimeException("账号或者密码错误");
            }
        } else {
            // 历史明文密码：先明文比对，通过则顺手升级为 BCrypt
            if (!stored.equals(workerLoginDTO.getPassword())) {
                throw new RuntimeException("账号或者密码错误");
            }
            worker.setPassword(bCryptPasswordEncoder.encode(workerLoginDTO.getPassword()));
            workerAdminMapper.updateWorker(worker);
        }
        //判断是否封禁
        if (worker.getStatus()==0){
            throw new RuntimeException("账号已被封禁，请联系管理员");
        }
        //返回用户信息
        return worker;

    }

    /**
     * 工人注册
     * @param workerRegisterDTO
     */
    @Transactional
    @Override
    public void register(WorkerRegisterDTO workerRegisterDTO) {
        //根据手机号查询用户
        Worker worker = workerAdminMapper.getByWorkerPhone(workerRegisterDTO.getPhone());
        //判断当前帐号是否存在
        if (worker != null){
            //存在返回已注册
            throw new RuntimeException("账号已注册");
        }
        //新建实体类
        Worker worker1 = new Worker();
        //为属性赋值
        worker1.setStatus(1);
        worker1.setPhone(workerRegisterDTO.getPhone());
        worker1.setName(workerRegisterDTO.getName());
        //密码加密
        worker1.setPassword(bCryptPasswordEncoder.encode(workerRegisterDTO.getPassword()));
        worker1.setCreateTime(LocalDateTime.now());
        worker1.setServiceStatus(0);
        //插入到工人表
        workerAdminMapper.insert(worker1);
    }
}
