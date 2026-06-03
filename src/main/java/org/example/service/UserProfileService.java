package org.example.service;

import org.example.dto.UserDTO.UserProfileDTO;
import org.example.vo.UserLoginVO;

public interface UserProfileService {
    /**
     * 更新用户信息
     *
     * @param userProfileDTO
     * @return
     */
    UserLoginVO updateProfile(UserProfileDTO userProfileDTO);
}
