package org.arcsoft.javaprofinal.application.domain.service;

import lombok.RequiredArgsConstructor;
import org.arcsoft.javaprofinal.application.domain.model.CurrentLimit;
import org.arcsoft.javaprofinal.application.domain.model.MaxLimit;
import org.arcsoft.javaprofinal.application.domain.model.exception.LimitExceededException;
import org.arcsoft.javaprofinal.application.port.in.LimitUseCase;
import org.arcsoft.javaprofinal.application.port.out.persistence.LimitPort;
import org.arcsoft.javaprofinal.application.port.out.persistence.TransactionPort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class LimitService implements LimitUseCase {

    private final LimitPort limitPort;
    private final TransactionPort transactionPort;

    @Override
    public MaxLimit getMaxLimit(final Long userId) {
        return limitPort.getUserMaxLimit(userId);
    }

    @Override
    public CurrentLimit getCurrentLimit(final Long userId) {
        var maxLimit = limitPort.getUserMaxLimit(userId);
        var spent = transactionPort.getSpentAmountForUserOnCurrentDay(userId);
        return CurrentLimit.builder()
                .userId(userId)
                .transactionId(null)
                .maxLimit(maxLimit.maxLimit())
                .currentLimit(maxLimit.getCurrentLimit(spent))
                .build();
    }

    @Override
    public MaxLimit updateMaxLimit(final Long userId, final BigDecimal maxLimit) {
        return limitPort.updateMaxLimit(new MaxLimit(userId, maxLimit));
    }

    @Override
    public CurrentLimit decreaseCurrentLimit(final Long userId, final BigDecimal amount) {
        var maxLimit = limitPort.getUserMaxLimit(userId);
        var spent = transactionPort.getSpentAmountForUserOnCurrentDay(userId);
        var currentLimit = maxLimit.maxLimit().subtract(spent);
        if (currentLimit.compareTo(amount) <= 0) {
            throw new LimitExceededException(userId, currentLimit, amount);
        }
        var transaction = transactionPort.newTransaction(userId, amount);
        return CurrentLimit.builder()
                .userId(userId)
                .transactionId(transaction.getId())
                .maxLimit(maxLimit.maxLimit())
                .currentLimit(maxLimit.getCurrentLimit(spent.add(amount)))
                .build();
    }
}
