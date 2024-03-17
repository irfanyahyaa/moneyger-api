package com.irfan.moneyger.controller;

import com.irfan.moneyger.constant.APIUrl;
import com.irfan.moneyger.dto.request.TransactionRequest;
import com.irfan.moneyger.dto.response.CommonResponse;
import com.irfan.moneyger.dto.response.TransactionResponse;
import com.irfan.moneyger.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(APIUrl.TRANSACTION)
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<TransactionResponse>> createTransaction(
            @RequestBody TransactionRequest request
    ) {
        TransactionResponse transactionResponse = transactionService.create(request);

        CommonResponse<TransactionResponse> response = CommonResponse.<TransactionResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("transaction created successfully")
                .data(transactionResponse)
                .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}
