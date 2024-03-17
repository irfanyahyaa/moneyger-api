package com.irfan.moneyger.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionRequest {
    private String userId;
    private String category;
    private Long income;
    private Long expense;
}
