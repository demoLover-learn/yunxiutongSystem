package org.example.service;

import org.example.Result.PageResult;
import org.example.Result.Result;
import org.example.dto.LoginDTO;
import org.example.dto.UserAdminPageQueryDTO;
import org.example.dto.WorkerAdminPageQueryDTO;
import org.example.entity.Employee;
import org.example.entity.Worker;
import org.springframework.data.domain.Page;

import javax.security.auth.login.AccountException;

public interface EmployeeService {

    /**
     * 管理员登陆
     * @param loginDTO
     */
    Employee login(LoginDTO loginDTO) throws AccountException;


    /**
     * 用户管理分页查询
     * @param pageQueryDTO
     * @return
     */
    PageResult getUser(UserAdminPageQueryDTO pageQueryDTO);
    /**
     * 更改账号状态
     * @param id
     * @return
     */
    void stopOrStart(Long id);
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
    void workerStatus(Long id);
}
