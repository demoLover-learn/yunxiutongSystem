package org.example.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import org.example.Result.PageResult;
import org.example.Result.Result;
import org.example.dto.LoginDTO;
import org.example.dto.UserAdminPageQueryDTO;
import org.example.dto.WorkerAdminPageQueryDTO;
import org.example.entity.Employee;
import org.example.entity.User;
import org.example.entity.Worker;
import org.example.mapper.EmployeeMapper;
import org.example.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.AccountException;
import java.util.List;

@Service
public class EmployeeServiceImpl  implements EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;



    /**
     * 管理员登陆
     * @param loginDTO
     */
    public Employee login(LoginDTO loginDTO) throws AccountException {
       //账号密码传过来了，先验证账号存在不
        Employee employee = employeeMapper.getByName(loginDTO.getUsername());
        if (employee == null) {
            throw new AccountException("账户不存在");
        }
        if(!employee.getPassword().equals(loginDTO.getPassword())) {
            throw new AccountException("账号或者密码错误");
        }
        if (employee.getStatus() == 0) {
            throw new AccountException("账号已经被锁定");
        }
        return employee;
    }

    /**
     * 用户管理分页查询
     * @param pageQueryDTO
     * @return
     */
    @Override
    public PageResult getUser(UserAdminPageQueryDTO pageQueryDTO) {
        PageHelper.startPage(pageQueryDTO.getPage(),pageQueryDTO.getPageSize());
        //根据pageQuery的内容查询对应的内容
        //select * from user where nickname=${nickname} and phone like concat${'%',${phone},'%'} and status=${status}
        Page<User> page= employeeMapper.getUser(pageQueryDTO);
        //pageresult的记录总数pigeSize,
        long total = page.getTotal();
        List<User> result = page.getResult();
        return new PageResult(total,result);

    }
    /**
     * 更改账号状态
     * @param id
     * @return
     */
    @Override
    public void stopOrStart(Long id) {
        //1.根据用户id查询出对应的数据
        User user=employeeMapper.getById(id);
        //2.判断用户的状态，如果为0，则变成1，
        user.setStatus(user.getStatus()==0?1:0);
        //更新数据库
        employeeMapper.update(user);
    }
    /**
     * 服务人员管理
     * @param queryDTO
     * @return
     */
    public PageResult getWorker(WorkerAdminPageQueryDTO queryDTO) {
        //设置开始页码，和一页的数量
        PageHelper.startPage(queryDTO.getPage(),queryDTO.getPageSize());

        //根据前端传来的信息进行模糊查询
        Page<Worker> result=employeeMapper.getWorker(queryDTO);
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
    public void workerStatus(Long id) {
        //1.根据工人id查询出对应的数据
        Worker worker=employeeMapper.getByWorkerId(id);
        //2.判断工人的状态，如果为0，则变成1，
        worker.setStatus(worker.getStatus()==0?1:0);
        //更新数据库
        employeeMapper.updateWorker(worker);
    }
}
