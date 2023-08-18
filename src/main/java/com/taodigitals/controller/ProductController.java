package com.taodigitals.controller;

import com.taodigitals.model.Product;
import com.taodigitals.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/activeProducts")
    public List<Product> listActiveProducts() {
        return productService.listActiveProducts();
    }

    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam(required = false) String productName,
                                        @RequestParam(required = false) BigDecimal minPrice,
                                        @RequestParam(required = false) BigDecimal maxPrice,
                                        @RequestParam(required = false) LocalDateTime minPostedDate,
                                        @RequestParam(required = false) LocalDateTime maxPostedDate) {
        return (List<Product>) productService.searchProducts( productName,  minPrice,
                 maxPrice,  minPostedDate,
                 maxPostedDate);
    }

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody Product product) {
        productService.createProduct(product);
        return ResponseEntity.ok("Product created successfully.");
    }

    @PutMapping("/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable Long productId, @RequestBody Product product) {
        productService.updateProduct(productId, product);
        return ResponseEntity.ok("Product updated successfully.");
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok("Product deleted successfully.");
    }
}
