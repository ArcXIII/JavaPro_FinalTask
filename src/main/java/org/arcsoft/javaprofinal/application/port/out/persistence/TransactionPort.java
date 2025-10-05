package org.arcsoft.javaprofinal.application.port.out.persistence;

import org.arcsoft.javaprofinal.application.domain.model.Transaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface TransactionPort {

    List<Transaction> getTransactionHistory(Long userId);

    Transaction newTransaction(Long userId, BigDecimal amount);

    BigDecimal getSpentAmountForUserOnCurrentDay(Long userId);

    Transaction compensate(UUID id);
}
