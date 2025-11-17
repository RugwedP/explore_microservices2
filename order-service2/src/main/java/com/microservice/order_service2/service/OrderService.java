package com.microservice.order_service2.service;

import com.microservice.order_service2.model.Order;
import com.microservice.order_service2.repo.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;


    public ResponseEntity<?> addOrder(Order order) {
        Map<String, List<Object>> response= new HashMap<>();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        System.out.println(order.getUserId());
        String url = "http://localhost:8091/user/fetch/"+order.getUserId();

        try
        {
            ResponseEntity<Object> user_response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    Object.class
            );
            System.out.println(user_response.getBody());
            List<Object> list = new ArrayList<>();
            list.add(user_response.getBody());
            response.put("user",list);


//            if (response.getStatusCode().is2xxSuccessful())
//            {
//                data = response.getBody();
//
//                System.out.println("Fetched user: " + data);
//            }
//            return ResponseEntity.ok(data);

        }

        catch (HttpClientErrorException | HttpServerErrorException ex)
        {

            int statusCode = ex.getRawStatusCode();
            String responseBody = ex.getResponseBodyAsString();
            System.out.println("Error from user-service: " + responseBody);

            return ResponseEntity
                    .status(statusCode)
                    .body( responseBody);
        }

        try
        {
            List<Order.OrderItem> orderItems = order.getItems();
            List<Object> productList = new ArrayList<>();
            response.put("product",productList);
            orderItems.forEach(ele->{
                String urlProduct = "http://localhost:8090/product/findById/"+ele.getProductId();
                ResponseEntity<Object> productResponse = restTemplate.exchange(
                        urlProduct,
                        HttpMethod.GET,
                        entity,
                        Object.class
                );
                System.out.println(productResponse.getBody());

                productList.add(productResponse.getBody());
            });

        }
        catch (HttpClientErrorException | HttpServerErrorException ex)
        {
            int statusCode = ex.getRawStatusCode();
            String productResponse = ex.getResponseBodyAsString();
            System.out.println("Error from user-service: " + productResponse);

            return ResponseEntity
                    .status(statusCode)
                    .body( productResponse);
        }
        catch (Exception e) {
            // For other errors (e.g., connection failure)
            System.out.println("General error: " + e.getMessage());
            return ResponseEntity
                    .status(500)
                    .body("Failed to communicate with User Service: " + e.getMessage());
        }

        Order savedOrder = orderRepo.save(order);

        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);




    }


}
