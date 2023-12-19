package es.dtr.job.interview.inditex.ms.infrastructure.database.brand;

import es.dtr.job.interview.commons.hexagonal.infrastructure.database.CrudPortRepository;
import es.dtr.job.interview.inditex.ms.domain.entity.BrandEntity;
import es.dtr.job.interview.inditex.ms.domain.repository.BrandDomainRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Getter
@Component
@RequiredArgsConstructor
public class BrandAdapterRepository implements BrandDomainRepository, CrudPortRepository<BrandEntity, BrandInfraEntity, Long> {

    // Dependencies
    private final BrandInfraRepository crudInfraRepository;
    private final BrandRepositoryMapper repositoryMapper;

}
