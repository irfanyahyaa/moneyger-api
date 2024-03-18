package com.irfan.moneyger.service;

import com.irfan.moneyger.dto.request.TransactionRequest;
import com.irfan.moneyger.dto.response.TransactionResponse;

public interface TransactionService {
    TransactionResponse create(TransactionRequest transactionRequest);

    TransactionResponse getByIdDTO(String transactionId);

    TransactionResponse getAllByIdDTO(String userId);

    TransactionResponse update(TransactionRequest transactionRequest);
}
