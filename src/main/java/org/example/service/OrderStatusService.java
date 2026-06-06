package org.example.service;

import org.example.entity.ServiceOrder;

public interface OrderStatusService {

    /**
     * 状态机设置
     * @param order
     * @param targetStatus
     * @param operatorType
     * @param operatorId
     * @param remark
     */
    void transition(ServiceOrder order, Integer targetStatus,
                    String operatorType, Long operatorId, String remark);

}
