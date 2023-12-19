package es.dtr.job.interview.inditex.ms.infrastructure.database.brand;

import es.dtr.job.interview.commons.hexagonal.domain.repository.CrudInfraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandInfraRepository extends CrudInfraRepository<BrandInfraEntity, Long> {

    // Configuration
    default Class<BrandInfraEntity> getEntityType() {
        return BrandInfraEntity.class;
    }

}
