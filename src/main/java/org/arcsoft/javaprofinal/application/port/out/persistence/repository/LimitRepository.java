package org.arcsoft.javaprofinal.application.port.out.persistence.repository;

import org.arcsoft.javaprofinal.application.port.out.persistence.entity.UserLimitEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LimitRepository extends CrudRepository<UserLimitEntity, Long> {

}
