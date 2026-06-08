package org.example.service.Impl;

import jakarta.annotation.Resource;
import org.example.context.BaseContext;
import org.example.entity.ServiceOrder;
import org.example.mapper.AdminOrderManageMapper;
import org.example.service.WorkerPendingService;
import org.example.vo.WorkerDetailVO;
import org.example.vo.WorkerPendingVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkerPendingServiceImpl implements WorkerPendingService {


    @Resource
    private AdminOrderManageMapper adminOrderManageMapper;

    /**
     * 待接单查询
     * @return
     */
    @Override
    public List<WorkerPendingVO> pendingOrder() {
        //查询当前工人的id，用来当作sql的条件查询经纬度距离
        Long workerId = BaseContext.getCurrentId();
        //查询接单状态为0的待接单订单
        List<ServiceOrder> order=adminOrderManageMapper.getPendingOrder(workerId);
        //新建List<workerPendingOrder>
        List<WorkerPendingVO> pending= new ArrayList<>();
        //通过forEach把赋值给list:pendingOrder
        for (ServiceOrder order1 : order) {
            WorkerPendingVO vo=new WorkerPendingVO();
            BeanUtils.copyProperties(order1,vo);
            pending.add(vo);
        }
        //封装返回
        return pending;
    }

    /**
     * 查看订单的详细信息
     * @param id
     * @return
     */
    @Override
    public WorkerDetailVO orderDetail(Long id) {
        //根据id查询订单
        ServiceOrder order = adminOrderManageMapper.getOrderDetailById(id);
        //判断订单是否存在
        if (order == null) {
            //不存在抛异常
            throw new RuntimeException("工单不存在");
        }
        //把查询到的订单封装到VO
       return WorkerDetailVO.builder()
                .address(order.getAddress())
                .appointmentTime(order.getAppointmentTime())
                .remark(order.getRemark())
               .orderNo(order.getOrderNo())
                .distance(order.getDistance())
                .serviceItemName(order.getServiceItemName())
                .totalAmount(order.getTotalAmount())
                .id(id)
               .orderStatus(order.getStatus())
               .orderStatusName(order.getOrderStatusName())
               .userPhone(order.getUserPhone())
               .userName(order.getUserName())
               .createTime(order.getCreateTime())
                .build();
    }
}
