package es.dtr.job.interview.inditex.ms.infrastructure.database.price;

import es.dtr.job.interview.commons.hexagonal.domain.entity.type.EntityTypesMapper;
import es.dtr.job.interview.commons.hexagonal.infrastructure.database.CrudRepositoryMapper;
import es.dtr.job.interview.inditex.ms.domain.entity.BrandEntity;
import es.dtr.job.interview.inditex.ms.domain.entity.PriceEntity;
import es.dtr.job.interview.inditex.ms.domain.entity.ProductEntity;
import es.dtr.job.interview.inditex.ms.infrastructure.database.brand.BrandInfraEntity;
import es.dtr.job.interview.inditex.ms.infrastructure.database.product.ProductInfraEntity;
import jakarta.validation.constraints.NotNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {PriceRepositoryMapper.class, EntityTypesMapper.class})
public interface PriceRepositoryMapper extends CrudRepositoryMapper<PriceEntity, PriceInfraEntity> {

    // Custom mappings
    String MAPPING_BRAND_INFRA_WITHOUTOUT_JOINS = "MAPPING_BRAND_INFRA_WITHOUTOUT_JOINS";
    String MAPPING_PRODUCT_INFRA_WITHOUTOUT_JOINS = "MAPPING_PRODUCT_INFRA_WITHOUTOUT_JOINS";
    String MAPPING_BRAND_DOMAIN_WITHOUTOUT_JOINS = "MAPPING_BRAND_DOMAIN_WITHOUTOUT_JOINS";
    String MAPPING_PRODUCT_DOMAIN_WITHOUTOUT_JOINS = "MAPPING_PRODUCT_DOMAIN_WITHOUTOUT_JOINS";

    @Mapping(target = "brand", source = "brand", qualifiedByName = MAPPING_BRAND_INFRA_WITHOUTOUT_JOINS)
    @Mapping(target = "product", source = "product", qualifiedByName = MAPPING_PRODUCT_INFRA_WITHOUTOUT_JOINS)
    PriceEntity entityInfraToEntityDomain(PriceInfraEntity source);

    @Named(MAPPING_BRAND_INFRA_WITHOUTOUT_JOINS)
    @Mapping(target = "prices", ignore = true)
    BrandEntity brandInfraToBrandDomain(@NotNull final BrandInfraEntity source);

    @Named(MAPPING_PRODUCT_INFRA_WITHOUTOUT_JOINS)
    @Mapping(target = "prices", ignore = true)
    ProductEntity productInfraToProductDomain(@NotNull final ProductInfraEntity source);

    @Mapping(target = "brand", source = "brand", qualifiedByName = MAPPING_BRAND_DOMAIN_WITHOUTOUT_JOINS)
    @Mapping(target = "product", source = "product", qualifiedByName = MAPPING_PRODUCT_DOMAIN_WITHOUTOUT_JOINS)
    PriceInfraEntity entityDomainToEntityInfra(PriceEntity source);

    @Named(MAPPING_BRAND_DOMAIN_WITHOUTOUT_JOINS)
    @Mapping(target = "prices", ignore = true)
    BrandInfraEntity brandDomainToBrandInfra(@NotNull final BrandEntity source);

    @Named(MAPPING_PRODUCT_DOMAIN_WITHOUTOUT_JOINS)
    @Mapping(target = "prices", ignore = true)
    ProductInfraEntity productDomainToProductInfra(@NotNull final ProductEntity source);

}
