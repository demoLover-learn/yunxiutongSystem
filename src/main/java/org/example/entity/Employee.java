package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.quartz.LocalDataSourceJobStore;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    //管理人员id
    private Long id;
    //账号
    private String username;
    //密码
    private String password;
    //姓名
    private String name;
    //角色
    private String role;
    //状态：1正常 2禁用
    private Integer status;
    //创建时间
    private LocalDateTime createTime;
    //更新时间
    private LocalDateTime  updateTime;

}
