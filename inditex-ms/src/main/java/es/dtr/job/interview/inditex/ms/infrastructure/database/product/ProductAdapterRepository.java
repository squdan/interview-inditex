package es.dtr.job.interview.inditex.ms.infrastructure.database.product;

import es.dtr.job.interview.commons.hexagonal.infrastructure.database.CrudPortRepository;
import es.dtr.job.interview.inditex.ms.domain.entity.ProductEntity;
import es.dtr.job.interview.inditex.ms.domain.repository.ProductDomainRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Getter
@Component
@RequiredArgsConstructor
public class ProductAdapterRepository implements ProductDomainRepository, CrudPortRepository<ProductEntity, ProductInfraEntity, Long> {

    // Dependencies
    private final ProductInfraRepository crudInfraRepository;
    private final ProductRepositoryMapper repositoryMapper;

}
