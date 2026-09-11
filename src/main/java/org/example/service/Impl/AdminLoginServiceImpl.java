package org.example.service.Impl;

import org.example.dto.AdminDTO.LoginDTO;
import org.example.entity.Employee;
import org.example.mapper.AdminLoginMapper;
import org.example.service.AdminLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountException;

@Service
public class AdminLoginServiceImpl implements AdminLoginService {
    @Autowired
    private AdminLoginMapper adminLoginMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;



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
        String stored = employee.getPassword();
        if (stored.startsWith("$2a$")) {
            // 已是 BCrypt 密文，直接比对
            if (!bCryptPasswordEncoder.matches(loginDTO.getPassword(), stored)) {
                throw new RuntimeException("账号或者密码错误");
            }
        } else {
            // 历史明文密码：先明文比对，通过则顺手升级为 BCrypt
            if (!stored.equals(loginDTO.getPassword())) {
                throw new RuntimeException("账号或者密码错误");
            }
            employee.setPassword(bCryptPasswordEncoder.encode(loginDTO.getPassword()));
            adminLoginMapper.updatePassword(employee);
        }
        if (employee.getStatus() == 0) {
            throw new RuntimeException("账号已经被锁定");
        }
        return employee;
    }


}
