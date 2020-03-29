package com.javatechie.os;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Payment {
    private int paymentId;
    private String transactionId;
    private int orderId;
    private String paymentStatus;
}
