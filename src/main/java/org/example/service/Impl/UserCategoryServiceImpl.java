package org.example.service.Impl;

import jakarta.annotation.Resource;
import org.example.entity.ServiceCategory;
import org.example.mapper.ServiceCategoryMapper;
import org.example.service.UserCategoryService;
import org.example.vo.ServiceCategoryVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserCategoryServiceImpl implements UserCategoryService {

    @Resource
    private ServiceCategoryMapper serviceCategoryMapper;

    /**
     * 查询服务类别
     * @return
     */
    @Override
    public List<ServiceCategoryVO> getAllCategories() {
        //调用mapper查询所有的服务类别
        List<ServiceCategory> all = serviceCategoryMapper.getEnable();
        //然后封装到ServiceCategoryVO中
        List<ServiceCategoryVO> vos = new ArrayList<>(all.size());
        all.forEach(serviceCategory -> {
            ServiceCategoryVO serviceCategoryVO = new ServiceCategoryVO();
            serviceCategoryVO.setId(serviceCategory.getId());
            serviceCategoryVO.setSort(serviceCategory.getSort());
            serviceCategoryVO.setName(serviceCategory.getName());
            vos.add(serviceCategoryVO);
        });

        return vos;
    }
}
