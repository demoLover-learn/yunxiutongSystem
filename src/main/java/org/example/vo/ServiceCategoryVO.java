package org.example.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceCategoryVO {
    private Long id;

    private String name;

    private Integer sort;

    private Integer status;
}
