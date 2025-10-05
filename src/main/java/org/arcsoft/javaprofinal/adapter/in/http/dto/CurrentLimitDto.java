package org.arcsoft.javaprofinal.adapter.in.http.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CurrentLimitDto extends MaxLimitDto {
    private BigDecimal currentLimit;
    private UUID transactionId;
}
