package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
/**
 * 服务分类表
 */
public class ServiceCategory {
    //分类id
    private Long id;
    //分类名称
    private String name;
    //排序
    private Integer sort;
    // '状态:1启用 0禁用',
    private Integer status;
    //创建时间
    private LocalDateTime createTime;
    //更新时间
    private LocalDateTime updateTime;

}
