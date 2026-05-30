package org.example.controller.admin;


import com.github.pagehelper.Page;
import org.example.Result.PageResult;
import org.example.Result.Result;
import org.example.dto.LoginDTO;
import org.example.dto.UserAdminPageQueryDTO;
import org.example.dto.WorkerAdminPageQueryDTO;
import org.example.entity.Employee;
import org.example.properties.JwtProperties;
import org.example.service.EmployeeService;
import org.example.util.JwtUtil;
import org.example.vo.EmployeeLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/")
public class AdminController {
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


    /**
     * 用户管理分页查询
     * @param pageQueryDTO
     * @return
     */
    @GetMapping("/users")
    public Result<PageResult> getUser(UserAdminPageQueryDTO pageQueryDTO) {
        PageResult pageResult= employeeService.getUser(pageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 更改账号状态
     * @param id
     * @return
     */
    @PutMapping("/users/{id}/status")
    public Result stopOrStart(@PathVariable Long id){
        employeeService.stopOrStart(id);
        return Result.success();
    }

    /**
     * 服务人员管理
     * @param queryDTO
     * @return
     */
    @GetMapping("/workers")
    public Result<PageResult> getWorker(WorkerAdminPageQueryDTO queryDTO) {
        PageResult result=employeeService.getWorker(queryDTO);
        return Result.success(result);
    }

    /**
     * 工人账号状态的设置
     * @param id
     * @return
     */
    @PutMapping("/workers/{id}/status")
    public Result workStatus(@PathVariable Long id){
        employeeService.workerStatus(id);
        return Result.success();
    }



}
