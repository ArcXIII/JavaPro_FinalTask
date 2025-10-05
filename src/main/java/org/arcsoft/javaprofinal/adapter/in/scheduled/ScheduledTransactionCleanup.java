package org.arcsoft.javaprofinal.adapter.in.scheduled;

import lombok.RequiredArgsConstructor;
import org.arcsoft.javaprofinal.application.port.in.TransactionCleanerUseCase;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScheduledTransactionCleanup {
    private final TransactionCleanerUseCase transactionCleanerUseCase;

    @Scheduled(cron = "1 0 0 * * *")
    public void cleanup() {
        transactionCleanerUseCase.cleanup();
    }
}
