package es.dtr.job.interview.inditex.ms.application.rest.brand;

import es.dtr.job.interview.commons.hexagonal.application.rest.crud.CrudControllerMapper;
import es.dtr.job.interview.commons.hexagonal.domain.entity.type.EntityTypesMapper;
import es.dtr.job.interview.inditex.ms.application.rest.price.PriceControllerMapper;
import es.dtr.job.interview.inditex.ms.domain.entity.BrandEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {PriceControllerMapper.class, EntityTypesMapper.class})
public interface BrandControllerMapper extends CrudControllerMapper<BrandDto, BrandEntity> {

}
