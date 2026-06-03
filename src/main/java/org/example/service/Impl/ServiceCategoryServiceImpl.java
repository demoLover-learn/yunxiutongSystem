package org.example.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;

import org.example.Result.PageResult;
import org.example.dto.AdminDTO.ServiceCategoryDTO;
import org.example.entity.ServiceCategory;
import org.example.mapper.ServiceCategoryMapper;
import org.example.service.ServiceCategoryService;
import org.example.vo.ServiceCategoryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ServiceCategoryServiceImpl implements ServiceCategoryService {
    @Resource
    private ServiceCategoryMapper serviceCategoryMapper;


    /**
     * 新增分类
     * @param serviceCategoryVO
     * @return
     */
    @Override
    public Long insert(ServiceCategoryVO serviceCategoryVO) {
        ServiceCategory category = new ServiceCategory();
        BeanUtils.copyProperties(serviceCategoryVO, category);
        category.setCreateTime(LocalDateTime.now());
        category.setStatus(1);
        Long l=serviceCategoryMapper.insert(category);
        return l;
    }

    /**
     * 服务分类管理
     * @param serviceCategoryDTO
     * @return
     */
    @Override
    public PageResult getServiceCategory(ServiceCategoryDTO serviceCategoryDTO) {
        //开始页码和页码中内容的数量
        PageHelper.startPage(serviceCategoryDTO.getPage(),serviceCategoryDTO.getPageSize());
        //查询对应的数据
        Page<ServiceCategory> result =serviceCategoryMapper.getServiceCategory(serviceCategoryDTO);
        //取出对应的数据
        long total = result.getTotal();
        List records = result.getResult();

        return  new PageResult(total,records);
    }
    /**
     * 编辑服务分类信息
     * @param id

     * @return
     */
    @Override
    public void updateServiceCategory(Long id, ServiceCategoryVO serviceCategoryVO) {
        //根据id获取对应的内容
        ServiceCategory category= serviceCategoryMapper.getByCategoryId(id);
        category.setName(serviceCategoryVO.getName());
        category.setSort(serviceCategoryVO.getSort());
        //更新数据库
       serviceCategoryMapper.update(category);

    }
    /**
     * 账号的禁用和启用
     * @param id
     * @param serviceCategoryVO
     */
    @Override
    public void updateStatus(Long id, ServiceCategoryVO serviceCategoryVO) {
        //根据id获取工人对象
        ServiceCategory category = serviceCategoryMapper.getByCategoryId(id);
        //更新状态
        category.setStatus(serviceCategoryVO.getStatus());
        //更新数据库
        serviceCategoryMapper.update(category);

    }
    /**
     * 删除对应的服务分类
     * @param id
     */
    @Override
    public void deleteById(Long id) {
        serviceCategoryMapper.deleteById(id);
    }
    /**
     * 查询所有分类
     * @return
     */
    @Override
    public List<ServiceCategory> getAllCategorys() {

      List<ServiceCategory> categories= serviceCategoryMapper.getAll();
 return categories;
    }
}
