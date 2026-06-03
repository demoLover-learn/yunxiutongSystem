package org.example.service;

import org.example.dto.UserDTO.UserLoginDTO;
import org.example.dto.UserDTO.UserRegisterDTO;
import org.example.entity.User;

public interface UserLoginService {
    /**
     * 用户登陆
     * @param userLoginDTO
     * @return
     */
    User userLogin(UserLoginDTO userLoginDTO);
    /**
     * 用户注册
     * @param userRegisterDTO
     * @return
     */
    void userRegister(UserRegisterDTO userRegisterDTO);
}
