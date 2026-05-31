package org.example.mapper;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.example.entity.ServiceOrderComment;
import org.springframework.web.bind.annotation.DeleteMapping;

@Mapper
public interface ServiceCommentMapper {
    /**
     * 评价管理分页查询
     * @param orderComment
     * @return
     */
    Page<ServiceOrderComment> getServiceComment(ServiceOrderComment orderComment);

    /**
     * 删除数据
     * @param id
     */
    @Delete("delete from service_order_comment where id=#{id} ")
    void deleteById(Long id);
}
