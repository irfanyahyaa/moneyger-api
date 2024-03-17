package com.irfan.moneyger.service;

import com.irfan.moneyger.dto.request.TransactionRequest;
import com.irfan.moneyger.dto.response.TransactionResponse;

public interface TransactionService {
    TransactionResponse create(TransactionRequest userRequest);

    TransactionResponse getByIdDTO(String id);

    TransactionResponse getAll();

    TransactionResponse update(TransactionRequest userRequest);

    TransactionResponse updateIsActiveById(String id, Boolean isActive);
}
