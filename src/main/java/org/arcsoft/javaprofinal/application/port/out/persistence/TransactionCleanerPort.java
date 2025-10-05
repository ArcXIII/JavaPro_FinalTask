package org.arcsoft.javaprofinal.application.port.out.persistence;

public interface TransactionCleanerPort {
    void removeOldTransactions(final Integer keepDays);
}
