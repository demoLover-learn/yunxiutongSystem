package org.example.dto.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserServicePageQueryDTO {

    private Integer page;

    private Integer pageSize;

    private Long categoryId;
}
