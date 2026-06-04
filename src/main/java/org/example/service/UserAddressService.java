package org.example.service;


import org.example.dto.UserDTO.UserAddressDTO;
import org.example.entity.UserAddress;
import org.example.vo.UserAddressVO;

import java.util.List;

public interface UserAddressService {
    /**
     * 查询当前用户地址
     * @param userId
     * @return
     */
    List<UserAddressVO> getAddress(Long userId);



    /**
     * 更新用户地址信息
     * @param id
     * @param userAddressDTO
     */
    void updateAddress(Long id, UserAddressDTO userAddressDTO);

    /**
     * 新增地址
     * @param userAddressDTO
     */
    void insert(UserAddressDTO userAddressDTO);

    /**
     * 删除地址信息
     * @param id
     */
    void deleteAddress(Long id);
}
