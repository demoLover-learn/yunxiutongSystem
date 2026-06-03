package org.example.service;

import org.example.Result.PageResult;
import org.example.dto.AdminDTO.ServiceCategoryDTO;
import org.example.entity.ServiceCategory;
import org.example.vo.ServiceCategoryVO;

import java.util.List;

public interface ServiceCategoryService {

    /**
     * 新增分类
     * @param serviceCategoryVO
     * @return
     */
    Long insert(ServiceCategoryVO serviceCategoryVO);
    /**
     * 服务分类管理
     * @param serviceCategoryDTO
     * @return
     */
    PageResult getServiceCategory(ServiceCategoryDTO serviceCategoryDTO);
    /**
     * 编辑服务分类信息
     * @param id

     * @return
     */
    void updateServiceCategory(Long id, ServiceCategoryVO serviceCategoryVO);

    /**
     * 账号的禁用和启用
     * @param id
     * @param serviceCategoryVO
     */
    void updateStatus(Long id, ServiceCategoryVO serviceCategoryVO);
    /**
     * 删除对应的服务分类
     * @param id
     */
    void deleteById(Long id);
    /**
     * 查询所有分类
     * @return
     */
    List<ServiceCategory> getAllCategorys();
}
