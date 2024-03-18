package com.irfan.moneyger.service;

import com.irfan.moneyger.dto.request.TransactionRequest;
import com.irfan.moneyger.dto.response.TransactionResponse;
import org.springframework.data.domain.Page;

public interface TransactionService {
    TransactionResponse create(TransactionRequest transactionRequest);

    TransactionResponse getByIdDTO(String transactionId);

    Page<TransactionResponse> getAll(TransactionRequest transactionRequest);

    TransactionResponse update(TransactionRequest transactionRequest);
}
