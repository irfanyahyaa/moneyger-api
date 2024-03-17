package com.irfan.moneyger.service.impl;

import com.irfan.moneyger.dto.request.TransactionRequest;
import com.irfan.moneyger.dto.response.TransactionResponse;
import com.irfan.moneyger.entity.MUser;
import com.irfan.moneyger.entity.TTransaction;
import com.irfan.moneyger.repository.TransactionRepository;
import com.irfan.moneyger.service.TransactionService;
import com.irfan.moneyger.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserService userService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TransactionResponse create(TransactionRequest transactionRequest) {
        MUser currUser = userService.getByIdEntity(transactionRequest.getUserId());

        String id = UUID.randomUUID().toString();
        Date date = new Date();

        if (transactionRequest.getIncome() == null && transactionRequest.getExpense() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "income and expense cannot be null");
        } else if ((transactionRequest.getIncome() != null && transactionRequest.getIncome() != 0L) && (transactionRequest.getExpense() != null && transactionRequest.getExpense() != 0L)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "fill either income or expense");
        }

        Long income = transactionRequest.getIncome();
        if (income == null) {
            income = 0L;
        }

        Long expense = transactionRequest.getExpense();
        if (expense == null) {
            expense = 0L;
        }

        Long balance = currUser.getBalance() + (income - expense);

        transactionRepository.createQuery(
                id,
                transactionRequest.getUserId(),
                date,
                transactionRequest.getCategory(),
                transactionRequest.getIncome(),
                transactionRequest.getExpense(),
                balance
        );

        userService.updateBalanceById(transactionRequest.getUserId(), balance);

        TTransaction transaction = findByIdOrThrowException(id);

        return transactionResponseBuilder(transaction);
    }

    @Override
    public TransactionResponse getByIdDTO(String id) {
        return null;
    }

    @Override
    public TransactionResponse getAll() {
        return null;
    }

    @Override
    public TransactionResponse update(TransactionRequest userRequest) {
        return null;
    }

    @Override
    public TransactionResponse updateIsActiveById(String id, Boolean isActive) {
        return null;
    }

    public TTransaction findByIdOrThrowException(String id) {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "transaction not found"));
    }

    public TransactionResponse transactionResponseBuilder(TTransaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .user(transaction.getUser())
                .date(transaction.getDate())
                .category(transaction.getCategory())
                .income(transaction.getIncome())
                .expense(transaction.getExpense())
                .balance(transaction.getBalance())
                .build();
    }
}
