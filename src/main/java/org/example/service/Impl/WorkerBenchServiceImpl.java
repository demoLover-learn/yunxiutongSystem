package org.example.service.Impl;

import jakarta.annotation.Resource;
import org.example.context.BaseContext;
import org.example.entity.ServiceOrder;
import org.example.mapper.AdminOrderManageMapper;
import org.example.service.WorkerBenchService;
import org.example.vo.WorkerBenchVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WorkerBenchServiceImpl implements WorkerBenchService {

    @Resource
    private AdminOrderManageMapper adminOrderManageMapper;

    /**
     * 工作台展示
     * @return
     */
    @Override
    public WorkerBenchVO showWorkbench() {
        //获取当前用户
        Long workerId = BaseContext.getCurrentId();
        //调用mapper查询对应的值
        WorkerBenchVO vo=adminOrderManageMapper.getbench(workerId);
        //查询正在服务中的工人订单
        List<ServiceOrder> orders=adminOrderManageMapper.getServiceByWorkerId(workerId);
        if (orders==null || orders.isEmpty()){
            return vo;
        }
        //把查到的正在服务中的订单插入vo
        vo.setActiveOrders(orders);
        //封装数据
        return vo;
    }
}
