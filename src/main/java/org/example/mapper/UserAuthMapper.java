package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.entity.User;

@Mapper
public interface UserAuthMapper {


    /**
     * 根据电话号码查询对象
     * @return
     */
    @Select("select * from user where phone=#{phone}")
    User getByPhone(String phone);

    /**
     * 把数据插入到数据库
     * @param user
     */
    void save(User user);

    /**
     * 更新数据库
     * @param user
     */
    void update(User user);
}
