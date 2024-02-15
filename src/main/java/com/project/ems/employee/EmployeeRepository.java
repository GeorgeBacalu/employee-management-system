package com.project.ems.employee;

import com.project.ems.experience.Experience;
import com.project.ems.study.Study;
import com.project.ems.trainer.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findAllByIsActiveTrue();

    List<Employee> findAllByExperiencesContains(Experience experience);

    List<Employee> findAllByStudiesContains(Study study);

    List<Employee> findAllByTrainer(Trainer trainer);
}
