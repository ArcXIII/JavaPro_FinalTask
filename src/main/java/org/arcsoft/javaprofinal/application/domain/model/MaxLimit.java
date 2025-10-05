package org.arcsoft.javaprofinal.application.domain.model;

import java.math.BigDecimal;

public record MaxLimit(Long userId, BigDecimal maxLimit) {
    public BigDecimal getCurrentLimit(BigDecimal spent) {
        return maxLimit.subtract(spent);
    }
}
