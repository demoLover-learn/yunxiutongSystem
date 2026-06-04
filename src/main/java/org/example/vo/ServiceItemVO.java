package org.example.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceItemVO {
    //服务项id
    private Long id;
   //分类id
    private Long categoryId;
    //项目名称
    private String name;
    //价格
    private BigDecimal price;
    //预计服务时常
    private Integer durationMinutes;
    //服务说明
    private String description;
    //status
    private Integer status;


}
