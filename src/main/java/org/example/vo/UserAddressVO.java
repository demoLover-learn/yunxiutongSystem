package org.example.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAddressVO {

    private Long id;

    private String contactName;

    private String contactPhone;
    //省加市加区
    private String province;

    private String city;

    private String district;

    private String detail;

    private Integer isDefault;


}
