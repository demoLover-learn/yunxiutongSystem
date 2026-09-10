package org.example.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.Result.PageResult;
import org.example.Result.Result;
import org.example.dto.AdminDTO.ServiceCommentPageQueryDTO;
import org.example.service.ServiceCommentService;
import org.springframework.web.bind.annotation.*;

@Tag(name = "管理端评价管理")
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
    @Operation(summary = "评价管理分页查询")
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
    @Operation(summary = "删除数据")
    @DeleteMapping("/{id}")
    public Result deleteComment(@PathVariable Long id){
        serviceCommentService.deleteComment(id);
        return Result.success();
    }



}
