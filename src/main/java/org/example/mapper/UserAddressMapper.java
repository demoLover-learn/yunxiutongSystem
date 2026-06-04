package org.example.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.entity.UserAddress;

import java.util.List;

@Mapper
public interface UserAddressMapper {
    /**
     * 根据用户id查询对应的地址信息
     * @param userId
     * @return
     */
    @Select("select  * from user_address where user_id=#{userId}")
    List<UserAddress> getAddressByUserId(Long userId);

    /**
     * 更新用户的地址信息
     * @param userAddress
     */
    void update(UserAddress userAddress);

    /**
     * 新增用户地址
     * @param userAddress
     */
    void insert(UserAddress userAddress);

    /**
     * 删除地址信息
     * @param id
     */
    @Delete("delete from user_address where id=#{id} and user_id=#{userId}")
    void deleteAddress(Long id,Long userId);
}
