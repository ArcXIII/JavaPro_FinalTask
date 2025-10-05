package org.arcsoft.javaprofinal.adapter.in.http.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionHistoryResponse {
    List<TransactionDto> transactions;
}
