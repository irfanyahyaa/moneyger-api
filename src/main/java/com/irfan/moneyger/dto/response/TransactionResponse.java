package com.irfan.moneyger.dto.response;

import com.irfan.moneyger.entity.MUser;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse {
    private String id;
    private MUser user;
    private Date date;
    private String category;
    private Long income;
    private Long expense;
    private Long balance;
}
