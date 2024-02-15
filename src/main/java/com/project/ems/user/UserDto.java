package com.project.ems.user;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    @Positive(message = "User ID must be positive")
    private Integer id;

    @NotBlank(message = "User name must not be blank")
    private String name;

    @NotBlank(message = "User email must not be blank")
    @Email(message = "User email must be valid")
    private String email;

    @NotBlank(message = "User password must not be blank")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+-=()])(?=\\S+$).{8,30}$", message = "User password must be valid")
    private String password;

    @NotBlank(message = "User mobile number must not be blank")
    @Pattern(regexp = "^(00|\\+?40|0)(7\\d{2}|\\d{2}[13]|[2-37]\\d|8[02-9]|9[0-2])\\s?\\d{3}\\s?\\d{3}$", message = "User mobile number must be valid")
    private String mobile;

    @NotBlank(message = "User address must not be blank")
    private String address;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @NotNull(message = "Role ID must not be null")
    @Positive(message = "Role ID must be positive")
    private Integer roleId;

    private List<@NotNull(message = "Authority ID must not be null") @Positive(message = "Authority ID must be positive") Integer> authoritiesIds;

    private Boolean isActive;
}
