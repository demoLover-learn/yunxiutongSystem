package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.entity.GrabOrderRecord;

/**
 * 抢单记录表 grab_order_record
 *
 * 用途：工单被取消后 worker_id 会被清空（释放工人），
 * 工人端"已取消"历史改为从这张表回溯"这单当初是我接的"。
 */
@Mapper
public interface GrabOrderRecordMapper {

    /**
     * 记录一次接单成功（工人抢单 / 管理员派单）
     *
     * @param record orderId、workerId、grabResult=1、createTime
     */
    void insert(GrabOrderRecord record);
}
