package org.example.mapper;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.dto.WorkerAdminPageQueryDTO;
import org.example.entity.Worker;

import java.util.List;

@Mapper
public interface WorkerAdminMapper {

    /**
     * 模糊查询工人的信息
     * @param worker
     * @return
     */
    Page<Worker> getWorker(Worker worker);

    /**
     * 模糊查询
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

    /**
     * 筛选合适的师傅
     * @return
     */
    @Select("select * from worker where status=1 and service_status=1")
    List<Worker> selectWorker();




}
