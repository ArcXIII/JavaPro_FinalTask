package org.arcsoft.javaprofinal.adapter.in.http;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.arcsoft.javaprofinal.adapter.in.http.dto.CompensationDto;
import org.arcsoft.javaprofinal.adapter.in.http.dto.TransactionHistoryResponse;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Операции с транзакциями")
@RequestMapping("transactions")
public interface TransactionOperations {

    @Operation(summary = "Получить историю транзакций",
            description = "Возвращает список транзакций",
            responses = {@ApiResponse(responseCode = "200", description = "Получен список транзакций")})
    @GetMapping
    TransactionHistoryResponse getTransactionHistory(@RequestParam(value = "userId", required = false) Long userId);

    @Operation(summary = "Компенсировать платёж",
            description = "Компенсирует транзакцию платежа",
            responses = @ApiResponse(responseCode = "200", description = "Результат компенсации"))
    @PostMapping("{transactionId}/compensate")
    CompensationDto compensateTransaction(@PathVariable("transactionId") final UUID transactionId);
}
