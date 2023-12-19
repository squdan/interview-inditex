package es.dtr.job.interview.inditex.ms.infrastructure.database.price;

import es.dtr.job.interview.commons.hexagonal.domain.repository.CrudInfraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceInfraRepository extends CrudInfraRepository<PriceInfraEntity, Long> {

    // Configuration
    default Class<PriceInfraEntity> getEntityType() {
        return PriceInfraEntity.class;
    }

}
