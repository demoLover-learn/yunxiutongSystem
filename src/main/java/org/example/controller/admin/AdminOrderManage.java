package org.example.controller.admin;

import jakarta.annotation.Resource;
import org.example.Result.PageResult;
import org.example.Result.Result;
import org.example.dto.AdminDTO.OrderManageDTO;
import org.example.entity.ServiceOrder;
import org.example.service.AdminOrderManageService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminOrderManage {
    @Resource
    private AdminOrderManageService adminOrderManageService;

    /**
     * 工单管理分页查询
     * @param orderManageDTO
     * @return
     */
    @GetMapping("/orders")
    public Result<PageResult> pageQuery(OrderManageDTO orderManageDTO) {
       PageResult result= adminOrderManageService.pageQuery(orderManageDTO);
        return Result.success(result);
    }

    /**
     * 获取订单的详细信息
     * @param id
     * @return
     */
    @GetMapping("/orders/{id}")
        public Result<ServiceOrder> OrderDetail(@PathVariable Long id){
      ServiceOrder serviceOrder= adminOrderManageService.getOrderDetailById(id);
        return Result.success(serviceOrder);
    }
    /**
     * 派单接口
     * @param id
     * @return
     */
    @PutMapping("/orders/{id}/assign")
    public Result deliveryOrder(@PathVariable Long id,@RequestBody Map<String,Long> body) {
       adminOrderManageService.getOrder(id,body.get("workerId"));
        return Result.success();
    }

    /**
     * 取消订单
     * @param id
     * @return
     */
    @PutMapping("/orders/{id}/cancel")
    public Result cancelOrder(@PathVariable Long id){
        adminOrderManageService.cancelOrder(id);
        return Result.success();
    }


}
