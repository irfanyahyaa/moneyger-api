package com.irfan.moneyger.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionRequest {
    private String id;
    private String userId;
    private String date;
    private String category;
    private Long income;
    private Long expense;
    private Integer page;
    private Integer size;
    private String sortBy;
    private String direction;
}
