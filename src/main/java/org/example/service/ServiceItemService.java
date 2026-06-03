package org.example.service;

import org.example.Result.PageResult;
import org.example.dto.AdminDTO.ServiceItemPageQueryDTO;
import org.example.vo.ServiceItemVO;

public interface ServiceItemService {

    /**
     * 分页查询
     * @param serviceItemPageQueryDTO
     * @return
     */
    PageResult pageQuery(ServiceItemPageQueryDTO serviceItemPageQueryDTO);
    /**
     * 新增项目
     * @param serviceItemVO
     * @return
     */
    Long insert(ServiceItemVO serviceItemVO);

    /**
     * 更新项目
     * @param serviceItemVO
     */
    void update(Long id,ServiceItemVO serviceItemVO);
    /**
     * 更改服务项目状态
     * @param id
     * @param serviceItemVO
     * @return
     */
    void stopOrStart(Long id, ServiceItemVO serviceItemVO);

    /**
     * 删除项目订单
     * @param id
     */
    void delete(Long id);
}
