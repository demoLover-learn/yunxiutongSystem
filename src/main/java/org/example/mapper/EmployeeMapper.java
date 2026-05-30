package org.example.mapper;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.Result.PageResult;
import org.example.dto.UserAdminPageQueryDTO;
import org.example.dto.WorkerAdminPageQueryDTO;
import org.example.entity.Employee;
import org.example.entity.User;
import org.example.entity.Worker;

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

    /**
     * 模糊查询工人的信息
     * @param queryDTO
     * @return
     */
   Page<Worker> getWorker(WorkerAdminPageQueryDTO queryDTO);

    /**
     * 根据id查询工人信息
     * @param id
     * @return
     */
    @Select("select * from worker where id=#{id}")
    Worker getByWorkerId(Long id);

    /**
     * 更新工人数据库
     * @param worker
     */

    void updateWorker(Worker worker);
}
