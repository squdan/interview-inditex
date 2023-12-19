package es.dtr.job.interview.inditex.ms.domain.service.brand;

import es.dtr.job.interview.commons.hexagonal.domain.service.crud.CrudService;
import es.dtr.job.interview.inditex.ms.domain.entity.BrandEntity;
import es.dtr.job.interview.inditex.ms.domain.repository.BrandDomainRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BrandService implements CrudService<BrandEntity, Long> {

    // Dependencies
    private final BrandDomainRepository repository;
}
