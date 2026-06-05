package org.example.controller.user;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.Result.PageResult;
import org.example.Result.Result;
import org.example.dto.UserDTO.UserCommentDTO;
import org.example.dto.UserDTO.UserOrderDTO;
import org.example.service.UserOrderService;
import org.example.vo.UserOrderVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserOrderController {

    @Resource
    private UserOrderService userOrderService;


    /**
     * 确认订单
     * @param userOrderDTO
     * @return
     */
    @PostMapping("/orders")
    public Result<Long> confirmOrder(@RequestBody UserOrderDTO userOrderDTO) {
        Long orderId=userOrderService.ConfirmOrder(userOrderDTO);
        return Result.success(orderId);
    }

    /**
     * 查询工单详情
     * @param id
     * @return
     */
    @GetMapping("/orders/{id}")
    public Result<UserOrderVO> orderDetail(@PathVariable Long id){
        UserOrderVO vo=userOrderService.getOrderDetail(id);
        return Result.success(vo);
    }

    /**
     * 工单支付
     * @param id
     * @return
     */
    @PutMapping("/orders/{id}/pay")
    public Result<String> payment(@PathVariable Long id){
        userOrderService.payment(id);
        return Result.success("支付成功");
    }

    /**
     * 取消订单
     * @param id
     * @return
     */
    @PutMapping("/orders/{id}/cancel")
    public Result cancelOrder(@PathVariable Long id){
        userOrderService.cancelOrder(id);
        return Result.success();
    }

    /**
     * 工单的分页查询
     * @param userOrderDTO
     * @return
     */
    @GetMapping("/orders")
    public Result<PageResult> orderList(UserOrderDTO userOrderDTO){
        PageResult pageResult=userOrderService.pageQuery(userOrderDTO);
    return Result.success(pageResult);
    }

    /**
     * 用户评价
     * @param id
     * @param userCommentDTO
     * @return
     */
    @PostMapping("/orders/{id}/comment")
    public Result<String> orderComment(@PathVariable Long id,
                                       @RequestBody UserCommentDTO userCommentDTO){
        userOrderService.orderComment(id,userCommentDTO);
        return Result.success("提交成功");
    }


}
