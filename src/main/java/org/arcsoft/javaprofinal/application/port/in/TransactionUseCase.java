package org.arcsoft.javaprofinal.application.port.in;

import org.arcsoft.javaprofinal.application.domain.model.Compensation;
import org.arcsoft.javaprofinal.application.domain.model.Transaction;

import java.util.List;
import java.util.UUID;

public interface TransactionUseCase {
    List<Transaction> getTransactionHistory(Long userId);

    Compensation compensateTransaction(final UUID transactionId);
}
