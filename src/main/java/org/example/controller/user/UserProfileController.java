package org.example.controller.user;

import com.sun.source.doctree.SummaryTree;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.Result.Result;
import org.example.dto.UserDTO.UserProfileDTO;
import org.example.service.UserProfileService;
import org.example.vo.UserLoginVO;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Tag(name = "用户端头像上传")
@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserProfileController {

    @Resource
    private UserProfileService userProfileService;
    /**
     * 更新用户信息
     * @param userProfileDTO
     * @return
     */
    @Operation(summary = "更新用户信息")
    @PutMapping("/profile")
    public Result<UserLoginVO> updateProfile(@RequestBody UserProfileDTO userProfileDTO){
        UserLoginVO userLoginVO=userProfileService.updateProfile(userProfileDTO);
        return Result.success(userLoginVO);
    }



}
