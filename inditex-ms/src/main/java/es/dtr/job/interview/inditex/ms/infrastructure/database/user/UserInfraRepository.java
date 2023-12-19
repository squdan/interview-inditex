package es.dtr.job.interview.inditex.ms.infrastructure.database.user;

import es.dtr.job.interview.commons.hexagonal.domain.repository.CrudInfraRepository;
import es.dtr.job.interview.commons.hexagonal.domain.repository.querydsl.QueryDslCustomTypesManager;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserInfraRepository extends CrudInfraRepository<UserInfraEntity, UUID> {

    // Configuration
    QueryDslCustomTypesManager CUSTOM_TYPES = new QueryDslCustomTypesManager();

    default Class<UserInfraEntity> getEntityType() {
        return UserInfraEntity.class;
    }

    default QueryDslCustomTypesManager getCustomTypesManager() {
        return CUSTOM_TYPES;
    }

    // Custom queries
    UserInfraEntity findByUsername(String username);

}
