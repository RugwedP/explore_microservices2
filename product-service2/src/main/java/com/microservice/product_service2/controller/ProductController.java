package com.microservice.product_service2.controller;

import com.microservice.product_service2.model.Product;
import com.microservice.product_service2.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@Valid @RequestBody Product product)
    {
        return this.productService.addProduct(product);
    }

    @GetMapping("/findById/{id}")
    public Product fetchProduct(@PathVariable("id") long id)
    {
        return this.productService.fetchProduct(id);
    }


}
