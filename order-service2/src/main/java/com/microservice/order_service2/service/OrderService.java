package com.microservice.order_service2.service;

import com.microservice.order_service2.model.Order;
import com.microservice.order_service2.repo.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;


    public ResponseEntity<?> addOrder(Order order) {
        Order savedOrder = orderRepo.save(order);
        return ResponseEntity.ok(savedOrder);
    }


}
