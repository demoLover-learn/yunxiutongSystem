package org.example.service.Impl;

import org.example.dto.AdminDTO.LoginDTO;
import org.example.entity.Employee;
import org.example.mapper.AdminLoginMapper;
import org.example.service.AdminLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountException;

@Service
public class AdminLoginServiceImpl implements AdminLoginService {
    @Autowired
    private AdminLoginMapper adminLoginMapper;



    /**
     * 管理员登陆
     * @param loginDTO
     */
    public Employee login(LoginDTO loginDTO) throws AccountException {
       //账号密码传过来了，先验证账号存在不
        Employee employee = adminLoginMapper.getByName(loginDTO.getUsername());
        if (employee == null) {
            throw new AccountException("账户不存在");
        }
        if(!employee.getPassword().equals(loginDTO.getPassword())) {
            throw new AccountException("账号或者密码错误");
        }
        if (employee.getStatus() == 0) {
            throw new AccountException("账号已经被锁定");
        }
        return employee;
    }


}
