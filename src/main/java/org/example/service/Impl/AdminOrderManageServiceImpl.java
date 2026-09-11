package org.example.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import org.example.Result.PageResult;
import org.example.context.BaseContext;
import org.example.dto.AdminDTO.OrderManageDTO;
import org.example.entity.ServiceOrder;
import org.example.entity.Worker;
import org.example.mapper.AdminOrderManageMapper;
import org.example.mapper.WorkerAdminMapper;
import org.example.service.AdminOrderManageService;
import org.example.service.OrderStatusService;
import org.example.util.RedisLock;
import org.example.util.impl.RedisLockImpl;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class AdminOrderManageServiceImpl implements AdminOrderManageService {
    @Resource
    private AdminOrderManageMapper adminOrderManageMapper;
    @Resource
    private WorkerAdminMapper workerAdminMapper;
    @Resource
    private OrderStatusService  orderStatusService;
    @Autowired
    private RedissonClient redissonClient;
    /**
     * 工单管理分页查询
     * @param orderManageDTO
     * @return
     */
    @Override
    public PageResult pageQuery(OrderManageDTO orderManageDTO) {
        //分页参数守卫：空或非法值回退为第1页、每页10条
        int pageNum = orderManageDTO.getPage() == null || orderManageDTO.getPage() < 1 ? 1 : orderManageDTO.getPage();
        int pageSize = orderManageDTO.getPageSize() == null || orderManageDTO.getPageSize() < 1 ? 10 : orderManageDTO.getPageSize();
        PageHelper.startPage(pageNum,pageSize);

        Page<ServiceOrder> result= adminOrderManageMapper.getData(orderManageDTO);

        long total = result.getTotal();
        List<ServiceOrder> result1 = result.getResult();
        return new PageResult(total,result1);
    }
    /**
     * 获取订单的详细信息
     * @param id
     * @return
     */
    @Override
    public ServiceOrder getOrderDetailById(Long id) {
       //根据id查询对应的信息
        ServiceOrder serviceOrder=adminOrderManageMapper.getOrderDetailById(id);
        if(serviceOrder==null){
            throw new RuntimeException("工单不存在");
        }
    return serviceOrder;
    }
    /**
     * 派单接口
     * @param id
     */
    @Transactional
    @Override
    public void getOrder(Long id,Long workerId) {

        Long userId = BaseContext.getCurrentId();

        RLock lock = redissonClient.getLock("lock:order:" + id);
        try{
            boolean acquired = lock.tryLock(10, TimeUnit.SECONDS);
            if(!acquired){
                throw new RuntimeException("派单繁忙，请重试");
            }
            // ⑨ 关键：不在 finally 里 unlock，注册到事务回调，事务提交后才释放锁
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCompletion(int status) {
                    if (lock.isHeldByCurrentThread()) {
                        lock.unlock();
                    }
                }
            });
        //根据工单id查询对应的单子
        ServiceOrder order = adminOrderManageMapper.getOrderDetailById(id);
        if(order==null){
            throw new RuntimeException("工单不存在");
        }
            Worker worker = workerAdminMapper.getByWorkerId(workerId);
        if(worker==null){
            throw new RuntimeException("工人不存在");
        }
            order.setWorkerId(workerId);
        //调用状态机更改对应的数据以及更新日志
        orderStatusService.transition(order,1,"admin",userId,"管理员派单给:"+worker.getName());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("派单被中断");
        }
    }

    /**
     * 取消订单
     * @param id
     */
    @Transactional
    @Override
    public void cancelOrder(Long id) {
        Long userId = BaseContext.getCurrentId();
        //根据订单查询id
        ServiceOrder order = adminOrderManageMapper.getOrderDetailById(id);

        if(order==null){
            throw new RuntimeException("工单不存在");
        }
        order.setCancelReason("管理员取消");
        //调用状态机更改对应的数据以及更新日志
        orderStatusService.transition(order,4,"admin",userId,"管理员取消");

    }
}
