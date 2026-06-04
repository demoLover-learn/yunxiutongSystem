package org.example.mapper;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.dto.AdminDTO.ServiceItemPageQueryDTO;
import org.example.entity.ServiceItem;


@Mapper
public interface ServiceItemMapper {

    /**
     * 分页查询
     * @param serviceItemPageQueryDTO
     * @return
     */
    Page<ServiceItem> selectAll(ServiceItemPageQueryDTO serviceItemPageQueryDTO);

    /**
     * 向ServiceCategory中插入数据
     * @param serviceItem
     * @return
     */
    Long insert(ServiceItem serviceItem);

    /**
     * 更新数据库
     * @param serviceItem
     */
    void update(ServiceItem serviceItem);

    /**
     * 根据id查询数据
     * @param id
     * @return
     */
    @Select("select * from service_item where id=#{id}")
    ServiceItem getById(Long id);

    /**
     * 删除项目订单
     * @param id
     */
    @Delete("delete from service_item where id=#{id}")
    void deleteById(Long id);

    /**
     * 用户端分页查询，过滤没被禁用的项目
     * @param query
     * @return
     */
    Page<ServiceItem> selectAllowed(ServiceItemPageQueryDTO query);
}
