package org.arcsoft.javaprofinal.adapter.in.http;

import lombok.RequiredArgsConstructor;
import org.arcsoft.javaprofinal.adapter.in.http.dto.CompensationDto;
import org.arcsoft.javaprofinal.adapter.in.http.dto.TransactionHistoryResponse;
import org.arcsoft.javaprofinal.adapter.in.http.mapper.TransactionDtoMapper;
import org.arcsoft.javaprofinal.application.port.in.TransactionUseCase;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class TransactionController implements TransactionOperations {

    private final TransactionUseCase transactionUseCase;
    private final TransactionDtoMapper mapper;

    @Override
    public TransactionHistoryResponse getTransactionHistory(final Long userId) {
        var transactions = mapper.toDto(transactionUseCase.getTransactionHistory(userId));
        return new TransactionHistoryResponse(transactions);
    }

    @Override
    public CompensationDto compensateTransaction(final UUID transactionId) {
        return mapper.toDto(transactionUseCase.compensateTransaction(transactionId));
    }
}
