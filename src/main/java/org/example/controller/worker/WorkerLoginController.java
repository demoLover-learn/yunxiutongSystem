package org.example.controller.worker;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.Result.Result;
import org.example.dto.WorkerDTO.WorkerLoginDTO;
import org.example.dto.WorkerDTO.WorkerRegisterDTO;
import org.example.entity.Worker;
import org.example.properties.JwtProperties;
import org.example.service.WorkerLoginService;
import org.example.util.JwtUtil;
import org.example.vo.WorkerLoginVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/worker")
@Slf4j
public class WorkerLoginController {

    @Resource
    private WorkerLoginService workerLoginService;
    @Resource
    private JwtProperties jwtProperties;
    /**
     * 工人登录
     * @param workerLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<WorkerLoginVO> workerLogin(@RequestBody WorkerLoginDTO workerLoginDTO) {
           Worker worker= workerLoginService.login(workerLoginDTO);
           //创建token，并返回前端
        Map<String,Object> claims=new HashMap<>();
        claims.put("userId",worker.getId());
        String token = JwtUtil.createJWT(claims,
                jwtProperties.getWorkerSecretKey(),
                jwtProperties.getUserTtl());
        WorkerLoginVO workerLoginVO = WorkerLoginVO.builder()
                .id(worker.getId())
                .phone(worker.getPhone())
                .workerName(worker.getName())
                .avatar(worker.getAvatar())
                .gender(worker.getGender() != null ? Integer.parseInt(worker.getGender()) : null)
                .token(token)
                .build();

        return Result.success(workerLoginVO);
    }

    /**
     * 工人注册
     * @param workerRegisterDTO
     * @return
     */
    @PostMapping("/register")
    public Result<String> register(@RequestBody WorkerRegisterDTO workerRegisterDTO) {
        workerLoginService.register(workerRegisterDTO);
        return Result.success("注册成功");
    }
}
