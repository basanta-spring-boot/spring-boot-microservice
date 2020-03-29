package com.javatechie.os;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransactionResponse {
    private Order order;
    private String transactionId;
    private String userName;
}
