package org.example.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.example.Result.PageResult;
import org.example.Result.Result;
import org.example.dto.AdminDTO.UserAdminPageQueryDTO;
import org.example.service.UserAdminService;
import org.springframework.web.bind.annotation.*;
@Tag(name = "管理端用户操作")
@RestController
@RequestMapping("/api/admin/")
public class UserAdminController {

    @Resource
    private UserAdminService userAdminService;

    /**
     * 用户管理分页查询
     * @param pageQueryDTO
     * @return
     */
    @Operation(summary = "用户分页查询")
    @GetMapping("/users")
    public Result<PageResult> getUser(UserAdminPageQueryDTO pageQueryDTO) {
        PageResult pageResult= userAdminService.getUser(pageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 更改账号状态
     * @param id
     * @return
     */
    @Operation(summary = "更改账号状态")
    @PutMapping("/users/{id}/status")
    public Result stopOrStart(@PathVariable Long id,@RequestBody UserAdminPageQueryDTO pageQueryDTO) {
        userAdminService.stopOrStart(id,pageQueryDTO);
        return Result.success();
    }
}
