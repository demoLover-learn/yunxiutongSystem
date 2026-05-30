package org.example.mapper;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.dto.ServiceCategoryDTO;
import org.example.entity.ServiceCategory;
import org.example.vo.ServiceCategoryVO;

import java.util.List;

@Mapper
public interface ServiceCategoryMapper {



    /**
     * 查询服务分类管理的数据
     * @param serviceCategoryDTO
     * @return
     */
    @Select("select * from service_category")
    Page<ServiceCategory> getServiceCategory(ServiceCategoryDTO serviceCategoryDTO);
    /**
     * 根据编辑服务id查询分类信息
     * @param id
     * @return
     */
    @Select("select * from service_category where id=#{id}")
    ServiceCategory getByCategoryId(Long id);

    /**
     * 更新服务分类数据库
     * @param category
     */
    void update(ServiceCategory category);
    /**
     * 删除对应的服务分类
     * @param id
     */
    @Delete("delete from service_category where id=#{id}")
    void deleteById(Long id);

    /**
     * 新增分类
     * @param category
     * @return
     */
    Long insert(ServiceCategory category);

    /**
     * 查询所有信息
     * @return
     */
    @Select("select * from service_category;")
    List<ServiceCategory> getAll();


}
