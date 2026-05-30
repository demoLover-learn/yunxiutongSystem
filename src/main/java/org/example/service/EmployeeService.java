package org.example.service;

import org.example.Result.PageResult;
import org.example.dto.LoginDTO;
import org.example.dto.UserAdminPageQueryDTO;
import org.example.entity.Employee;

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
}
