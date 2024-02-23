package com.project.ems.trainer;

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
public class TrainerDto extends UserDto {

    @Positive(message = "Supervising trainer ID must be positive")
    private Integer supervisingTrainerId;

    @Positive(message = "Number of trainees must be positive")
    private Integer nrTrainees;

    @Positive(message = "Max available trainees must be positive")
    private Integer maxTrainees;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TrainerDto that = (TrainerDto) o;
        return Objects.equals(supervisingTrainerId, that.supervisingTrainerId) && Objects.equals(nrTrainees, that.nrTrainees) && Objects.equals(maxTrainees, that.maxTrainees);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), supervisingTrainerId, nrTrainees, maxTrainees);
    }
}
