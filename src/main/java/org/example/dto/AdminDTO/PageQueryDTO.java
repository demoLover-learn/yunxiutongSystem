package org.example.dto.AdminDTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageQueryDTO {
    //页码
    private Integer page=1;
    //页码大小
    private Integer pageSize=10;

    private Long id;

    private String name;
}
