package com.irfan.moneyger.service.impl;

import com.irfan.moneyger.dto.request.TransactionRequest;
import com.irfan.moneyger.dto.response.TransactionResponse;
import com.irfan.moneyger.entity.MUser;
import com.irfan.moneyger.entity.TTransaction;
import com.irfan.moneyger.repository.TransactionRepository;
import com.irfan.moneyger.service.TransactionService;
import com.irfan.moneyger.service.UserService;
import com.irfan.moneyger.specification.TransactionSpecification;
import com.irfan.moneyger.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
                income,
                expense,
                balance
        );

        userService.updateBalanceById(transactionRequest.getUserId(), balance);

        TTransaction transaction = findByIdOrThrowException(id);

        return transactionResponseBuilder(transaction);
    }

    @Override
    public TransactionResponse getByIdDTO(String transactionId) {
        TTransaction transaction = findByIdOrThrowException(transactionId);

        return transactionResponseBuilder(transaction);
    }

    @Override
    public Page<TransactionResponse> getAll(TransactionRequest transactionRequest) {
        if (transactionRequest.getPage() <= 0) transactionRequest.setPage(1);

        Sort sort = Sort.by(Sort.Direction.fromString(transactionRequest.getDirection()), transactionRequest.getSortBy());
        Pageable pageable = PageRequest.of((transactionRequest.getPage() - 1), transactionRequest.getSize(), sort);
        Specification<TTransaction> specification = TransactionSpecification.getSpecification(transactionRequest);

        return transactionRepository.findAll(specification, pageable).map(this::transactionResponseBuilder);
    }

    @Override
    public TransactionResponse update(TransactionRequest transactionRequest) {
        TTransaction currTransaction = findByIdOrThrowException(transactionRequest.getId());

        Date updatedDate = DateUtil.parseDate(transactionRequest.getDate(), "yyyy-MM-dd");

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

        Long balance = currTransaction.getBalance() + (income - expense);

        transactionRepository.updateQuery(
                currTransaction.getId(),
                transactionRequest.getUserId(),
                updatedDate,
                transactionRequest.getCategory(),
                transactionRequest.getIncome(),
                transactionRequest.getExpense(),
                balance
        );

        TTransaction updatedTransaction = TTransaction.builder()
                .id(currTransaction.getId())
                .user(currTransaction.getUser())
                .date(updatedDate)
                .category(transactionRequest.getCategory())
                .income(transactionRequest.getIncome())
                .expense(transactionRequest.getExpense())
                .balance(balance)
                .build();

        return transactionResponseBuilder(updatedTransaction);
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
