package com.taodigitals.service;

import com.taodigitals.ApprovalStatus;
import com.taodigitals.model.ApprovalQueue;
import com.taodigitals.repository.ApprovalQueueRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApprovalQueueService {
    private final ApprovalQueueRepository approvalQueueRepository;

    public ApprovalQueueService(ApprovalQueueRepository approvalQueueRepository) {
        this.approvalQueueRepository = approvalQueueRepository;
    }

    public List<ApprovalQueue> getProductsInApprovalQueue() {
        return approvalQueueRepository.findAllByOrderByRequestDateAsc();
    }

    public void addToApprovalQueue(ApprovalQueue approvalQueue) {
        approvalQueueRepository.save(approvalQueue);
    }

    public void approveProduct(Long approvalId) {
        ApprovalQueue approvalQueue = approvalQueueRepository.findById(approvalId).get();

        approvalQueue.getProduct().setStatus(true);
        approvalQueue.setApprovalStatus(ApprovalStatus.APPROVED);

        approvalQueueRepository.delete(approvalQueue);
    }

    public void rejectProduct(Long approvalId) {
        ApprovalQueue approvalQueue = approvalQueueRepository.findById(approvalId).get();

        approvalQueue.setApprovalStatus(ApprovalStatus.REJECTED);

        approvalQueueRepository.delete(approvalQueue);
    }
}
