package org.example.controller.admin;

import jakarta.annotation.Resource;
import org.example.Result.PageResult;
import org.example.Result.Result;
import org.example.dto.ServiceCategoryDTO;
import org.example.service.ServiceCategoryService;
import org.example.vo.ServiceCategoryVO;
import org.springframework.web.bind.annotation.*;

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
    @PutMapping("/{id}/status")
    public Result updateStatus(@PathVariable Long id, @RequestBody ServiceCategoryVO serviceCategoryVO){
        serviceCategoryService.updateStatus(id,serviceCategoryVO);
        return Result.success();
    }

    /**
     * 删除对应的服务分类
     * @param id
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        serviceCategoryService.deleteById(id);
    }
}
