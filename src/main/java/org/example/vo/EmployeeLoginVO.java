package org.example.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeLoginVO {

    private Long id;

    private String username;

    private String name;
    //jwt令牌
    private String token;


}
