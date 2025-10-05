package org.arcsoft.javaprofinal;

import org.arcsoft.javaprofinal.adapter.in.scheduled.ScheduledTransactionCleanup;
import org.arcsoft.javaprofinal.application.port.out.persistence.TransactionPort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@Sql(value = "classpath:sql/fill-transactions.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(statements = "TRUNCATE TABLE TRANSACTIONS", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class TransactionCleanupIntegrationTest {

    @Autowired
    private TransactionPort transactionPort;

    @Autowired
    private ScheduledTransactionCleanup cleaner;

    @Test
    void testTransactionCleanup() {
        var history = transactionPort.getTransactionHistory(null);

        assertEquals(10, history.size());

        cleaner.cleanup();

        history = transactionPort.getTransactionHistory(null);

        assertEquals(0, history.size());
    }
}
