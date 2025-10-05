package org.arcsoft.javaprofinal.adapter.in.http.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
    UUID id;
    Long userId;
    BigDecimal amount;
    OffsetDateTime created;
    boolean compensated;
}
