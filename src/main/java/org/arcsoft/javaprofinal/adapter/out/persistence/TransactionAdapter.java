package org.arcsoft.javaprofinal.adapter.out.persistence;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.arcsoft.javaprofinal.application.domain.model.Transaction;
import org.arcsoft.javaprofinal.application.domain.model.exception.NotFoundException;
import org.arcsoft.javaprofinal.application.port.out.persistence.TransactionCleanerPort;
import org.arcsoft.javaprofinal.application.port.out.persistence.TransactionPort;
import org.arcsoft.javaprofinal.application.port.out.persistence.entity.TransactionEntity;
import org.arcsoft.javaprofinal.application.port.out.persistence.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionAdapter implements TransactionPort, TransactionCleanerPort {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper mapper;

    @Override
    public List<Transaction> getTransactionHistory(final Long userId) {
        if (userId == null) {
            return mapper.toModel(transactionRepository.findAll());
        } else {
            return mapper.toModel(transactionRepository.findByUserId(userId));
        }
    }

    @Override
    @Transactional
    public Transaction newTransaction(final Long userId, final BigDecimal amount) {
        return mapper.toModel(transactionRepository.save(
                TransactionEntity.builder()
                        .amount(amount)
                        .compensated(false)
                        .userId(userId)
                        .build()));
    }

    @Override
    @Transactional
    public BigDecimal getSpentAmountForUserOnCurrentDay(final Long userId) {
        var transactions = transactionRepository.findByUserIdOnCurrentDay(userId);
        return transactions.stream()
                .map(TransactionEntity::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    @Transactional
    public Transaction compensate(final UUID id) {
        var transaction = transactionRepository.findById(id);

        var result = transaction.map(t -> {
            t.setCompensated(true);
            return t;
        }).orElseThrow(() -> new NotFoundException("Не найдена транзакция для компенсации c id:" + id));

        return mapper.toModel(result);
    }

    @Override
    @Transactional
    public void removeOldTransactions(final Integer keepDays) {
        final var deleteBefore = OffsetDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0).minusDays(keepDays);
        transactionRepository.deleteByCreatedIsBefore(deleteBefore);
    }
}
