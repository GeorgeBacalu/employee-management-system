package com.project.ems.employee;

import com.project.ems.user.UserDto;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

@Data
@ToString(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto extends UserDto {

    @Positive(message = "Trainer ID must be positive")
    private Integer trainerId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        EmployeeDto that = (EmployeeDto) o;
        return Objects.equals(trainerId, that.trainerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), trainerId);
    }
}
