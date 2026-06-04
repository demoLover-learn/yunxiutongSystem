package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
//项目服务表
public class ServiceItem {
    //服务项id
    private Long id;
    //分类id
    private Long categoryId;
    //分类名称（非数据库字段，仅用于列表显示）
    private String categoryName;
    //服务名称
    private String name;
    //基础价格
    private BigDecimal price;
    //服务说明
    private String description;
    //预计服务时常
    private Integer durationMinutes;
    //状态：1上架 0下架
    private Integer status;
    //创建时间
    private LocalDateTime createTime;
    //更新时间
    private LocalDateTime updateTime;




}
