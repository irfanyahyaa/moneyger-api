package com.irfan.moneyger.controller;

import com.irfan.moneyger.constant.APIUrl;
import com.irfan.moneyger.dto.request.TransactionRequest;
import com.irfan.moneyger.dto.response.CommonResponse;
import com.irfan.moneyger.dto.response.PagingResponse;
import com.irfan.moneyger.dto.response.TransactionResponse;
import com.irfan.moneyger.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<CommonResponse<List<TransactionResponse>>> getAll(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "id") String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc") String direction
    ) {
        TransactionRequest request = TransactionRequest.builder()
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .build();
        Page<TransactionResponse> transaction = transactionService.getAll(request);

        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPages(transaction.getTotalPages())
                .totalElement(transaction.getTotalElements())
                .page(transaction.getPageable().getPageNumber() + 1)
                .size(transaction.getPageable().getPageSize())
                .hasNext(transaction.hasNext())
                .hasPrevious(transaction.hasPrevious())
                .build();

        CommonResponse<List<TransactionResponse>> response = CommonResponse.<List<TransactionResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message("transaction fetched successfully")
                .data(transaction.getContent())
                .paging(pagingResponse)
                .build();

        return ResponseEntity
                .ok(response);
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
