package com.microservice.order_service2.service;

import com.microservice.order_service2.model.Order;
import com.microservice.order_service2.repo.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;


    public ResponseEntity<?> addOrder(Order order) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        System.out.println(order.getUserId());
        String url = "http://localhost:8091/user/fetch/"+order.getUserId();
        Object data = null;
        try
        {
            ResponseEntity<Object> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    Object.class
            );
            if (response.getStatusCode().is2xxSuccessful())
            {
                data = response.getBody();

                System.out.println("Fetched user: " + data);
            }
            return ResponseEntity.ok(data);

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
        catch (Exception e) {
            // For other errors (e.g., connection failure)
            System.out.println("General error: " + e.getMessage());
            return ResponseEntity
                    .status(500)
                    .body("Failed to communicate with User Service: " + e.getMessage());
        }



    }


}
