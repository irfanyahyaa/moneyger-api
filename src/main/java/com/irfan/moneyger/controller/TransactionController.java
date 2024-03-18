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
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<TransactionResponse>> getById(
            @PathVariable("id") String id
    ) {
        TransactionResponse transactionResponse = transactionService.getByIdDTO(id);

        CommonResponse<TransactionResponse> response = CommonResponse.<TransactionResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message("transaction fetched successfully")
                .data(transactionResponse)
                .build();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<TransactionResponse>> getAll(String userId) {
        TransactionResponse transactionResponse = transactionService.getAllByIdDTO(userId);
        return null;
    }

    @PutMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<TransactionResponse>> updateTransaction(
            @RequestBody TransactionRequest request
    ) {
        TransactionResponse transactionResponse = transactionService.update(request);
        return null;
    }
}
