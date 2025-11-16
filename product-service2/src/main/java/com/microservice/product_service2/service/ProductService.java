package com.microservice.product_service2.service;

import com.microservice.product_service2.exception.ResourceNotFoundException;
import com.microservice.product_service2.model.Product;
import com.microservice.product_service2.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    public ResponseEntity<?> addProduct(Product product) {

        Product product1 = this.productRepo.save(product);
        return ResponseEntity.ok(product1);

    }

    public Product fetchProduct(long id) {
        return productRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("Product not found with id :"+id));
    }
}
