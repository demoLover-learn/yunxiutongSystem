package org.example.service.Impl;

import jakarta.annotation.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.example.context.BaseContext;
import org.example.entity.ServiceOrder;
import org.example.entity.Worker;
import org.example.mapper.AdminOrderManageMapper;
import org.example.mapper.WorkerAdminMapper;
import org.example.service.OrderStatusService;
import org.example.service.WorkerGrabService;
import org.example.util.RedisLock;
import org.example.util.impl.RedisLockImpl;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class WorkerGrabServiceImpl implements WorkerGrabService {


    @Resource
    private AdminOrderManageMapper adminOrderManageMapper;
    @Resource
    private OrderStatusService orderStatusService;
    @Resource
    private WorkerAdminMapper workerAdminMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;



    /**
     * 工人抢单
     * @param id
     * @return
     */
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
        RedisLock redisLock=new RedisLockImpl("lock:order:"+id,stringRedisTemplate);
        //使用乐观锁抢单
        try{
            boolean b = redisLock.tryLock(10L);
            //判断是否拿到了
            if (!b){
                throw new RuntimeException(" 抢单繁忙，请稍后重试");
            }
            //根据id查询订单
            ServiceOrder orders = adminOrderManageMapper.getOrderDetailById(id);
            if (orders == null) throw new RuntimeException("工单不存在");
            if (orders.getStatus() != 0) throw new RuntimeException("工单已被抢");
            orders.setWorkerId(workerId);
            orderStatusService.transition(orders,1,"worker",
                    workerId,"工人:"+worker.getName()+"抢到了订单");
        }finally {
            redisLock.unlock();
        }
    }
}
