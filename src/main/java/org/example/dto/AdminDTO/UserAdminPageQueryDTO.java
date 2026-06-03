package org.example.dto.AdminDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAdminPageQueryDTO {
    //页码
    private Integer page;
    //页码大小
    private Integer pageSize;
    //昵称
    private String nickname;
    //手机号
    private String phone;
    //账号状态
    private Integer status;


}
