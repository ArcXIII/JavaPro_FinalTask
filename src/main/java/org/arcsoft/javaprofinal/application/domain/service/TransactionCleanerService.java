package org.arcsoft.javaprofinal.application.domain.service;

import org.arcsoft.javaprofinal.application.port.in.TransactionCleanerUseCase;
import org.arcsoft.javaprofinal.application.port.out.persistence.TransactionCleanerPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TransactionCleanerService implements TransactionCleanerUseCase {

    private final Integer keepHistoryFor;
    private final TransactionCleanerPort transactionPort;

    public TransactionCleanerService(
            @Value("${app.keep-transaction-history:3}") Integer keepHistoryFor,
            TransactionCleanerPort transactionPort) {
        this.keepHistoryFor = keepHistoryFor;
        this.transactionPort = transactionPort;
    }

    public void cleanup() {
        transactionPort.removeOldTransactions(keepHistoryFor);
    }
}
