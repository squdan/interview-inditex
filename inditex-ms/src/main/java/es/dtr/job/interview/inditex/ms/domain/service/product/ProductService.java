package es.dtr.job.interview.inditex.ms.domain.service.product;

import es.dtr.job.interview.commons.hexagonal.domain.service.crud.CrudService;
import es.dtr.job.interview.inditex.ms.domain.entity.ProductEntity;
import es.dtr.job.interview.inditex.ms.domain.repository.ProductDomainRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductService implements CrudService<ProductEntity, Long> {

    // Dependencies
    private final ProductDomainRepository repository;

}
