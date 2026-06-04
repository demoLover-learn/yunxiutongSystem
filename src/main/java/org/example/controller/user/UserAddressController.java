package org.example.controller.user;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.example.Result.Result;
import org.example.context.BaseContext;
import org.example.dto.UserDTO.UserAddressDTO;

import org.example.service.UserAddressService;
import org.example.vo.UserAddressVO;

import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @DeleteMapping("/addresses/{id}")
    public Result deleteAddress(@PathVariable Long id){
        userAddressService.deleteAddress(id);
        return Result.success();
    }






}
