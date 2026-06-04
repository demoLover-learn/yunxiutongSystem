package org.example.service;

import org.example.Result.PageResult;
import org.example.dto.AdminDTO.ServiceItemPageQueryDTO;
import org.example.vo.ServiceItemVO;

import java.util.List;

public interface UserServiceService {

    /**
     * 分页查询
     * @param query
     * @return
     */
   List<ServiceItemVO> pageQuery(ServiceItemPageQueryDTO query);

    /**
     * 查询项目详细信息
     * @param id
     * @return
     */
    ServiceItemVO getDetail(Long id);
}
