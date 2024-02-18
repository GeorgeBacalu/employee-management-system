package com.project.ems.employee;

import com.project.ems.experience.Experience;
import com.project.ems.study.Study;
import com.project.ems.trainer.Trainer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import static com.project.ems.constants.Constants.EMPLOYEE_ACTIVE_FILTER_QUERY;
import static com.project.ems.constants.Constants.EMPLOYEE_FILTER_QUERY;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findAllByIsActiveTrue();

    Page<Employee> findAllByIsActiveTrue(Pageable pageable);

    List<Employee> findAllByExperiencesContains(Experience experience);

    List<Employee> findAllByStudiesContains(Study study);

    List<Employee> findAllByTrainer(Trainer trainer);

    @Query(EMPLOYEE_FILTER_QUERY) Page<Employee> findAllByKey(Pageable pageable, @Param("key") String key);

    @Query(EMPLOYEE_ACTIVE_FILTER_QUERY) Page<Employee> findAllActiveByKey(Pageable pageable, @Param("key") String key);
}
