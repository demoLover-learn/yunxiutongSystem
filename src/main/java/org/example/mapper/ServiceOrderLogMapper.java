package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.entity.ServiceOrderLog;

@Mapper
public interface ServiceOrderLogMapper {

    /**
     * 工单日志的处插入
     * @param serviceOrderLog
     */
    void insert(ServiceOrderLog serviceOrderLog);
}
