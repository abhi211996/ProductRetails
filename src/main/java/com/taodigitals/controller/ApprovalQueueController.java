package com.taodigitals.controller;

import com.taodigitals.model.ApprovalQueue;
import com.taodigitals.service.ApprovalQueueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products/approval-queue")
public class ApprovalQueueController {
    private final ApprovalQueueService approvalQueueService;

    public ApprovalQueueController(ApprovalQueueService approvalQueueService) {
        this.approvalQueueService = approvalQueueService;
    }

    @GetMapping
    public List<ApprovalQueue> getProductsInApprovalQueue() {
        return approvalQueueService.getProductsInApprovalQueue();
    }

    @PutMapping("/{approvalId}/approve")
    public ResponseEntity<String> approveProduct(@PathVariable Long approvalId) {
        approvalQueueService.approveProduct(approvalId);
        return ResponseEntity.ok("Product approved successfully.");
    }

    @PutMapping("/{approvalId}/reject")
    public ResponseEntity<String> rejectProduct(@PathVariable Long approvalId) {
        approvalQueueService.rejectProduct(approvalId);
        return ResponseEntity.ok("Product rejected successfully.");
    }
}

