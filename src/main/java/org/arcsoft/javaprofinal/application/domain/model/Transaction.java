package org.arcsoft.javaprofinal.application.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public final class Transaction {
    private UUID id;
    @Setter
    private Long userId;
    @Setter
    private BigDecimal amount;
    private OffsetDateTime created;
    @Setter
    private boolean compensated;
}
