package org.arcsoft.javaprofinal.adapter.out.persistence;

import org.arcsoft.javaprofinal.application.domain.model.MaxLimit;
import org.arcsoft.javaprofinal.application.port.out.persistence.entity.UserLimitEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface LimitMapper {
    MaxLimit toModel(UserLimitEntity entity);

    UserLimitEntity toEntity(MaxLimit limit);
}
