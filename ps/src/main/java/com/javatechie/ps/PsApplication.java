package com.javatechie.ps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@SpringBootApplication
@EnableEurekaClient
@RestController
@RequestMapping("/ms2")
public class PsApplication {
    @Autowired
    private PaymentRepository repository;

    @PostMapping("/payment")
    public Payment processPayment(@RequestBody Payment payment) {
        String txId = UUID.randomUUID().toString();
        payment.setTransactionId(txId);
        //do third party call for payment,then set the payment status
        //as of now am setting payment status as success
        payment.setPaymentStatus("SUCCESS");
        return repository.save(payment);
    }

    @GetMapping("/{orderId}")
    public Payment findPaymentByOrderId(@PathVariable int orderId) {
        return repository.findByOrderId(orderId);
    }

    public static void main(String[] args) {
        SpringApplication.run(PsApplication.class, args);
    }

}
