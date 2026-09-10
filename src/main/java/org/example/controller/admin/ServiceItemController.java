package org.example.controller.admin;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.example.Result.PageResult;
import org.example.Result.Result;
import org.example.dto.AdminDTO.ServiceItemPageQueryDTO;
import org.example.service.ServiceItemService;
import org.example.vo.ServiceItemVO;
import org.springframework.web.bind.annotation.*;
@Tag(name="管理端项目端")
@RestController
@RequestMapping("/api/admin")
public class ServiceItemController {
    @Resource
    private ServiceItemService serviceItemService;

    /**
     * 分页查询
     * @param serviceItemPageQueryDTO
     * @return
     */
    @Operation(summary = "项目服务分页查询")
    @GetMapping("/service-items")
    public Result<PageResult> getServiceItem(ServiceItemPageQueryDTO serviceItemPageQueryDTO) {
       PageResult result=serviceItemService.pageQuery(serviceItemPageQueryDTO);
       return Result.success(result);
    }

    /**
     * 新增项目
     * @param serviceItemVO
     * @return
     */
    @Operation(summary = "新增项目")
    @PostMapping("/service-items")
    public Result<Long> insert(@RequestBody ServiceItemVO serviceItemVO) {
    Long l=serviceItemService.insert(serviceItemVO);
    return Result.success(l);
    }

    /**
     * 更新项目
     * @param serviceItemVO
     * @return
     */
    @Operation(summary = "更新项目")
    @PutMapping("/service-items/{id}")
    public Result update(@PathVariable Long id,@RequestBody ServiceItemVO serviceItemVO) {
        serviceItemService.update(id,serviceItemVO);
        return Result.success();
    }

    /**
     * 更改服务项目状态
     * @param id
     * @param serviceItemVO
     * @return
     */
    @Operation(summary = "更新服务项目状态")
    @PutMapping("/service-items/{id}/status")
    public Result stopOrStart(@PathVariable Long id,@RequestBody ServiceItemVO serviceItemVO) {
        serviceItemService.stopOrStart(id,serviceItemVO);
        return Result.success();
    }

    /**
     * 删除项目订单
     * @param id
     * @return
     */
    @Operation(summary = "删除项目订单")
    @DeleteMapping("/service-items/{id}")
    public Result delete(@PathVariable Long id) {
        serviceItemService.delete(id);
        return Result.success();
    }

}
