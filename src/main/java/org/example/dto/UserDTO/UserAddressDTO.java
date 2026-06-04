package org.example.dto.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAddressDTO {

    private String contactName;

    private String contactPhone;

    private String province;

    private String city;

    private String district;

    private String detail;

    private Integer isDefault;

}
