package org.arcsoft.javaprofinal.application.port.in;

import org.arcsoft.javaprofinal.application.domain.model.CurrentLimit;
import org.arcsoft.javaprofinal.application.domain.model.MaxLimit;

import java.math.BigDecimal;

public interface LimitUseCase {
    MaxLimit getMaxLimit(Long userId);

    CurrentLimit getCurrentLimit(Long userId);

    MaxLimit updateMaxLimit(Long userId, BigDecimal maxLimit);

    CurrentLimit decreaseCurrentLimit(Long userId, BigDecimal amount);
}
