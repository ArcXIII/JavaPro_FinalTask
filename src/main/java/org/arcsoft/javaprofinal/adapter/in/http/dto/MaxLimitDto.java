package org.arcsoft.javaprofinal.adapter.in.http.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaxLimitDto {
    private Long userId;
    private BigDecimal maxLimit;
}
