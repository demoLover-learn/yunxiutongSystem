package org.example.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    //用户id
    private Long id;
    //手机号
    private String phone;
    //密码
    private String password;
    //昵称
    private String nickname;
    //头像
    private String avatar;
    //状态：1正常，0禁用
    private Integer status;
    //创建时间
    private LocalDateTime createTime;
    //更新时间
    private LocalDateTime updateTime;
}
