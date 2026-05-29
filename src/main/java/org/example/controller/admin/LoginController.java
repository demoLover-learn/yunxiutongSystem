package org.example.controller.admin;


import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.example.Result.Result;
import org.example.dto.LoginDTO;
import org.example.entity.Employee;
import org.example.properties.JwtProperties;
import org.example.service.EmployeeService;
import org.example.util.JwtUtil;
import org.example.vo.EmployeeLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import javax.security.auth.login.AccountException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/")
public class LoginController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 管理员登陆
     * @param loginDTO
     * @return
     */
    @PostMapping("/auth/login")
    public Result<EmployeeLoginVO> login(@RequestBody LoginDTO loginDTO) throws AccountException {
       Employee employee= employeeService.login(loginDTO);
       //生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        //payload载体
        claims.put("empId",employee.getId());
        String token=JwtUtil
                .createJWT(claims,
                        jwtProperties.getAdminSecretKey(),
                        jwtProperties.getAdminTtl());

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .name(employee.getName())
                .token(token)
                .name(employee.getName())
                .build();
            return Result.success(employeeLoginVO);

    }


}
