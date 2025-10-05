package org.arcsoft.javaprofinal.application.port.out.persistence.repository;

import org.arcsoft.javaprofinal.application.port.out.persistence.entity.TransactionEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends CrudRepository<TransactionEntity, UUID> {

    @Query(value = """
            SELECT t from TransactionEntity t
            WHERE t.compensated = false and cast(t.created as localdate) = cast(now() as localdate)
            """)
    List<TransactionEntity> findByUserIdOnCurrentDay(Long userId);

    List<TransactionEntity> findByUserId(Long userId);

    @Modifying
    @Query("""
            DELETE FROM TransactionEntity t
            where t.created < :createdBefore
            """)
    void deleteByCreatedIsBefore(@Param("createdBefore") OffsetDateTime createdBefore);
}
