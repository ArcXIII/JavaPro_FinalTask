package org.arcsoft.javaprofinal.application.domain.model;

import lombok.Builder;
import lombok.Data;
import org.arcsoft.javaprofinal.adapter.in.http.dto.CompensationResult;

import java.math.BigDecimal;

@Data
@Builder
public class Compensation {
    Long userId;
    CompensationResult result;
    BigDecimal currentLimit;
}
