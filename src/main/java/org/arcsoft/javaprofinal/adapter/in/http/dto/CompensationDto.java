package org.arcsoft.javaprofinal.adapter.in.http.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CompensationDto {
    Long userId;
    CompensationResult result;
    BigDecimal currentLimit;
}
