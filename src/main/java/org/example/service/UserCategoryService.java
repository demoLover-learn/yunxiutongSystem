package org.example.service;

import org.example.vo.ServiceCategoryVO;

import java.util.List;

public interface UserCategoryService {

    /**
     * 查询服务类别
     * @return
     */
    List<ServiceCategoryVO> getAllCategories();
}
