package org.example.dto.AdminDTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageQueryDTO {
    //页码
    private int page;
    //页码大小
    private int pageSize;

    private Long id;

    private String name;
}
