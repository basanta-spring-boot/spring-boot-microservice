package com.javatechie.gs;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallBackController {

    @RequestMapping("/orderFallBack")
    public Mono<String> orderServiceFallBack() {
        return Mono.just("Order API is taking too long to respond or is down. Please try again later");
    }
    @RequestMapping("/paymentfallback")
    public Mono<String> paymentServiceFallBack() {
        return Mono.just("Payment API is taking too long to respond or is down. Please try again later");
    }
}
