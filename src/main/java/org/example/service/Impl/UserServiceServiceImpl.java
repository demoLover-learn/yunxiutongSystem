package org.example.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;

import org.example.dto.AdminDTO.ServiceItemPageQueryDTO;
import org.example.entity.ServiceItem;
import org.example.mapper.ServiceItemMapper;
import org.example.service.UserServiceService;
import org.example.vo.ServiceItemVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceServiceImpl implements UserServiceService {

    @Resource
    private ServiceItemMapper serviceItemMapper;



    /**
     * 分页查询
     * @param query
     * @return
     */
    @Override
    public List<ServiceItemVO> pageQuery(ServiceItemPageQueryDTO query) {
        //设置开始页码，和当前页码的容量
        PageHelper.startPage(query.getPage(),query.getPageSize());
        //分页查询
        Page<ServiceItem> page = serviceItemMapper.selectAllowed(query);
        //给VO赋值
        List<ServiceItemVO> serviceItemVOS = new ArrayList<>(page.size());
        for (ServiceItem serviceItem : page) {
            ServiceItemVO serviceItemVO = new ServiceItemVO();
            BeanUtils.copyProperties(serviceItem, serviceItemVO);
            serviceItemVOS.add(serviceItemVO);
        }
        return serviceItemVOS;
    }

    /**
     * 查看服务详情信息
     * @param id
     * @return
     */
    @Override
    public ServiceItemVO getDetail(Long id) {
        //根据id查询对应的服务项目
       ServiceItem serviceItem= serviceItemMapper.getByDetailId(id);
        //判断服务项目是否存在
        if (serviceItem== null ) {
            //不存在返回错误
            throw new RuntimeException("服务项目不存在");
        }
        ServiceItemVO vo = new ServiceItemVO();
        BeanUtils.copyProperties(serviceItem, vo);
        vo.setCategoryName(serviceItem.getCategoryName());
        return vo;
    }
}
