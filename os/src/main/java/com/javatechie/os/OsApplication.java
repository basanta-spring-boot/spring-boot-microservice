package com.javatechie.os;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
@EnableEurekaClient
@RestController
@RequestMapping("/ms1")
@RefreshScope
public class OsApplication {

    @Autowired
    @Lazy
    private RestTemplate template;

    @Autowired
    private OrderRepository repository;

    @Value("${microservice.paymentservice.endpoint.uri}")
    private String PAYMENT_ENDPOINT_URL;

    @PostMapping("/orders")
    public TransactionResponse bookOrders(@RequestBody TransactionRequest request) {
        //do payment , if payment succeed then save orders to DB
        //then invoke courier service
        Order order = request.getOrder();
        Payment payment = request.getPayment();
        payment.setOrderId(order.getId());
        Payment paymentResponse = template.
                postForObject(PAYMENT_ENDPOINT_URL, payment, Payment.class);
        if (paymentResponse.getPaymentStatus().equalsIgnoreCase("SUCCESS")) {
            repository.save(order);
        }
        return new TransactionResponse(order, paymentResponse.getTransactionId(), request.getUserName());
    }

    @GetMapping("/orders")
    public List<Order> viewOrders() {
        return repository.findAll();
    }

    public static void main(String[] args) {
        SpringApplication.run(OsApplication.class, args);
    }

    @Bean
    @LoadBalanced
    public RestTemplate template() {
        return new RestTemplate();
    }
}
