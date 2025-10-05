package org.arcsoft.javaprofinal;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.arcsoft.javaprofinal.adapter.in.http.LimitOperations;
import org.arcsoft.javaprofinal.adapter.in.http.dto.LimitUpdateRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.arcsoft.javaprofinal.common.BigDecimalFormatter.format;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Sql(statements = "TRUNCATE TABLE USER_LIMITS", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Sql(statements = "TRUNCATE TABLE TRANSACTIONS", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class LimitIntegrationTests {

    private static final Long USER_ID = 1L;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LimitOperations limitOperations;

    @Value("${app.default-limit}")
    private BigDecimal defaultLimit;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void test_GetMaxLimitForUser_WhenGet_ThenOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/limits/{userId}", USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.userId").value(USER_ID.intValue()),
                        jsonPath("$.maxLimit").value(defaultLimit.intValue())
                );
    }

    @Test
    void test_GetCurrentLimitForUser_WhenGet_ThenOk() throws Exception {
        limitOperations.decreaseCurrentLimit(USER_ID, new LimitUpdateRequest(BigDecimal.valueOf(defaultLimit.intValue() - 500)));
        mockMvc.perform(MockMvcRequestBuilders.get("/limits/{userId}/current", USER_ID))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.userId").value(USER_ID.intValue()),
                        jsonPath("$.maxLimit").value(defaultLimit.doubleValue()),
                        jsonPath("$.currentLimit").value(500.0),
                        jsonPath("$.transactionId").doesNotExist()
                );
    }

    @Test
    void test_UpdateLimitForUser_WhenNewValueOkAndUserExists_ThenOk() throws Exception {
        limitOperations.getMaxLimit(USER_ID);
        mockMvc.perform(MockMvcRequestBuilders.put("/limits/{userId}", USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new LimitUpdateRequest(BigDecimal.valueOf(100500.01)))))
                .andExpectAll(status().isOk(),
                        jsonPath("$.userId").value(USER_ID.intValue()),
                        jsonPath("$.maxLimit").value(100500.01));
    }

    @Test
    void test_UpdateLimitForUser_WhenNewValueOkAndUserNotExists_ThenOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/limits/{userId}", USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new LimitUpdateRequest(BigDecimal.valueOf(100500.01)))))
                .andExpectAll(status().isOk(),
                        jsonPath("$.userId").value(USER_ID.intValue()),
                        jsonPath("$.maxLimit").value(100500.01));
    }


    @Test
    void test_DecreaseLimitForUser_WhenNewValueOk_ThenOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/limits/{userId}", USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new LimitUpdateRequest(BigDecimal.valueOf(500)))))
                .andExpectAll(status().isOk(),
                        jsonPath("$.userId").value(USER_ID.intValue()),
                        jsonPath("$.maxLimit").value(10000),
                        jsonPath("$.currentLimit").value(9500),
                        jsonPath("$.transactionId").isNotEmpty());
    }

    @Test
    void test_UpdateLimitForUser_WhenNewValueLessThanRequired_ThenError() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/limits/{userId}", USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new LimitUpdateRequest(BigDecimal.valueOf(-1)))))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.message").value("Method argument not valid"),
                        jsonPath("$.errors").isNotEmpty()
                );
    }

    @Test
    void test_DecreaseLimitForUser_WhenAmountLessThanRequired_ThenError() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/limits/{userId}", USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new LimitUpdateRequest(BigDecimal.valueOf(-1)))))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.message").value("Method argument not valid"),
                        jsonPath("$.errors").isNotEmpty()
                );
    }

    @Test
    void test_DecreaseLimitForUser_WhenAmountExceedsLimit_ThenError() throws Exception {
        limitOperations.getMaxLimit(USER_ID);
        final var requestedAmount = defaultLimit.add(BigDecimal.ONE);
        mockMvc.perform(MockMvcRequestBuilders.post("/limits/{userId}", USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new LimitUpdateRequest(requestedAmount))))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.message")
                                .value("Current limit exceeded for user with Id: " + USER_ID +
                                        ", current limit: " + format(defaultLimit) +
                                        ", requested amount: " + format(requestedAmount)),
                        jsonPath("$.errors").doesNotExist()
                );
    }
}
