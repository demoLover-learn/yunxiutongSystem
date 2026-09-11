package org.example.service.Impl;

import jakarta.annotation.Resource;
import org.example.dto.UserDTO.UserLoginDTO;
import org.example.dto.UserDTO.UserRegisterDTO;
import org.example.entity.User;
import org.example.mapper.UserAuthMapper;
import org.example.service.UserAuthService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserAuthServiceImpl implements UserAuthService {

    @Resource
    private UserAuthMapper userAuthMapper;
    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    /**
     * 用户登陆
     * @param userLoginDTO
     * @return
     */
    @Override
    public User userLogin(UserLoginDTO userLoginDTO) {
        //验证用户是否存在
            User user1= userAuthMapper.getByPhone(userLoginDTO.getPhone());
        //判断账号是否存在
        if (user1==null ){
            //不存在返回错误
            throw new RuntimeException("账号不存在，请先注册");
        }
        //存在判断账号密码是否正确
        if (user1.getStatus()!=1){
            throw new RuntimeException("账号已被封禁，请联系管理员");
        }
        //验证密码是否错误
        String stored = user1.getPassword();
        if (stored.startsWith("$2a$")) {
            // 已是 BCrypt 密文，直接比对
            if (!bCryptPasswordEncoder.matches(userLoginDTO.getPassword(), stored)) {
                throw new RuntimeException("账号或者密码错误");
            }
        } else {
            // 历史明文密码：先明文比对，通过则顺手升级为 BCrypt
            if (!stored.equals(userLoginDTO.getPassword())) {
                throw new RuntimeException("账号或者密码错误");
            }
            user1.setPassword(bCryptPasswordEncoder.encode(userLoginDTO.getPassword()));
            userAuthMapper.update(user1);
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
        if (userRegisterDTO==null){
            throw new RuntimeException("请输入有效信息");
        }
        BeanUtils.copyProperties(userRegisterDTO, user);
        user.setCreateTime(LocalDateTime.now());
        user.setStatus(1);
        //根据phone查询数据库，看看账号是否重复，重复抛出错误
        User user1= userAuthMapper.getByPhone(userRegisterDTO.getPhone());
        if (user1!=null){
            throw new RuntimeException("账号已存在，请直接登陆");
        }
        //密码加密
        user.setPassword(bCryptPasswordEncoder.encode(userRegisterDTO.getPassword()));
        //保存到数据库
        userAuthMapper.save(user);


    }
}
