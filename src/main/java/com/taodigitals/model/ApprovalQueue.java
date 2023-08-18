package com.taodigitals.model;

import com.taodigitals.ApprovalStatus;

import javax.persistence.*;


import java.time.LocalDateTime;
@Entity
public class ApprovalQueue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Product product;
    private LocalDateTime requestDate;
    private ApprovalStatus approvalStatus;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public LocalDateTime getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(LocalDateTime requestDate) {
        this.requestDate = requestDate;
    }

    public ApprovalStatus getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(ApprovalStatus approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public ApprovalQueue( Product product, LocalDateTime requestDate, ApprovalStatus approvalStatus) {
        this.product = product;
        this.requestDate = requestDate;
        this.approvalStatus = approvalStatus;
    }
}
