package org.arcsoft.javaprofinal.adapter.in.http;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.arcsoft.javaprofinal.adapter.in.http.dto.CurrentLimitDto;
import org.arcsoft.javaprofinal.adapter.in.http.dto.LimitUpdateRequest;
import org.arcsoft.javaprofinal.adapter.in.http.dto.MaxLimitDto;
import org.arcsoft.javaprofinal.adapter.in.http.mapper.LimitDtoMapper;
import org.arcsoft.javaprofinal.application.port.in.LimitUseCase;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LimitController implements LimitOperations {

    private final LimitUseCase limitUseCase;
    private final LimitDtoMapper mapper;

    @Override
    public MaxLimitDto getMaxLimit(final Long userId) {
        return mapper.toDto(limitUseCase.getMaxLimit(userId));
    }

    @Override
    public CurrentLimitDto getCurrentLimit(final Long userId) {
        return mapper.toDto(limitUseCase.getCurrentLimit(userId));
    }

    @Override
    public MaxLimitDto updateMaxLimit(final Long userId, @Valid final LimitUpdateRequest updateRequest) {
        return mapper.toDto(limitUseCase.updateMaxLimit(userId, updateRequest.getAmount()));
    }

    @Override
    public CurrentLimitDto decreaseCurrentLimit(final Long userId, @Valid final LimitUpdateRequest updateRequest) {
        return mapper.toDto(limitUseCase.decreaseCurrentLimit(userId, updateRequest.getAmount()));
    }
}
