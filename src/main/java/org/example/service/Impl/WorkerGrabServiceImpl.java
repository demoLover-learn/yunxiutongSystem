package org.example.service.Impl;

import jakarta.annotation.Resource;
import org.example.context.BaseContext;
import org.example.entity.ServiceOrder;
import org.example.entity.Worker;
import org.example.mapper.AdminOrderManageMapper;
import org.example.mapper.WorkerAdminMapper;
import org.example.service.OrderStatusService;
import org.example.service.WorkerGrabService;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.concurrent.TimeUnit;

@Service
public class WorkerGrabServiceImpl implements WorkerGrabService {


    @Resource
    private RedissonClient redissonClient;
    @Resource
    private AdminOrderManageMapper adminOrderManageMapper;
    @Resource
    private OrderStatusService orderStatusService;
    @Resource
    private WorkerAdminMapper workerAdminMapper;




    /**
     * 工人抢单
     * @param id
     * @return
     */
    @Transactional
    @Override
    public void grabOrder(Long id) {
        //获取当前用户id
        Long workerId = BaseContext.getCurrentId();
        //查询工人信息
        Worker worker = workerAdminMapper.getByWorkerId(workerId);
        if (worker.getServiceStatus()!=1){
            throw new RuntimeException("当前状态不可接单");
        }
        if (worker.getStatus()==0){
            throw new RuntimeException("封禁账号无法操作");
        }
        //获取redission锁
        RLock lock = redissonClient.getLock("lock:order:" + id);
        //使用redission抢单
        try{
//
            boolean acquired = lock.tryLock(10, TimeUnit.SECONDS);

            //判断是否拿到了
            if (!acquired){
                throw new RuntimeException(" 抢单繁忙，请稍后重试");
            }
            // ⑨ 关键：不在 finally 里 unlock，注册到事务回调，事务提交后才释放锁
            TransactionSynchronizationManager.registerSynchronization(
                    new TransactionSynchronization() {
                        @Override
                        public void afterCompletion(int status) {
                            if (lock.isHeldByCurrentThread()) {
                                lock.unlock();
                            }
                        }
                    }
            );
            //根据id查询订单
            ServiceOrder orders = adminOrderManageMapper.getOrderDetailById(id);
            if (orders == null) throw new RuntimeException("工单不存在");
            if (orders.getStatus() != 0) throw new RuntimeException("工单已被抢");
            orders.setWorkerId(workerId);
            orderStatusService.transition(orders,1,"worker",
                    workerId,"工人:"+worker.getName()+"抢到了订单");

        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
            throw new RuntimeException("抢单被中断");
        }
    }
}
