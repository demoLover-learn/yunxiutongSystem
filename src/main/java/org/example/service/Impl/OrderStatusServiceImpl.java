package org.example.service.Impl;

import jakarta.annotation.Resource;
import org.example.entity.ServiceOrder;
import org.example.entity.ServiceOrderLog;
import org.example.entity.Worker;
import org.example.mapper.AdminOrderManageMapper;
import org.example.mapper.ServiceOrderLogMapper;
import org.example.mapper.WorkerAdminMapper;
import org.example.service.OrderStatusService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class OrderStatusServiceImpl implements OrderStatusService {
    @Resource
    private AdminOrderManageMapper adminOrderManageMapper;
    @Resource
    private WorkerAdminMapper workerAdminMapper;
    @Resource
    private ServiceOrderLogMapper serviceOrderLogMapper;

    /**
     * 状态机的设置
     * @param order
     * @param targetStatus
     * @param operatorType
     * @param operatorId
     * @param remark
     */
    @Transactional
    @Override
    public void transition(ServiceOrder order, Integer targetStatus, String operatorType,
                           Long operatorId, String remark) {
        //查询需要更改的工人的信息2.工人id是在判断这个状态之前就设定进order里面的，所致可以直接查到
        Worker worker = workerAdminMapper.getByWorkerId(order.getWorkerId());

        //抽取初始状态码
        Integer fromStatus = order.getStatus();
        //② isAllowed(from, target) 校验 → 不合法抛异常
        if(!isAllowed(fromStatus,targetStatus)){
            throw new RuntimeException("工单状态不支持更改");
        }
  //③ 执行业务变更（switch targetStatus）→ 改时间字段 + 工人状态
        switch (targetStatus){
            case 1:
                    order.setStatus(targetStatus);
                    order.setReceiveTime(LocalDateTime.now());
                break;
            case 2:
                if(worker.getServiceStatus()!=1){
                    throw new RuntimeException("工人正在服务中，无法开始新服务");
                };
                order.setStatus(targetStatus);
                order.setStartServiceTime(LocalDateTime.now());
                if(fromStatus==1){
                    worker.setServiceStatus(2);}

                break;
            case 3:
                order.setStatus(targetStatus);
                order.setFinishServiceTime(LocalDateTime.now());
                //0:下线休息 1：可接单 2：服务中
                if(fromStatus==2){
                worker.setServiceStatus(1);}
                break;
             case 4:
                 order.setStatus(targetStatus);
                 order.setCancelTime(LocalDateTime.now());
                 if(fromStatus==1){
                 worker.setServiceStatus(1);}
                 break;
        }
        if(worker!=null){
        worker.setUpdateTime(LocalDateTime.now());
        //更新工人数据库
        workerAdminMapper.updateWorker(worker);}
        order.setUpdateTime(LocalDateTime.now());
        //更新工单数据库
        adminOrderManageMapper.update(order);

  //④ INSERT service_order_log
        ServiceOrderLog orderLog = ServiceOrderLog.builder()
                .toStatus(targetStatus)
                .createTime(LocalDateTime.now())
                .fromStatus(fromStatus)
                .orderId(order.getId())
                .content(remark)
                .operatorId(operatorId)
                .operatorType(operatorType).build();
        serviceOrderLogMapper.insert(orderLog);
    }

    private boolean isAllowed(Integer from,Integer target){
        if((from==0 && target==1) || (from==0&&target==4)
        || (from==1 && target==2) || (from==2 && target==3)
            ||(from==1 && target==4)){
            return true;
        }else {
            return false;
        }

    }
}
