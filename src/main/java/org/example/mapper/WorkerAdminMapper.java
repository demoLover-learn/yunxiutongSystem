package org.example.mapper;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.dto.WorkerAdminPageQueryDTO;
import org.example.entity.Worker;

@Mapper
public interface WorkerAdminMapper {

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
