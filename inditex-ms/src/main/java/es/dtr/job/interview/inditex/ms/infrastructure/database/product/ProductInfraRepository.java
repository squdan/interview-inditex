package es.dtr.job.interview.inditex.ms.infrastructure.database.product;

import es.dtr.job.interview.commons.hexagonal.domain.repository.CrudInfraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductInfraRepository extends CrudInfraRepository<ProductInfraEntity, Long> {

    // Configuration
    default Class<ProductInfraEntity> getEntityType() {
        return ProductInfraEntity.class;
    }

}
