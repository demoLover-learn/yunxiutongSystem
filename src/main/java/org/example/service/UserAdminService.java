package org.example.service;

import org.example.Result.PageResult;
import org.example.dto.AdminDTO.UserAdminPageQueryDTO;

public interface UserAdminService {


    /**
     * 用户管理分页查询
     * @param pageQueryDTO
     * @return
     */
    PageResult getUser(UserAdminPageQueryDTO pageQueryDTO);
    /**
     * 更改账号状态
     * @param id
     * @return
     */
    void stopOrStart(Long id,UserAdminPageQueryDTO pageQueryDTO);
}
