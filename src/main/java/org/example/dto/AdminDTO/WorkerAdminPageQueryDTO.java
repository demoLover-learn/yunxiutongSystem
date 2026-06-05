package org.example.dto.AdminDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkerAdminPageQueryDTO {
    //页码
    private Integer page;
    //页码大小
    private Integer pageSize;
    //姓名
    private String name;

    //账号状态：1正常 0禁用
    private Integer status;


}
