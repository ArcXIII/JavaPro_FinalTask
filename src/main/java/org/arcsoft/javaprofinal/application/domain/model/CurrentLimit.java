package org.arcsoft.javaprofinal.application.domain.model;

import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record CurrentLimit(Long userId, BigDecimal currentLimit, BigDecimal maxLimit, UUID transactionId) {
}
