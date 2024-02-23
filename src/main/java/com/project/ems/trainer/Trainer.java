package com.project.ems.trainer;

import com.project.ems.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@DiscriminatorValue("TRAINER")
public class Trainer extends User {

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer supervisingTrainer;

    private Integer nrTrainees;

    private Integer maxTrainees;
}
