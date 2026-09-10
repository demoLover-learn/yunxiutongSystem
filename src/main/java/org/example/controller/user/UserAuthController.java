package org.example.controller.user;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.Result.Result;
import org.example.context.BaseContext;
import org.example.dto.UserDTO.UserLoginDTO;
import org.example.dto.UserDTO.UserRegisterDTO;
import org.example.entity.User;
import org.example.properties.JwtProperties;
import org.example.service.UserAuthService;
import org.example.util.JwtUtil;
import org.example.vo.UserLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@Tag(name="用户端登陆管理")
@RequestMapping("/api/user")
@Slf4j
public class UserAuthController {

    @Resource
    private UserAuthService userAuthService;
    @Resource
    private JwtProperties jwtProperties;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 用户登陆
     * @param userLoginDTO
     * @return
     */
    @Operation(summary = "用户登陆")
    @PostMapping("/login")
    public Result<UserLoginVO> userLogin(@RequestBody UserLoginDTO userLoginDTO) {
        User user= userAuthService.userLogin(userLoginDTO);
        Map<String,Object> claims=new HashMap<>();
        claims.put("userId",user.getId());
        //生成jwt令牌
        String token = JwtUtil.createJWT(
                claims,
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl()
        );
        //存入redis
        stringRedisTemplate.opsForValue().set(
                "login:user:" +user.getId(),
                token,
                2, TimeUnit.HOURS
        );
        //封装VO对象返回前端
        UserLoginVO userLoginVO = UserLoginVO.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .token(token)
                .avatar(user.getAvatar())
                .phone(user.getPhone())
                .build();
        return Result.success(userLoginVO);
    }

    /**
     * 用户注册
     * @param userRegisterDTO
     * @return
     */
    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public Result userRegister(@RequestBody UserRegisterDTO userRegisterDTO) {
        userAuthService.userRegister(userRegisterDTO);
        return Result.success();
    }

    /**
     * 用户退出
     * @return
     */
    @Operation(summary = "用户退出")
    @PostMapping("/logout")
    public Result logout() {
        Long userId = BaseContext.getCurrentId();
        if (userId != null) {
            stringRedisTemplate.delete("login:user:" + userId);
        }
        return Result.success();
    }





}
