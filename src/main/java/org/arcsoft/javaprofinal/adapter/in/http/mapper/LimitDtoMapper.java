package org.arcsoft.javaprofinal.adapter.in.http.mapper;

import org.arcsoft.javaprofinal.adapter.in.http.dto.CurrentLimitDto;
import org.arcsoft.javaprofinal.adapter.in.http.dto.MaxLimitDto;
import org.arcsoft.javaprofinal.application.domain.model.CurrentLimit;
import org.arcsoft.javaprofinal.application.domain.model.MaxLimit;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.ReportingPolicy.IGNORE;

@Mapper(componentModel = SPRING, unmappedTargetPolicy = IGNORE)
public interface LimitDtoMapper {
    MaxLimitDto toDto(MaxLimit maxLimit);

    CurrentLimitDto toDto(CurrentLimit currentLimit);

}
