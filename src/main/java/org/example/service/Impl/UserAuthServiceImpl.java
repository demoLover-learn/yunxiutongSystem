package org.example.service.Impl;

import jakarta.annotation.Resource;
import org.example.dto.UserDTO.UserLoginDTO;
import org.example.dto.UserDTO.UserRegisterDTO;
import org.example.entity.User;
import org.example.mapper.UserAuthMapper;
import org.example.service.UserAuthService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserAuthServiceImpl implements UserAuthService {

    @Resource
    private UserAuthMapper userAuthMapper;
    /**
     * 用户登陆
     * @param userLoginDTO
     * @return
     */
    @Override
    public User userLogin(UserLoginDTO userLoginDTO) {
        //验证用户是否存在
        User user1= userAuthMapper.getUser(userLoginDTO.getUsername());
        //判断账号是否存在
        if (user1==null ){
            //不存在返回错误
            throw new RuntimeException("账号不存在，请先注册");
        }
        //存在判断账号密码是否正确
        if(!(user1.getPhone().equals(userLoginDTO.getUsername()))){
            throw new RuntimeException("账号或者密码错误");
        }
        if (user1.getStatus()!=1){
            throw new RuntimeException("账号已被封禁，请联系管理员");
        }
        if(!(user1.getPassword().equals(userLoginDTO.getPassword()))){
            throw new RuntimeException("账号或者密码错误");
        }
        //正确的话返回用户信息
        return user1;
    }
    /**
     * 用户注册
     * @param userRegisterDTO
     * @return
     */
    @Override
    public void userRegister(UserRegisterDTO userRegisterDTO) {
        //新建实体类把属性拷贝过来
        User user = new User();
        BeanUtils.copyProperties(userRegisterDTO, user);
        user.setCreateTime(LocalDateTime.now());
        user.setStatus(1);
        //根据phone查询数据库，看看账号是否重复，重复抛出错误
        User user1= userAuthMapper.getByPhone(userRegisterDTO.getPhone());
        if (user1!=null){
            throw new RuntimeException("账号已存在，请直接登陆");
        }
        //保存到数据库
        userAuthMapper.save(user);


    }
}
