package com.retentix.repository;

import com.retentix.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    // Find all feedback submitted by a particular customer
    List<Feedback> findByCustomerId(Long customerId);
}