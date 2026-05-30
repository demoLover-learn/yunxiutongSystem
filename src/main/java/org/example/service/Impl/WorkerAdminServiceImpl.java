package org.example.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import org.example.Result.PageResult;
import org.example.dto.WorkerAdminPageQueryDTO;
import org.example.entity.Worker;
import org.example.mapper.WorkerAdminMapper;
import org.example.service.WorkerAdminService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WorkerAdminServiceImpl implements WorkerAdminService {
    @Resource
    private WorkerAdminMapper workerAdminMapper;


    /**
     * 服务人员管理
     * @param queryDTO
     * @return
     */
    public PageResult getWorker(WorkerAdminPageQueryDTO queryDTO) {
        //设置开始页码，和一页的数量
        PageHelper.startPage(queryDTO.getPage(),queryDTO.getPageSize());

        //根据前端传来的信息进行模糊查询
        Page<Worker> result=workerAdminMapper.getWorker(queryDTO);
        //取出对应的数据
        long total = result.getTotal();
        List records = result.getResult();
        return  new PageResult(total,records);
    }
    /**
     * 工人账号状态的设置
     * @param id
     * @return
     */
    @Transactional
    @Override
    public void workerStatus(Long id,WorkerAdminPageQueryDTO queryDTO) {
        //1.根据工人id查询出对应的数据
        Worker worker=workerAdminMapper.getByWorkerId(id);
        //2.判断工人的状态，如果为0，则变成1，
         worker.setStatus(queryDTO.getStatus());
        //更新数据库
        workerAdminMapper.updateWorker(worker);
    }
}
