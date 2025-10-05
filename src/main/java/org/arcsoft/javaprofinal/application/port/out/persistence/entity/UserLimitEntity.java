package org.arcsoft.javaprofinal.application.port.out.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_limits")
public class UserLimitEntity {
    @Id
    private Long userId;

    private BigDecimal maxLimit;
}
