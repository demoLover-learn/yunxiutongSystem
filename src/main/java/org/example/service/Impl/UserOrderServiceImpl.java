package org.example.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import org.example.Result.PageResult;
import org.example.context.BaseContext;
import org.example.dto.UserDTO.UserCommentDTO;
import org.example.dto.UserDTO.UserOrderDTO;
import org.example.entity.ServiceOrder;
import org.example.entity.ServiceOrderComment;
import org.example.mapper.AdminOrderManageMapper;
import org.example.mapper.ServiceItemMapper;
import org.example.mapper.UserOrderCommentMapper;
import org.example.service.OrderStatusService;
import org.example.service.UserOrderService;
import org.example.vo.UserOrderVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserOrderServiceImpl implements UserOrderService {
    @Resource
    private UserOrderCommentMapper userOrderCommentMapper;
    @Resource
    private ServiceItemMapper serviceItemMapper;
    @Resource
    private AdminOrderManageMapper adminOrderManageMapper;
    @Resource
    private OrderStatusService orderStatusService;

    /**
     * 确认订单
     * @param userOrderDTO
     * @return
     */
    @Transactional
    @Override
    public Long ConfirmOrder(UserOrderDTO userOrderDTO) {
        //新建实体类接受信息\
        ServiceOrder serviceOrder = new ServiceOrder();
        //当前用户id
        Long userId = BaseContext.getCurrentId();
        //生成订单号
        String orderNo="YXT"+System.currentTimeMillis();
        //订单的超时时间
        LocalDateTime payExpire=LocalDateTime.now().plusMinutes(15);
      //把属性拷贝一下
        serviceOrder.setStatus(0);
        BeanUtils.copyProperties(userOrderDTO, serviceOrder);
        //查询对应项目的金额
        BigDecimal totalAmount=serviceItemMapper.getAmountById(userOrderDTO.getServiceItemId());
        serviceOrder.setUserId(userId);
        serviceOrder.setOrderNo(orderNo);
        serviceOrder.setPayStatus(0);
        serviceOrder.setTotalAmount(totalAmount);
        //下单的时间localDateTime
        serviceOrder.setCreateTime(LocalDateTime.now());
        serviceOrder.setPayExpireTime(payExpire);
        //保存到数据库，返回生成的订单id
        adminOrderManageMapper.insert(serviceOrder);
        //返回Id
        return serviceOrder.getId();
    }
    /**
     * 查询工单详情
     * @param id
     * @return
     */
    @Override
    public UserOrderVO getOrderDetail(Long id) {
        //根据工单id查询工单
        ServiceOrder order = adminOrderManageMapper.getOrderDetailById(id);
        if (order == null) {
            //判断工单是否存在
            throw new RuntimeException("工单不存在");
        }
        //封装返回
        UserOrderVO orderVO = UserOrderVO.builder()
                .id(order.getId())
                .orderNo(order.getOrderNo())
                .serviceItemName(order.getServiceItemName())
                .address(order.getAddress())
                .appointmentTime(order.getAppointmentTime())
                .totalAmount(order.getTotalAmount())
                .createTime(order.getCreateTime())
                .workerName(order.getWorkerName())
                .status(order.getStatus())
                .payTime(order.getPayTime())
                .orderStatusName(order.getOrderStatusName())
                .payExpireTime(order.getPayExpireTime())
                .build();
        return orderVO;
    }

    /**
     * 工单支付
     * @param id
     */
    @Transactional
    @Override
    public void payment(Long id) {
        //获取当前用户id
        Long userId = BaseContext.getCurrentId();
        //根据id查询对应的工单
        ServiceOrder order = adminOrderManageMapper.getOrderDetailById(id);
        if (order == null) {
            throw new RuntimeException("工单不存在");
        }
        //判断工单的用户id和当前用户id是否一致
        if(!order.getUserId().equals(userId)){
            throw new RuntimeException("无权操作此订单");
        }
        //判断订单的状态是否==0
        if(order.getStatus()!=0){
            throw new RuntimeException("订单状态不可支付");
        }
        if(order.getPayExpireTime().isBefore(LocalDateTime.now())){
            //判断支付时间是否过期
            throw new RuntimeException("订单超时请重新下单");
        }
        //更改支付状态和下单时间
        order.setPayStatus(1);
        order.setPayTime(LocalDateTime.now());
        //更新order数据库
        adminOrderManageMapper.update(order);
    }

    /**
     *  取消订单
     * @param id
     */
    @Transactional
    @Override
    public void cancelOrder(Long id) {
        //获取当前用户id
        Long userId = BaseContext.getCurrentId();
        //根据id查询订单
        ServiceOrder order = adminOrderManageMapper.getOrderDetailById(id);
        //判断订单是否存在
        if (order == null) {
            //不存在抛出错误
            throw new RuntimeException("订单不存在");
        }
        if(!order.getUserId().equals(userId)){
            throw new RuntimeException("无权操作此订单");
        }
        order.setCancelReason("用户主动取消");
        //调用状态机更改对应的数据以及更新日志
        orderStatusService.transition(order,4,"user",userId,"用户取消");
    }
    /**
     * 工单分页查询
     * @param userOrderDTO
     * @return
     */
    @Override
    public PageResult pageQuery(UserOrderDTO userOrderDTO) {
        //获取当前用户id
        Long userId = BaseContext.getCurrentId();
        //设置起始页和每页的大小
        PageHelper.startPage(userOrderDTO.getPage(),userOrderDTO.getPageSize());
        //查询数据
       Page<ServiceOrder> page=adminOrderManageMapper.getDataByUserIdAndStatus(userId,userOrderDTO.getStatus());
        //判断是否为空
        if (page==null || page.isEmpty()){
            return new PageResult(0, new ArrayList<>());
        }
        long total = page.getTotal();
        List<UserOrderVO> voList = new ArrayList<>();
        page.forEach(order -> {
            UserOrderVO vo = UserOrderVO.builder()
                    .id(order.getId())
                    .orderNo(order.getOrderNo())
                    .status(order.getStatus())
                    .orderStatusName(order.getOrderStatusName())
                    .serviceItemName(order.getServiceItemName())
                    .totalAmount(order.getTotalAmount())
                    .createTime(order.getCreateTime())
                    .build();
            voList.add(vo);
        });
       return PageResult.builder()
                .total(total)
                .records(voList)
                .build();
    }
    /**
     * 用户评价
     * @param id
     * @param userCommentDTO
     * @return
     */
    @Transactional
    @Override
    public void orderComment(Long id, UserCommentDTO userCommentDTO) {
        //获取当前用户id
        Long userId = BaseContext.getCurrentId();
        //查询对应的订单信息
        ServiceOrder order = adminOrderManageMapper.getOrderDetailById(id);
        //判断订单是否存在
        if (order == null) {
            //不存在抛出错误
            throw new RuntimeException("订单不存在");
        }
        //判断是不是当前用户的订单
        if(!order.getUserId().equals(userId)){
            throw new RuntimeException("无权操作此订单");
        }
        //判断当前订单状态是不是已完成
        if(order.getStatus()!=3){
            throw new RuntimeException("订单状态不能被评价");
        }
        if (order.getWorkerId()==null){
            throw new RuntimeException("订单状态异常");
        }
        //查看订单是否已经评价
        ServiceOrderComment exist= userOrderCommentMapper.getByOrderId(order.getId());
        if (exist != null) {
            throw new RuntimeException("订单已被评价，不可重复评价");
        }
        //提交评论到comment表
        ServiceOrderComment comment = ServiceOrderComment.builder()
                .orderId(order.getId())
                .userId(userId)
                .createTime(LocalDateTime.now())
                .content(userCommentDTO.getContent())
                .score(userCommentDTO.getRating())
                .workerId(order.getWorkerId())
                .build();
        //插入数据库
        userOrderCommentMapper.insert(comment);


    }
}
