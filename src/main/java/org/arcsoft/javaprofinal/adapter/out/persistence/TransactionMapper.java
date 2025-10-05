package org.arcsoft.javaprofinal.adapter.out.persistence;

import org.arcsoft.javaprofinal.application.domain.model.Transaction;
import org.arcsoft.javaprofinal.application.port.out.persistence.entity.TransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TransactionMapper {

    Transaction toModel(TransactionEntity transactionEntity);

    List<Transaction> toModel(Iterable<TransactionEntity> transactionEntity);
}