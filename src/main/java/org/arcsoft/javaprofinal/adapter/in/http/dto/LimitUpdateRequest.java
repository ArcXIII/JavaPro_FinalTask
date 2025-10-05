package org.arcsoft.javaprofinal.adapter.in.http.dto;

import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LimitUpdateRequest {
    @DecimalMin(value = "0.01")
    BigDecimal amount;
}
