package es.dtr.job.interview.inditex.ms.infrastructure.database.product;

import es.dtr.job.interview.commons.hexagonal.domain.entity.type.EntityTypesMapper;
import es.dtr.job.interview.commons.hexagonal.infrastructure.database.CrudRepositoryMapper;
import es.dtr.job.interview.inditex.ms.domain.entity.ProductEntity;
import es.dtr.job.interview.inditex.ms.infrastructure.database.price.PriceRepositoryMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {PriceRepositoryMapper.class, EntityTypesMapper.class})
public interface ProductRepositoryMapper extends CrudRepositoryMapper<ProductEntity, ProductInfraEntity> {

}
