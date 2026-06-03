package org.example.service.Impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import jakarta.annotation.Resource;
import org.example.Result.PageResult;
import org.example.dto.AdminDTO.ServiceCommentPageQueryDTO;
import org.example.entity.ServiceOrderComment;
import org.example.mapper.ServiceCommentMapper;
import org.example.service.ServiceCommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceCommentServiceImpl implements ServiceCommentService {
    @Resource
    private ServiceCommentMapper serviceCommentMapper;
    /**
     * 评价管理分页查询
     * @param serviceCommentPageQueryDTO
     * @return
     */
    @Override
    public PageResult getComment(ServiceCommentPageQueryDTO serviceCommentPageQueryDTO) {
        //设置开始页和内容大小
        PageHelper.startPage(serviceCommentPageQueryDTO.getPage(), serviceCommentPageQueryDTO.getPageSize());
        //新建实体类接收参数
        ServiceOrderComment orderComment = new ServiceOrderComment();
        BeanUtils.copyProperties(serviceCommentPageQueryDTO, orderComment);
        //查询数据
        Page<ServiceOrderComment> result= serviceCommentMapper.getServiceComment(orderComment);

        long total = result.getTotal();
        List<ServiceOrderComment> results = result.getResult();
        return new PageResult(total, results);
    }

    /**
     * 删除数据
     * @param id
     */
    @Override
    public void deleteComment(Long id) {
        serviceCommentMapper.deleteById(id);

    }
}
