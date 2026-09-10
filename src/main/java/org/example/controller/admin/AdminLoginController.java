package org.example.controller.admin;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.Result.Result;
import org.example.context.BaseContext;
import org.example.dto.AdminDTO.LoginDTO;
import org.example.entity.Employee;
import org.example.properties.JwtProperties;
import org.example.service.AdminLoginService;
import org.example.util.JwtUtil;
import org.example.vo.EmployeeLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/admin/")
@Tag(name = "管理端相关接口")
public class AdminLoginController {
    @Autowired
    private AdminLoginService adminLoginService;
    @Autowired
    private JwtProperties jwtProperties;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 管理员登陆
     * @param loginDTO
     * @return
     */
    @Operation(summary = "管理员登陆")
    @PostMapping("/auth/login")
    public Result<EmployeeLoginVO> login(@RequestBody LoginDTO loginDTO) throws AccountException {
       Employee employee= adminLoginService.login(loginDTO);
       //生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        //payload载体
        claims.put("empId",employee.getId());
        String token=JwtUtil
                .createJWT(claims,
                        jwtProperties.getAdminSecretKey(),
                        jwtProperties.getAdminTtl());
        stringRedisTemplate.opsForValue().set(
                "login:admin:" + employee.getId(),
                token,
                2, TimeUnit.HOURS
        );
        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .name(employee.getName())
                .token(token)
                .build();
            return Result.success(employeeLoginVO);
    }

    /**
     * 退出接口
     * @return
     */
    @Operation(summary = "管理员退出")
    @PostMapping("/logout")
    public Result logout() {
        Long employeeId = BaseContext.getCurrentId();
        if (employeeId != null) {
            stringRedisTemplate.delete("login:admin:" + employeeId);
        }
        return Result.success();
    }
}
