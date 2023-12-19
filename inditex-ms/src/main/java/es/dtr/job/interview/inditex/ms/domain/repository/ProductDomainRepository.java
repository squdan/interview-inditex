package es.dtr.job.interview.inditex.ms.domain.repository;

import es.dtr.job.interview.commons.hexagonal.domain.repository.CrudDomainRepository;
import es.dtr.job.interview.inditex.ms.domain.entity.ProductEntity;

public interface ProductDomainRepository extends CrudDomainRepository<ProductEntity, Long> {

}
