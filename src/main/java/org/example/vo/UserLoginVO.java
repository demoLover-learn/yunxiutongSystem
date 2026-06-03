package org.example.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class UserLoginVO {

    private Long id;

    private String phone;

    private String nickname;

    private String avatar;

    private String token;




}
