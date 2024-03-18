package com.irfan.moneyger.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    private String id;
    private String firstName;
    private String lastName;
    private Long balance;
    private Boolean isActive;
    private Integer page;
    private Integer size;
    private String sortBy;
    private String direction;
}
