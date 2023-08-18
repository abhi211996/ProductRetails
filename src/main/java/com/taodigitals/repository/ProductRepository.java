package com.taodigitals.repository;

import com.taodigitals.ProductStatus;
import com.taodigitals.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByStatusOrderByPostedDateDesc();
}