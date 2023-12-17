package es.dtr.job.interview.inditex.ms.domain.service.price;

import es.dtr.job.interview.commons.data.type.EntityTypesMapper;
import es.dtr.job.interview.commons.service.crud.CrudMapper;
import es.dtr.job.interview.inditex.ms.domain.entity.BrandEntity;
import es.dtr.job.interview.inditex.ms.domain.entity.PriceEntity;
import es.dtr.job.interview.inditex.ms.domain.entity.ProductEntity;
import es.dtr.job.interview.inditex.ms.domain.service.brand.BrandDto;
import es.dtr.job.interview.inditex.ms.domain.service.product.ProductDto;
import jakarta.validation.constraints.NotNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {EntityTypesMapper.class})
public interface PriceMapper extends CrudMapper<PriceEntity, PriceDto> {

    // Custom mappings
    String MAPPING_BRAND_WITHOUTOUT_JOINS = "MAPPING_BRAND_WITHOUTOUT_JOINS";
    String MAPPING_PRODUCT_WITHOUTOUT_JOINS = "MAPPING_PRODUCT_WITHOUTOUT_JOINS";

    @Mapping(target = "brand", source = "brand", qualifiedByName = MAPPING_BRAND_WITHOUTOUT_JOINS)
    @Mapping(target = "product", source = "product", qualifiedByName = MAPPING_PRODUCT_WITHOUTOUT_JOINS)
    PriceDto entityToDto(PriceEntity source);

    @Named(MAPPING_BRAND_WITHOUTOUT_JOINS)
    @Mapping(target = "prices", ignore = true)
    BrandDto mapBrandEntityWithoutJoins(@NotNull final BrandEntity source);

    @Named(MAPPING_PRODUCT_WITHOUTOUT_JOINS)
    @Mapping(target = "prices", ignore = true)
    ProductDto mapProductEntityWithoutJoins(@NotNull final ProductEntity source);
}
