package org.example.mapper;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.dto.UserAdminPageQueryDTO;
import org.example.entity.Employee;
import org.example.entity.User;

@Mapper
public interface EmployeeMapper {
    @Select("select * from employee where username=#{username}")
    Employee getByName(String username);

    /**
     * 用户管理分页查询
     * @param pageQueryDTO
     * @return
     */
    Page<User> getUser(UserAdminPageQueryDTO pageQueryDTO);

    /**
     * 根据用户查询id
     * @param id
     * @return
     */
    @Select("select * from user where id=#{id}")
    User getById(Long id);

    /**
     * 动态更新
     * @param user
     */
    void update(User user);
}
