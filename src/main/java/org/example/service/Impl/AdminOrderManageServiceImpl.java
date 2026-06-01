package org.example.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import org.example.Result.PageResult;
import org.example.context.BaseContext;
import org.example.dto.OrderManageDTO;
import org.example.dto.WorkerAdminPageQueryDTO;
import org.example.entity.ServiceOrder;
import org.example.entity.Worker;
import org.example.mapper.AdminOrderManageMapper;
import org.example.mapper.WorkerAdminMapper;
import org.example.service.AdminOrderManageService;
import org.example.util.RedisLock;
import org.example.util.impl.RedisLockImpl;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminOrderManageServiceImpl implements AdminOrderManageService {
    @Resource
    private AdminOrderManageMapper adminOrderManageMapper;
    @Resource
    private WorkerAdminMapper workerAdminMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    /**
     * 工单管理分页查询
     * @param orderManageDTO
     * @return
     */
    @Override
    public PageResult pageQuery(OrderManageDTO orderManageDTO) {
        PageHelper.startPage(orderManageDTO.getPage(),orderManageDTO.getPageSize());

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
        RedisLock redisLock = new RedisLockImpl("order:"+id,stringRedisTemplate);
        boolean isLock = redisLock.tryLock(10L);
        if(!isLock){
            throw new RuntimeException("派单繁忙，请重试");
        }
        try{
        //根据工单id查询对应的单子
        ServiceOrder order = adminOrderManageMapper.getOrderDetailById(id);
        if(order==null){
            throw new RuntimeException("工单不存在");
        }
        Integer status = order.getStatus();
        if(status!=0){
            throw new RuntimeException("该工单不可以被派单");
        }
        //根据师傅的名字查询出师傅的id
        Worker worker= workerAdminMapper.getByWorkerId(workerId);
        if(worker==null||worker.getId()==null){
            throw new RuntimeException("该师傅不存在");
        }
        //状态改为已接单
        order.setStatus(1);
        //添加服务人员字段的Id,因为前端的字段不属于数据库字段，所以传入工人id，分页查询可以查到
        order.setWorkerId(workerId);
        order.setUpdateTime(LocalDateTime.now());
        order.setReceiveTime(LocalDateTime.now());
        //更新工人信息
        worker.setServiceStatus(2);
        worker.setUpdateTime(LocalDateTime.now());
        workerAdminMapper.updateWorker(worker);
        //更新工单
        adminOrderManageMapper.update(order);}
        finally {
            redisLock.unlock();
        }
    }

    /**
     * 取消订单
     * @param id
     */
    @Transactional
    @Override
    public void cancelOrder(Long id) {
        //根据订单查询id
        ServiceOrder order = adminOrderManageMapper.getOrderDetailById(id);

        if(order==null){
            throw new RuntimeException("工单不存在");
        }
        //判断是否有工人接单
        Integer status = order.getStatus();
        if(status==4){
            //有如果状态大于1，抛出错误，无法取消
            throw new RuntimeException("订单已经取消，无法重复取消");
        }
        if(status==3){
            //有如果状态大于1，抛出错误，无法取消
            throw new RuntimeException("工单已完成，无法取消");
        }
        if(status==2){
            //有如果状态大于1，抛出错误，无法取消
            throw new RuntimeException("工单正在执行，无法取消");
        }
        if (status==1&&order.getWorkerId()!=null){
            //查询工人的信息,把接单工人状态设置为1待接单
            Worker worker = workerAdminMapper.getByWorkerId(order.getWorkerId());
            if(worker!=null){
            worker.setServiceStatus(1);
            worker.setUpdateTime(LocalDateTime.now());
            workerAdminMapper.updateWorker(worker);}
            //如果状态等于1，设置状态为已取消
            order.setStatus(4);
        }
        if(status==0){
            order.setStatus(4);
        }
        // 工单的更新时间，取消时间，取消理由
        order.setCancelReason("管理员取消");
        order.setUpdateTime(LocalDateTime.now());
        order.setCancelTime(LocalDateTime.now());
        //更新数据库
        adminOrderManageMapper.update(order);

    }
}
