package org.example.controller.admin;

import jakarta.annotation.Resource;
import org.example.Result.PageResult;
import org.example.Result.Result;
import org.example.dto.AdminDTO.ServiceItemPageQueryDTO;
import org.example.service.ServiceItemService;
import org.example.vo.ServiceItemVO;
import org.springframework.web.bind.annotation.*;

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
    @DeleteMapping("/service-items/{id}")
    public Result delete(@PathVariable Long id) {
        serviceItemService.delete(id);
        return Result.success();
    }

}
