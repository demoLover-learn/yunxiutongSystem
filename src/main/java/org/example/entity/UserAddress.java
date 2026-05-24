package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAddress {
    //地址id
    private Long id;
    //用户id
    private Long userId;
    //联系人
    private String contactName;
    //联系电话
    private String contactPhone;
    //省
    private String province;
    //市
    private String city;
    //区
    private String district;
    //详细地址
    private String detailAddress;
    //当前纬度
    private BigDecimal latitude;
    //当前经度
    private BigDecimal longitude;
    //是否默认地址：1是 0否
    private Integer isDefault;
    //创建时间
    private LocalDateTime createTime;
    //更新时间
    private LocalDateTime updateTime;

}
