package org.arcsoft.javaprofinal.application.port.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "transactions")
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class TransactionEntity {

    @Id
    @GeneratedValue
    private UUID id;

    private Long userId;

    private BigDecimal amount;

    @CreatedDate
    private OffsetDateTime created;

    private boolean compensated;

    @Builder
    public TransactionEntity(Long userId, BigDecimal amount, boolean compensated) {
        this.userId = userId;
        this.amount = amount;
        this.compensated = compensated;
    }
}
