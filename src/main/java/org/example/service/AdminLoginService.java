package org.example.service;

import org.example.dto.LoginDTO;
import org.example.entity.Employee;

import javax.security.auth.login.AccountException;

public interface AdminLoginService {

    /**
     * 管理员登陆
     * @param loginDTO
     */
    Employee login(LoginDTO loginDTO) throws AccountException;








}
