package org.example.controller.user;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

import org.example.Result.Result;
import org.example.dto.AdminDTO.ServiceItemPageQueryDTO;
import org.example.service.UserServiceService;
import org.example.vo.ServiceItemVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserServiceController {

    @Resource
    private UserServiceService userServiceService;

    /**
     * 分页查询
     * @param serviceItemPageQueryDTO
     * @return
     */
    @GetMapping("/services")
    public Result<List<ServiceItemVO>> pageQuery(ServiceItemPageQueryDTO serviceItemPageQueryDTO) {
        List<ServiceItemVO> vos= userServiceService.pageQuery(serviceItemPageQueryDTO);
        return Result.success(vos);
    }

    /**
     * 查看服务详情信息
     * @param id
     * @return
     */
    @GetMapping("/services/{id}")
    public Result<ServiceItemVO> getDetail(@PathVariable Long id) {
     ServiceItemVO  vo=userServiceService.getDetail(id);
        return Result.success(vo);
    }
}
