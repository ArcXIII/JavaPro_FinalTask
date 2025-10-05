package org.arcsoft.javaprofinal.application.port.out.persistence;

import org.arcsoft.javaprofinal.application.domain.model.MaxLimit;

public interface LimitPort {
    MaxLimit getUserMaxLimit(Long userId);

    MaxLimit updateMaxLimit(MaxLimit newMaxLimit);

}
