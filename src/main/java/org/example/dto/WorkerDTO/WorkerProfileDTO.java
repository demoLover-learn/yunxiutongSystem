package org.example.dto.WorkerDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkerProfileDTO {
    private String avatar;

    private String gender;

    private String skillDesc;

    private String workerName;
}
