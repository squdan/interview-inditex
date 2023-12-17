package es.dtr.job.interview.inditex.ms.domain.service.product;

import es.dtr.job.interview.commons.data.type.EntityTypesMapper;
import es.dtr.job.interview.commons.service.crud.CrudMapper;
import es.dtr.job.interview.inditex.ms.domain.entity.ProductEntity;
import es.dtr.job.interview.inditex.ms.domain.service.price.PriceMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {PriceMapper.class, EntityTypesMapper.class})
public interface ProductMapper extends CrudMapper<ProductEntity, ProductDto> {

}
