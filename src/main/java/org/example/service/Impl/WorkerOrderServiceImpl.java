package org.example.service.Impl;

import jakarta.annotation.Resource;
import org.example.context.BaseContext;
import org.example.dto.WorkerDTO.WorkerOrderDTO;
import org.example.entity.ServiceOrder;
import org.example.mapper.AdminOrderManageMapper;
import org.example.service.OrderStatusService;
import org.example.service.WorkerOrderService;
import org.example.vo.WorkerDetailVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkerOrderServiceImpl implements WorkerOrderService {

    @Resource
    private AdminOrderManageMapper adminOrderManageMapper;
    @Resource
    private OrderStatusService orderStatusService;
    /**
     * 工人端工单查询
     * @param vo
     * @return
     */
    @Override
    public List<WorkerDetailVO> orderList(WorkerOrderDTO vo) {
        //获取当前用户id
        Long workerId = BaseContext.getCurrentId();
        Integer status = vo.getStatus();
        //根据用户id和状态查出所有订单的详细信息
       List<ServiceOrder> ser=adminOrderManageMapper.getDataByWorkerIdAndStatus(workerId,status);
        //判断是否为空
        if (ser==null ||ser.isEmpty()){
            //为空返回空集合
            return new ArrayList<>();
        }
        List<WorkerDetailVO> vos=new ArrayList<>(ser.size());
        //copy属性到List<WorkerDetailVO>
        for (ServiceOrder serviceOrder : ser) {
            WorkerDetailVO worker = WorkerDetailVO.builder()
                    .id(serviceOrder.getId())
                    .orderNo(serviceOrder.getOrderNo())
                    .userName(serviceOrder.getUserName())
                    .userPhone(serviceOrder.getUserPhone())
                    .orderStatusName(serviceOrder.getOrderStatusName())
                    .orderStatus(serviceOrder.getStatus())
                    .createTime(serviceOrder.getCreateTime())
                    .address(serviceOrder.getAddress())
                    .serviceItemName(serviceOrder.getServiceItemName())
                    .remark(serviceOrder.getRemark())
                    .appointmentTime(serviceOrder.getAppointmentTime())
                    .totalAmount(serviceOrder.getTotalAmount())
                    .build();
            vos.add(worker);
        }

        //返回
        return vos;
    }

    /**
     * 开始服务
     * @param id
     * @return
     */
    @Override
    public void startServer(Long id) {
        //查询当前操作用户id
        Long workerId = BaseContext.getCurrentId();
        //根据订单id查询订单是否存在
        ServiceOrder order = adminOrderManageMapper.getOrderDetailById(id);
        //不存在抛异常
        if (order==null){
            throw new RuntimeException("工单不存在");
        }
        //校验当前订单是不是该工人的
        if(!workerId.equals(order.getWorkerId())){
            throw new RuntimeException("无权操作此工单");
        }
        //使用状态机进行状态转换
        orderStatusService.transition(order,2,"worker",
                workerId,"工人开始服务");


    }
    /**
     * 完成服务
     * @param id
     * @return
     */
    @Override
    public void completeOrder(Long id) {
        //查询当前操作工人
        Long workerId = BaseContext.getCurrentId();
        //查询当前订单是否存在
        ServiceOrder order = adminOrderManageMapper.getOrderDetailById(id);
        if (order==null){
            throw new RuntimeException("当前工单不存在");
        }
        //判断该工单是否属于当前操作工人
        if(!workerId.equals(order.getWorkerId())){
            throw new RuntimeException("无权操作该订单");
        }
        //调用状态机进行状态转换
        orderStatusService.transition(order,3,"worker",workerId,"工单被完成");

    }
}
