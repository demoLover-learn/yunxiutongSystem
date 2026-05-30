package org.example.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import org.example.Result.PageResult;
import org.example.dto.ServiceItemPageQueryDTO;
import org.example.entity.ServiceCategory;
import org.example.entity.ServiceItem;
import org.example.mapper.ServiceCategoryMapper;
import org.example.mapper.ServiceItemMapper;
import org.example.service.ServiceItemService;

import org.example.vo.ServiceCategoryVO;
import org.example.vo.ServiceItemVO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ServiceItemServiceImpl implements ServiceItemService {
    @Resource
    private ServiceItemMapper serviceItemMapper;



    /**
     * 分页查询
     * @param serviceItemPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(ServiceItemPageQueryDTO serviceItemPageQueryDTO) {
        //把起始页码和页码记录的内容总条数传进去
        PageHelper.startPage(serviceItemPageQueryDTO.getPage(),serviceItemPageQueryDTO.getPageSize());

        //查询数据库对应数据
        Page<ServiceItem> page=serviceItemMapper.selectAll(serviceItemPageQueryDTO);

        long total = page.getTotal();
        List<ServiceItem> list=page.getResult();
        return new PageResult(total,list);
    }
    /**
     * 新增项目
     * @param serviceItemVO
     * @return
     */
    @Override
    public Long insert(ServiceItemVO serviceItemVO) {
        //创建serviceCategory实体类对象
        ServiceItem serviceItem = new ServiceItem();
        //把Vo拷贝到实体类中
        BeanUtils.copyProperties(serviceItemVO,serviceItem);
        //根据分类名称进行查询对应的项目id
        serviceItem.setStatus(1);
        serviceItem.setCreateTime(LocalDateTime.now());
        //插入数据，返回数值
        Long l= serviceItemMapper.insert(serviceItem);
        return l;
    }

    /**
     * 更新项目
     * @param serviceItemVO
     */
    @Override
    public void update(Long id,ServiceItemVO serviceItemVO) {
        ServiceItem serviceItem=serviceItemMapper.getById(id);
        //更改属性
        serviceItem.setName(serviceItemVO.getName());
        serviceItem.setDescription(serviceItemVO.getDescription());
        serviceItem.setPrice(serviceItemVO.getPrice());
        serviceItem.setDurationMinutes(serviceItemVO.getDurationMinutes());
        serviceItem.setCategoryId(serviceItemVO.getCategoryId());
        serviceItem.setUpdateTime(LocalDateTime.now());
        //更新数据库
        serviceItemMapper.update(serviceItem);
    }
    /**
     * 更改服务项目状态
     * @param id
     * @param serviceItemVO
     * @return
     */
    @Override
    public void stopOrStart(Long id, ServiceItemVO serviceItemVO) {
        //根据id查询对应的订单
        ServiceItem serviceItem = serviceItemMapper.getById(id);
        //更新status值
        serviceItem.setStatus(serviceItemVO.getStatus());
        serviceItemMapper.update(serviceItem);
    }

    /**
     * 删除项目订单
     * @param id
     */
    @Override
    public void delete(Long id) {
        serviceItemMapper.deleteById(id);
    }
}
