package es.dtr.job.interview.inditex.ms.infrastructure.database.price;

import es.dtr.job.interview.commons.hexagonal.infrastructure.database.CrudPortRepository;
import es.dtr.job.interview.inditex.ms.domain.entity.PriceEntity;
import es.dtr.job.interview.inditex.ms.domain.repository.PriceDomainRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Getter
@Component
@RequiredArgsConstructor
public class PriceAdapterRepository implements PriceDomainRepository, CrudPortRepository<PriceEntity, PriceInfraEntity, Long> {

    // Dependencies
    private final PriceInfraRepository crudInfraRepository;
    private final PriceRepositoryMapper repositoryMapper;

}
