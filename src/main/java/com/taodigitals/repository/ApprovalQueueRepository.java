package com.taodigitals.repository;

import com.taodigitals.model.ApprovalQueue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApprovalQueueRepository extends JpaRepository<ApprovalQueue, Long> {
    List<ApprovalQueue> findAllByOrderByRequestDateAsc();
}