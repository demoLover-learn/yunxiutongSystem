package org.example.mapper;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
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

    /**
     * 根据工人id查询工人是否存在
     * @param username
     * @return
     */
    @Select("select * from worker where phone=#{username}")
    Worker getByWorkerPhone(String username);

    /**
     * 工人插入
     * @param worker1
     */
    void insert(Worker worker1);

    /**
     * 统计查询当日订单和总订单数
     * @param workerId
     * @return
     */
    Worker getMoreByWorkerId(Long workerId);
}
