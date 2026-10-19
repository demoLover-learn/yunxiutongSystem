package org.example.mapper;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.dto.AdminDTO.OrderManageDTO;
import org.example.entity.ServiceOrder;
import org.example.vo.UserOrderVO;
import org.example.vo.WorkerBenchVO;

import java.util.List;

@Mapper
public interface AdminOrderManageMapper {

    /**
     * 工单管理分页查询
     * @param orderManageDTO
     * @return
     */
    Page<ServiceOrder> getData(OrderManageDTO orderManageDTO);

    /**
     * 根据id查询工单详细信息
     * @param id
     * @return
     */
    ServiceOrder getOrderDetailById(Long id);

    /**
     * 动态更新数据库
     * @param order
     */
    int update(ServiceOrder order);

    /**
     * 插入数据库
     * @param order
     */
    void insert(ServiceOrder order);

    /**
     * 用户端分页查询工单分类
     * @param userId
     * @param status
     * @return
     */
    Page<ServiceOrder> getDataByUserIdAndStatus(Long userId, String status);

    /**
     * 待接单状态查询
     * @return
     */
    List<ServiceOrder> getPendingOrder(Long workerId);

    /**
     * 工人端根据用户名和状态查询详细信息
     * @param workerId
     * @param status
     * @return
     */
    List<ServiceOrder> getDataByWorkerIdAndStatus(Long workerId, Integer status);

    /**
     * 工作台展示
     * @param workerId
     * @return
     */
   WorkerBenchVO getbench(Long workerId);

    /**
     * 查询待处理的订单
     * @param workerId
     * @return
     */

    List<ServiceOrder> getServiceByWorkerId(Long workerId);

    /**
     * 定时任务取消未支付订单
     * @return
     */
    @Select("select *, order_status as status from service_order where order_status = 0 and pay_status = 0 and pay_expire_time < now() limit 500")
    List<ServiceOrder> getTimeoutOrders();

    /**
     * 定时取消已支付但是无人接单的订单
     * @return
     */
    @Select("select *, order_status as status from service_order " +
            "where order_status = 0 and pay_status = 1 " +
            "and pay_time < now() - interval 30 minute limit 500")
    List<ServiceOrder> getUnacceptedPaidOrders();



    /**
     * 根据用户id查询工具
     * @param userId
     * @return
     */
    @Select("select *, order_status as status from service_order where user_id=#{userId} order by create_time desc limit 10")
    List<UserOrderVO> listByUserId(Long userId);

    /**
     * 根据订单id查询对应的订单
     * @param orderNo
     * @return
     */

    ServiceOrder getByOrderNo(String orderNo);

    /**
     * 查询工人服务中的工单数目
     * @param workerId
     * @return
     */
    @Select("select count(*) from service_order where worker_id=#{workerId} and order_status=2")
    int countServingOrders(Long workerId);

}
