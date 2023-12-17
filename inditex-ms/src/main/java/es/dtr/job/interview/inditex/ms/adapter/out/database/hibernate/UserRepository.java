package es.dtr.job.interview.inditex.ms.adapter.out.database.hibernate;

import es.dtr.job.interview.commons.data.CrudRepository;
import es.dtr.job.interview.commons.data.querydsl.QueryDslCustomTypesManager;
import es.dtr.job.interview.inditex.ms.domain.entity.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, UUID> {

    // Configuration
    QueryDslCustomTypesManager CUSTOM_TYPES = new QueryDslCustomTypesManager();

    Optional<UserEntity> findByUsername(String username);

    @Override
    default QueryDslCustomTypesManager getCustomTypesManager() {
        return CUSTOM_TYPES;
    }
}
