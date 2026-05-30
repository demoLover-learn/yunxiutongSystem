package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceCategoryDTO {

    //页码
    private Integer page;
    //页码内容大小
    private Integer pageSize;

    private Long categoryId;



}
