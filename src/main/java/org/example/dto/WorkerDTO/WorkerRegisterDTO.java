package org.example.dto.WorkerDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkerRegisterDTO {
    private String name;

    private String password;

    private String phone;
}
