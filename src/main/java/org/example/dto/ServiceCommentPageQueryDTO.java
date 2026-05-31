package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceCommentPageQueryDTO {
    //页码开始位置
    private Integer page;
    //页码所展示的内容容量
    private Integer pageSize;
//orderNo=&userName=&workerName=
    //工单编号
    private String orderNo;
    //用户名
    private String userName;
    //工人名称
    private String workerName;

}
