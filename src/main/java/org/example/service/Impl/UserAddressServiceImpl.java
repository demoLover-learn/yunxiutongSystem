package org.example.service.Impl;

import jakarta.annotation.Resource;
import org.example.context.BaseContext;
import org.example.dto.UserDTO.UserAddressDTO;
import org.example.entity.UserAddress;
import org.example.mapper.UserAddressMapper;
import org.example.service.UserAddressService;
import org.example.vo.UserAddressVO;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserAddressServiceImpl implements UserAddressService {

    @Resource
    private UserAddressMapper userAddressMapper;
    /**
     * 查询当前用户地址
     * @param userId
     * @return
     */
    @Override
    public List<UserAddressVO> getAddress(Long userId) {
        //根据用户id查询对应的信息
        List<UserAddress> userAddress=userAddressMapper.getAddressByUserId(userId);
        //如果没有的话返回null
        if (userAddress == null || userAddress.isEmpty()) {
            return new ArrayList<>();
        }
        List<UserAddressVO> userAddressVO=new ArrayList<>(userAddress.size());
        userAddress.forEach(address->{
            UserAddressVO addressVO = UserAddressVO.builder()
                    .id(address.getId())
                    .isDefault(address.getIsDefault())
                    .province(address.getProvince())
                    .city(address.getCity())
                    .district(address.getDistrict())
                    .detail(address.getDetailAddress())
                    .contactPhone(address.getContactPhone())
                    .contactName(address.getContactName())
                    .build();
            userAddressVO.add(addressVO);
        });
        //返回封装VO
        return userAddressVO;

    }

    /**
     * 更新用户信息
     * @param id
     * @param userAddressDTO
     */
    @Override
    public void updateAddress(Long id, UserAddressDTO userAddressDTO) {
        //把对应的属性信息拷贝到userAddress中
        UserAddress userAddress=new UserAddress();
        userAddress.setId(id);
        userAddress.setUserId(BaseContext.getCurrentId());
        BeanUtils.copyProperties(userAddressDTO,userAddress);
        userAddress.setUpdateTime(LocalDateTime.now());
        userAddress.setDetailAddress(userAddressDTO.getDetail());

        //更新数据库
        userAddressMapper.update(userAddress);
    }

    /**
     * 新增地址
     * @param userAddressDTO
     */
    @Override
    public void insert(UserAddressDTO userAddressDTO) {
        //获取当前用户id
        Long userId = BaseContext.getCurrentId();
        //新建实体类
        UserAddress userAddress = new UserAddress();
        //拷贝属性
        BeanUtils.copyProperties(userAddressDTO,userAddress);
        //设置创建时间和用户
        userAddress.setUserId(userId);
        userAddress.setDetailAddress(userAddressDTO.getDetail());
        userAddress.setCreateTime(LocalDateTime.now());
        //插入数据库
        userAddressMapper.insert(userAddress);

    }

    /**
     * 删除地址信息
     * @param id
     */
    @Override
    public void deleteAddress(Long id) {
        //删除只能删除自己的地址
        Long userId = BaseContext.getCurrentId();
        userAddressMapper.deleteAddress(id,userId);

    }
}
