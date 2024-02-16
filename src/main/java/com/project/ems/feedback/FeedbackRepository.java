package com.project.ems.feedback;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {

    @Query("SELECT f FROM Feedback f WHERE LOWER(CONCAT(f.type, ' ', f.description, ' ', f.sentAt, ' ', f.user.name)) LIKE %:key%")
    Page<Feedback> findAllByKey(Pageable pageable, @Param("key") String key);
}
