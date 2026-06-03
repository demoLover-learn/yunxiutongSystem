package org.example.dto.AdminDTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {


    //登陆账号
    private String username;
    //登陆密码
    private String password;

}
