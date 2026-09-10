package org.example.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.example.Result.PageResult;
import org.example.Result.Result;
import org.example.dto.AdminDTO.ServiceCategoryDTO;
import org.example.entity.ServiceCategory;
import org.example.service.ServiceCategoryService;
import org.example.vo.ServiceCategoryVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name="管理端分类管理")
@RestController
@RequestMapping("/api/admin/service-categories")
public class ServiceCategoryController {
    @Resource
    private ServiceCategoryService serviceCategoryService;

    /**
     * 新增分类
     * @param serviceCategoryVO
     * @return
     */
    @Operation(summary = "新增分类")
    @PostMapping
    public Result<Long> insert(@RequestBody ServiceCategoryVO serviceCategoryVO) {
       Long l= serviceCategoryService.insert(serviceCategoryVO);
       return Result.success(l);
    }



    /**
     * 服务分类管理
     * @param
     * @return
     */
    @Operation(summary = "服务分类管理")
    @GetMapping
    public Result<PageResult> getServiceCategory(ServiceCategoryDTO serviceCategoryDTO) {
        PageResult result=serviceCategoryService.getServiceCategory(serviceCategoryDTO);
        return Result.success(result);
    }

    /**
     * 编辑服务分类信息
     * @param id
     * @return
     */
    @Operation(summary = "编辑服务分类信息")
    @PutMapping("/{id}")
    public Result update(@PathVariable Long id, @RequestBody ServiceCategoryVO serviceCategoryVO) {
        serviceCategoryService.updateServiceCategory(id,serviceCategoryVO);
        return Result.success();
    }

    /**
     * 账号的禁用和启用
     * @param id
     * @param serviceCategoryVO
     * @return
     */
    @Operation(summary = "账号的禁用和启用")
    @PutMapping("/{id}/status")
    public Result updateStatus(@PathVariable Long id, @RequestBody ServiceCategoryVO serviceCategoryVO){
        serviceCategoryService.updateStatus(id,serviceCategoryVO);
        return Result.success();
    }

    /**
     * 删除对应的服务分类
     * @param id
     */
    @Operation(summary = "删除分类服务")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        serviceCategoryService.deleteById(id);
    }

    /**
     * 查询所有分类
     * @return
     */
    @Operation(summary = "查询所有分类")
    @GetMapping("/all")
    public Result<List<ServiceCategory>> getCategory() {
       List<ServiceCategory> serviceCategory = serviceCategoryService.getAllCategorys();
        return Result.success(serviceCategory);
    }
}
