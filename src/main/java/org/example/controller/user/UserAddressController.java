package org.example.controller.user;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.Result.Result;
import org.example.context.BaseContext;
import org.example.dto.UserDTO.UserAddressDTO;

import org.example.service.UserAddressService;
import org.example.vo.UserAddressVO;

import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name="用户端地址管理")
@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserAddressController {

    @Resource
    private UserAddressService userAddressService;

    /**
     * 查看当前用户地址
     * @return
     */
    @Operation(summary = "查看用户地址")
    @GetMapping("/addresses")
    public Result<List<UserAddressVO>> getAddress(){
        //获取当前登陆用户id
        Long userId = BaseContext.getCurrentId();
        List<UserAddressVO> userAddressVO=userAddressService.getAddress(userId);
        return Result.success(userAddressVO);
    }

    /**
     * 新增地址
     * @param userAddressDTO
     * @return
     */
    @Operation(summary = "新增地址")
    @PostMapping("/addresses")
    public Result insertAddress(@RequestBody UserAddressDTO userAddressDTO){
        userAddressService.insert(userAddressDTO);
        return Result.success();
    }



    /**
     * 更新数据
     * @param id
     * @return
     */
    @Operation(summary = "更新地址信息")
    @PutMapping("/addresses/{id}")
    public Result updateAddress(@PathVariable Long id,
                                @RequestBody UserAddressDTO userAddressDTO){
        userAddressService.updateAddress(id,userAddressDTO);
         return Result.success();
    }

    /**
     * 删除地址信息
     * @param id
     * @return
     */
    @Operation(summary = "删除地址信息")
    @DeleteMapping("/addresses/{id}")
    public Result deleteAddress(@PathVariable Long id){
        userAddressService.deleteAddress(id);
        return Result.success();
    }






}
