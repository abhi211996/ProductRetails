package com.taodigitals.service;


import com.taodigitals.ApprovalStatus;
import com.taodigitals.model.ApprovalQueue;
import com.taodigitals.model.Product;
import com.taodigitals.repository.ProductRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ApprovalQueueService approvalQueueService;

    public ProductService(ProductRepository productRepository, ApprovalQueueService approvalQueueService) {
        this.productRepository = productRepository;
        this.approvalQueueService = approvalQueueService;
    }

    public List<Product> listActiveProducts() {
        return productRepository.findByStatusOrderByPostedDateDesc();
    }

    public static Specification<Product> searchProducts(String productName, BigDecimal minPrice,
                                                        BigDecimal maxPrice, LocalDateTime minPostedDate,
                                                        LocalDateTime maxPostedDate) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (productName != null && !productName.isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")),
                        "%" + productName.toLowerCase() + "%"));
            }

            if (minPrice != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
            }

            if (maxPrice != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
            }

            if (minPostedDate != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), minPostedDate));
            }

            if (maxPostedDate != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), maxPostedDate));
            }

            predicates.add(criteriaBuilder.isTrue(root.get("status")));

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
    public void createProduct(Product product) {
        if (product.getPrice().compareTo(BigDecimal.valueOf(10000)) <= 0) {
            productRepository.save(product);
        } else {
            product.setStatus(false);
            ApprovalQueue approvalQueue = new ApprovalQueue(product, LocalDateTime.now(), ApprovalStatus.PENDING);
            approvalQueueService.addToApprovalQueue(approvalQueue);
        }
    }

    public void updateProduct(Long productId, Product product) {
        Product existingProduct = productRepository.findById(productId).get();

        BigDecimal previousPrice = existingProduct.getPrice();
        BigDecimal newPrice = product.getPrice();

        if (newPrice.compareTo(previousPrice.multiply(BigDecimal.valueOf(1.5))) > 0) {
            ApprovalQueue approvalQueue = new ApprovalQueue(existingProduct, LocalDateTime.now(), ApprovalStatus.PENDING);
            approvalQueueService.addToApprovalQueue(approvalQueue);
        }

        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        productRepository.save(existingProduct);
    }

    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).get();
        ApprovalQueue approvalQueue = new ApprovalQueue(product, LocalDateTime.now(), ApprovalStatus.PENDING);
        approvalQueueService.addToApprovalQueue(approvalQueue);

        productRepository.delete(product);
    }
}
