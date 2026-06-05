package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.entity.ServiceOrderComment;

@Mapper
public interface UserOrderCommentMapper {
    /**
     * 更新数据库
     * @param comment
     */
    void update(ServiceOrderComment comment);

    /**
     * 插入数据库
     * @param comment
     */
    void insert(ServiceOrderComment comment);

    /**
     * 通过订单id查询评价
     * @param id
     * @return
     */
    @Select("select * from service_order_comment where order_id=#{id}")
    ServiceOrderComment getByOrderId(Long id);
}
