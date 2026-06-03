package org.example.service.Impl;

import jakarta.annotation.Resource;
import org.example.context.BaseContext;
import org.example.dto.UserDTO.UserProfileDTO;
import org.example.entity.User;
import org.example.mapper.UserAuthMapper;
import org.example.service.UserProfileService;
import org.example.vo.UserLoginVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserProfileServiceImpl implements UserProfileService {

   @Resource
   private UserAuthMapper userAuthMapper;

    /**
     * 更新用户信息
     *
     * @param userProfileDTO
     * @return
     */
    @Override
    public UserLoginVO updateProfile(UserProfileDTO userProfileDTO) {
        //获取当前用户的id
        Long userId = BaseContext.getCurrentId();
        //新建实体类接收信息，属性拷贝
        User user = new User();
        BeanUtils.copyProperties(userProfileDTO, user);
        user.setId(userId);
        user.setUpdateTime(LocalDateTime.now());
        //更新数据库
       userAuthMapper.update(user);

        UserLoginVO userLoginVO = UserLoginVO.builder()
                .avatar(user.getAvatar())
                .nickname(user.getNickname())
                .build();
        return userLoginVO;
    }
}
