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

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findAllByIsActiveTrue();

    Page<Employee> findAllByIsActiveTrue(Pageable pageable);

    List<Employee> findAllByExperiencesContains(Experience experience);

    List<Employee> findAllByStudiesContains(Study study);

    List<Employee> findAllByTrainer(Trainer trainer);

    @Query("SELECT e FROM Employee e JOIN e.authorities a JOIN e.experiences ex JOIN e.studies s WHERE LOWER(CONCAT(e.name, ' ', e.email, ' ', e.mobile, ' ', e.address, ' ', e.birthday, ' ', e.role.type, ' ', a.type, ' ', e.employmentType, ' ', e.position, ' ', e.grade, ' ', e.salary, ' ', e.hiredAt, ' ', ex.title, ' ', s.title, ' ', e.trainer.name)) LIKE %:key%")
    Page<Employee> findAllByKey(Pageable pageable, @Param("key") String key);

    @Query("SELECT e FROM Employee e JOIN e.authorities a JOIN e.experiences ex JOIN e.studies s WHERE LOWER(CONCAT(e.name, ' ', e.email, ' ', e.mobile, ' ', e.address, ' ', e.birthday, ' ', e.role.type, ' ', a.type, ' ', e.employmentType, ' ', e.position, ' ', e.grade, ' ', e.salary, ' ', e.hiredAt, ' ', ex.title, ' ', s.title, ' ', e.trainer.name)) LIKE %:key% AND e.isActive = true")
    Page<Employee> findAllActiveByKey(Pageable pageable, @Param("key") String key);
}
