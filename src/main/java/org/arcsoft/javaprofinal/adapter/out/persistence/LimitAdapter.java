package org.arcsoft.javaprofinal.adapter.out.persistence;

import org.arcsoft.javaprofinal.application.domain.model.MaxLimit;
import org.arcsoft.javaprofinal.application.port.out.persistence.LimitPort;
import org.arcsoft.javaprofinal.application.port.out.persistence.entity.UserLimitEntity;
import org.arcsoft.javaprofinal.application.port.out.persistence.repository.LimitRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class LimitAdapter implements LimitPort {

    private final LimitRepository limitRepository;
    private final LimitMapper mapper;
    private final BigDecimal defaultLimit;

    public LimitAdapter(LimitRepository limitRepository,
                        final LimitMapper mapper,
                        @Value("${app.default-limit}") BigDecimal defaultLimit) {
        this.limitRepository = limitRepository;
        this.defaultLimit = defaultLimit;
        this.mapper = mapper;
    }


    @Override
    @Transactional
    public MaxLimit getUserMaxLimit(final Long userId) {
        return mapper.toModel(limitRepository.findById(userId).orElseGet(() -> limitRepository.save(new UserLimitEntity(userId, defaultLimit))));
    }

    @Override
    @Transactional
    public MaxLimit updateMaxLimit(final MaxLimit newMaxLimit) {
        var result = limitRepository.findById(newMaxLimit.userId())
                .map(l -> {
                    l.setMaxLimit(newMaxLimit.maxLimit());
                    return l;
                })
                .orElseGet(() -> mapper.toEntity(newMaxLimit));
        limitRepository.save(result);
        return mapper.toModel(result);
    }
}
