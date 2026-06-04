package org.example.controller.user;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.Result.Result;
import org.example.service.UserCategoryService;
import org.example.vo.ServiceCategoryVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserCategoryController {
    @Resource
    private UserCategoryService userCategoryService;

    /**
     * 查询服务类别
     * @return
     */
    @GetMapping("/categories")
    public Result<List<ServiceCategoryVO>> getCategories(){
        List<ServiceCategoryVO> allCategories = userCategoryService.getAllCategories();
        return Result.success(allCategories);
    }

}
