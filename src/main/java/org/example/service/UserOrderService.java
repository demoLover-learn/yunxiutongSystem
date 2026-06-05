package org.example.service;

import org.example.Result.PageResult;
import org.example.dto.UserDTO.UserCommentDTO;
import org.example.dto.UserDTO.UserOrderDTO;
import org.example.vo.UserOrderVO;

import java.util.List;

public interface UserOrderService {

    /**
     * 确认订单
     * @param userOrderDTO
     * @return
     */
    Long ConfirmOrder(UserOrderDTO userOrderDTO);
    /**
     * 查询工单详情
     * @param id
     * @return
     */
    UserOrderVO getOrderDetail(Long id);

    /**
     * 工单支付
     * @param id
     */
    void payment(Long id);

    /**
     *取消订单
     * @param id
     */
    void cancelOrder(Long id);

    /**
     * 工单分页查询
     * @param userOrderDTO
     * @return
     */
   PageResult pageQuery(UserOrderDTO userOrderDTO);

    /**
     * 用户评价
     * @param id
     * @param userCommentDTO
     * @return
     */
    void orderComment(Long id, UserCommentDTO userCommentDTO);
}
