package org.arcsoft.javaprofinal.adapter.in.http.mapper;

import org.arcsoft.javaprofinal.adapter.in.http.dto.CompensationDto;
import org.arcsoft.javaprofinal.adapter.in.http.dto.TransactionDto;
import org.arcsoft.javaprofinal.application.domain.model.Compensation;
import org.arcsoft.javaprofinal.application.domain.model.Transaction;
import org.mapstruct.Mapper;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface TransactionDtoMapper {
    TransactionDto toDto(Transaction transaction);

    List<TransactionDto> toDto(List<Transaction> transaction);

    CompensationDto toDto(Compensation compensation);
}
