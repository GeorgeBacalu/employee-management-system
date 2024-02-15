package com.project.ems.trainer;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainerRepository extends JpaRepository<Trainer, Integer> {

    List<Trainer> findAllByIsActiveTrue();
}
