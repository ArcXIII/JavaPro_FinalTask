package org.arcsoft.javaprofinal.application.domain.model.exception;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.arcsoft.javaprofinal.common.BigDecimalFormatter.format;

public class LimitExceededException extends RuntimeException {
    public LimitExceededException(Long userId, BigDecimal currentLimit, BigDecimal requestedAmount) {
        super("Current limit exceeded for user with Id: " + userId +
                ", current limit: " + format(currentLimit.setScale(2, RoundingMode.UNNECESSARY)) +
                ", requested amount: " + format(requestedAmount.setScale(2, RoundingMode.UNNECESSARY)));
    }
}
