package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderManageDTO {
    //页码
    private Integer page;
    //页码容量
    private Integer pageSize;
    //订单编号
    private String orderNo;
    //用户名称
    private String userName;
    //订单状态
    private Integer orderStatus;
}
