package org.example.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import org.example.Result.PageResult;
import org.example.dto.UserAdminPageQueryDTO;
import org.example.entity.User;
import org.example.mapper.UserAdminMapper;
import org.example.service.UserAdminService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAdminServiceImpl implements UserAdminService {

    @Resource
    private UserAdminMapper userAdminMapper;

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
        Page<User> page= userAdminMapper.getUser(pageQueryDTO);
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
    public void stopOrStart(Long id,UserAdminPageQueryDTO pageQueryDTO) {
        //1.根据用户id查询出对应的数据
        User user=userAdminMapper.getById(id);
        //2.判断用户的状态，如果为0，则变成1，
       user.setStatus(pageQueryDTO.getStatus());
        //更新数据库
        userAdminMapper.update(user);
    }

}
