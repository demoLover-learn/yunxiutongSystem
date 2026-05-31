package org.example.service;

import org.example.Result.PageResult;
import org.example.dto.ServiceCommentPageQueryDTO;

public interface ServiceCommentService {
    /**
     * 评价管理分页查询
     * @param serviceCommentPageQueryDTO
     * @return
     */
    PageResult getComment(ServiceCommentPageQueryDTO serviceCommentPageQueryDTO);

    /**
     * 删除数据
     * @param id
     */
    void deleteComment(Long id);
}
