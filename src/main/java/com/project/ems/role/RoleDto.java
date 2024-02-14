package com.project.ems.role;

import com.project.ems.role.enums.RoleType;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {

    private Integer id;

    private RoleType type;
}
