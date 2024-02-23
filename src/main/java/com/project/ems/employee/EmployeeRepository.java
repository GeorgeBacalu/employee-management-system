package com.project.ems.employee;

import com.project.ems.base.BaseUserRepository;
import com.project.ems.trainer.Trainer;

import java.util.List;

public interface EmployeeRepository extends BaseUserRepository<Employee> {

    List<Employee> findAllByTrainer(Trainer trainer);
}
