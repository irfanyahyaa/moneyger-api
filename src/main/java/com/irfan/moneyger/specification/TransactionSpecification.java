package com.irfan.moneyger.specification;

import com.irfan.moneyger.dto.request.TransactionRequest;
import com.irfan.moneyger.entity.TTransaction;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TransactionSpecification {
    public static Specification<TTransaction> getSpecification(TransactionRequest transactionRequest) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (transactionRequest.getId() != null) {
                predicates.add(
                        criteriaBuilder.like(
                                root.get("id"),
                                "%" + transactionRequest.getId() + "%"
                        )
                );
            }

            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        };
    }
}
