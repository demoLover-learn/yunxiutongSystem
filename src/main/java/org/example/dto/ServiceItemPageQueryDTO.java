package org.example.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceItemPageQueryDTO {

    //页码
    private Integer page;
    //页码大小
    private Integer pageSize;
    //分类id
    private Long categoryId;

}
