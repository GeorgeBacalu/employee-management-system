package com.project.ems.employee;

import com.project.ems.trainer.Trainer;
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
@DiscriminatorValue("EMPLOYEE")
public class Employee extends User {

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;
}
