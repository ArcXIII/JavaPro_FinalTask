package org.arcsoft.javaprofinal.application.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.arcsoft.javaprofinal.adapter.in.http.dto.CompensationResult;
import org.arcsoft.javaprofinal.application.domain.model.Compensation;
import org.arcsoft.javaprofinal.application.domain.model.Transaction;
import org.arcsoft.javaprofinal.application.domain.model.exception.NotFoundException;
import org.arcsoft.javaprofinal.application.port.in.TransactionUseCase;
import org.arcsoft.javaprofinal.application.port.out.persistence.LimitPort;
import org.arcsoft.javaprofinal.application.port.out.persistence.TransactionPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService implements TransactionUseCase {
    private final TransactionPort transactionPort;
    private final LimitPort limitPort;


    @Override
    public List<Transaction> getTransactionHistory(final Long userId) {
        return transactionPort.getTransactionHistory(userId);
    }

    @Override
    public Compensation compensateTransaction(final UUID transactionId) {
        try {
            var transaction = transactionPort.compensate(transactionId);

            var spent = transactionPort.getSpentAmountForUserOnCurrentDay(transaction.getUserId());
            var maxLimit = limitPort.getUserMaxLimit(transaction.getUserId());

            return Compensation.builder()
                    .currentLimit(maxLimit.getCurrentLimit(spent))
                    .result(CompensationResult.SUCCESS)
                    .userId(transaction.getUserId())
                    .build();
        } catch (NotFoundException e) {
            log.error(e.getMessage());
            return Compensation.builder()
                    .result(CompensationResult.FAILURE)
                    .build();
        }
    }
}
