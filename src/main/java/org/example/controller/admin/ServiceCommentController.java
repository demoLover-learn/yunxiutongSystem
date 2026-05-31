package org.example.controller.admin;

import jakarta.annotation.Resource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.example.Result.PageResult;
import org.example.Result.Result;
import org.example.dto.ServiceCommentPageQueryDTO;
import org.example.service.ServiceCommentService;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/admin/order-comments")
public class ServiceCommentController {
    @Resource
    private ServiceCommentService serviceCommentService;

    /**
     * 评价管理分页查询
     * @param serviceCommentPageQueryDTO
     * @return
     */
    @GetMapping
    public Result<PageResult> commentPageQuery(ServiceCommentPageQueryDTO serviceCommentPageQueryDTO) {
           PageResult result= serviceCommentService.getComment(serviceCommentPageQueryDTO);
           return Result.success(result);
    }

    /**
     * 删除数据
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result deleteComment(@PathVariable Long id){
        serviceCommentService.deleteComment(id);
        return Result.success();
    }



}
