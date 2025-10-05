package org.arcsoft.javaprofinal;

import org.arcsoft.javaprofinal.adapter.in.http.LimitOperations;
import org.arcsoft.javaprofinal.adapter.in.http.dto.CompensationResult;
import org.arcsoft.javaprofinal.adapter.in.http.dto.LimitUpdateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static java.util.UUID.randomUUID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Sql(statements = "TRUNCATE TABLE USER_LIMITS", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(statements = "TRUNCATE TABLE TRANSACTIONS", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class TransactionIntegrationTests {

    private static final Long USER_ID = 1L;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LimitOperations limitOperations;

    @Value("${app.default-limit}")
    private BigDecimal defaultLimit;

    @Test
    void testGetTransactionHistoryForUser() throws Exception {
        limitOperations.getMaxLimit(USER_ID);
        var decreaseAmount = defaultLimit.subtract(BigDecimal.valueOf(500));
        var currentLimit = limitOperations.decreaseCurrentLimit(USER_ID, new LimitUpdateRequest(decreaseAmount));
        var transactionId = currentLimit.getTransactionId();
        mockMvc.perform(get("/transactions").queryParam("userId", USER_ID.toString()))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.transactions").isNotEmpty(),
                        jsonPath("$.transactions[0].id").value(transactionId.toString()),
                        jsonPath("$.transactions[0].userId").value(USER_ID),
                        jsonPath("$.transactions[0].amount").value(9500),
                        jsonPath("$.transactions[0].compensated").value(false)
                );
    }


    @Test
    void testGetTransactionHistoryForAll() throws Exception {
        limitOperations.getMaxLimit(USER_ID);
        var decreaseAmount = defaultLimit.subtract(BigDecimal.valueOf(500));
        var currentLimit = limitOperations.decreaseCurrentLimit(USER_ID, new LimitUpdateRequest(decreaseAmount));
        var transactionId = currentLimit.getTransactionId();
        mockMvc.perform(get("/transactions"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.transactions").isNotEmpty(),
                        jsonPath("$.transactions[0].id").value(transactionId.toString()),
                        jsonPath("$.transactions[0].userId").value(USER_ID),
                        jsonPath("$.transactions[0].amount").value(9500),
                        jsonPath("$.transactions[0].compensated").value(false)
                );
    }

    @Test
    void testCompensateTransaction_WhenTransactionIdPresent_ThenOk() throws Exception {
        limitOperations.getMaxLimit(USER_ID);
        var decreaseAmount = defaultLimit.subtract(BigDecimal.valueOf(500));
        var currentLimit = limitOperations.decreaseCurrentLimit(USER_ID, new LimitUpdateRequest(decreaseAmount));
        var transactionId = currentLimit.getTransactionId();
        mockMvc.perform(post("/transactions/{transactionId}/compensate", transactionId))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.userId").value(USER_ID),
                        jsonPath("$.result").value(CompensationResult.SUCCESS.name()),
                        jsonPath("$.currentLimit").value(defaultLimit.doubleValue())
                );
    }

    @Test
    void testCompensateTransaction_WhenTransactionIdNotPresent_ThenFailure() throws Exception {
        mockMvc.perform(post("/transactions/{transactionId}/compensate", randomUUID()))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.result").value(CompensationResult.FAILURE.name()),
                        jsonPath("$.userId").doesNotExist(),
                        jsonPath("$.currentLimit").doesNotExist()
                );
    }
}
