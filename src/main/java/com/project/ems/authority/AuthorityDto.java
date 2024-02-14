package com.project.ems.authority;

import com.project.ems.authority.enums.AuthorityType;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorityDto {

    private Integer id;

    private AuthorityType type;
}
