package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Worker {
//服务人员id
    private Long id;
    //姓名
    private String name;
    //头像
    private String avatar;
    //手机号
    private String phone;
    //密码
    private String password;
    //性别：1男 2女
    private String gender;
    //技能描述
    private String skillDesc;
    //接单状态：0休息中 1可接单 2服务中
    private Integer serviceStatus;
    //当前纬度
    private BigDecimal latitude;
    //当前经度
    private BigDecimal longitude;
    //评分
    private BigDecimal rating;
    //账号状态：1正常 0禁用
    private Integer status;
    //创建时间
    private LocalDateTime createTime;
    //更新时间
    private LocalDateTime updateTime;

    //非数据库字段,服务状态
    private String serviceStatusName;
    //接单数
    private Integer orderCount;


}
