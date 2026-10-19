package org.example.service.Impl;


import org.example.handler.BusinessException;
import jakarta.annotation.Resource;
import org.example.entity.GrabOrderRecord;
import org.example.entity.ServiceOrder;
import org.example.entity.ServiceOrderLog;
import org.example.entity.Worker;
import org.example.mapper.AdminOrderManageMapper;
import org.example.mapper.GrabOrderRecordMapper;
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
    private GrabOrderRecordMapper grabOrderRecordMapper;
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
        // isAllowed(from, target) 校验 → 不合法抛异常
        if(!isAllowed(fromStatus,targetStatus)){
            throw new BusinessException("工单状态不支持更改");
        }
  // 执行业务变更（switch targetStatus）→ 改时间字段 + 工人状态
        switch (targetStatus){
            case 1://已接单
                    order.setStatus(targetStatus);
                    order.setReceiveTime(LocalDateTime.now());
                break;
            case 2://服务中
                if(worker.getServiceStatus()!=1){
                    throw new BusinessException("工人正在服务中，无法开始新服务");
                };
                order.setStatus(targetStatus);
                order.setStartServiceTime(LocalDateTime.now());
                if(fromStatus==1){
                    worker.setServiceStatus(2);}

                break;
            case 3://已完成
                order.setStatus(targetStatus);
                order.setFinishServiceTime(LocalDateTime.now());
                //0:下线休息 1：可接单 2：服务中
                if(fromStatus==2){
                worker.setServiceStatus(1);}
                break;
             case 4://已取消
                 order.setStatus(targetStatus);
                //先判断订单是否已支付
                 if (Integer.valueOf(1).equals(order.getPayStatus())) {
                     //支付的话直接设置订单状态为退款
                     order.setPayStatus(2);
                 }
                 order.setCancelTime(LocalDateTime.now());
                 // 取消后必须释放工单归属，否则"已接单后被取消"的单会一直挂在工人名下
                 order.setWorkerId(null);
                 // 只有"这个工人手上没有正在服务的单"才放回可接单
                 if (fromStatus == 1 && worker != null
                         && adminOrderManageMapper.countServingOrders(worker.getId()) == 0) {
                     worker.setServiceStatus(1);
                 }
                 break;
        }
        if(worker!=null){
        worker.setUpdateTime(LocalDateTime.now());
        //更新工人数据库
        workerAdminMapper.updateWorker(worker);}
        order.setUpdateTime(LocalDateTime.now());
        //更新工单数据库
        order.setFromStatus(fromStatus);
        int rows = adminOrderManageMapper.update(order);
        if (rows == 0) {
            throw new BusinessException("工单状态已变更，请刷新后重试");
        }

        // INSERT service_order_log
        ServiceOrderLog orderLog = ServiceOrderLog.builder()
                .toStatus(targetStatus)
                .createTime(LocalDateTime.now())
                .fromStatus(fromStatus)
                .orderId(order.getId())
                .content(remark)
                .operatorId(operatorId)
                .operatorType(operatorType).build();
        serviceOrderLogMapper.insert(orderLog);

        // 接单记录：抢单 / 派单成功后落一条，取消时 worker_id 会被清空，
        // 工人端"已取消"历史靠这张表回溯"这单当初是我接的"
        if (targetStatus == 1 && order.getWorkerId() != null) {
            grabOrderRecordMapper.insert(new GrabOrderRecord(
                    null, order.getId(), order.getWorkerId(), 1, LocalDateTime.now()));
        }
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
