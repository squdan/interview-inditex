package es.dtr.job.interview.inditex.ms.domain.service.price;

import es.dtr.job.interview.commons.TestData;
import es.dtr.job.interview.commons.api.querydsl.QueryDslUtils;
import es.dtr.job.interview.commons.data.CrudRepository;
import es.dtr.job.interview.commons.data.Currencies;
import es.dtr.job.interview.commons.error.GenericException;
import es.dtr.job.interview.commons.service.crud.CrudMapper;
import es.dtr.job.interview.commons.service.crud.CrudService;
import es.dtr.job.interview.commons.test.service.CrudServiceTest;
import es.dtr.job.interview.inditex.ms.adapter.out.database.hibernate.PriceRepository;
import es.dtr.job.interview.inditex.ms.domain.entity.BrandEntity;
import es.dtr.job.interview.inditex.ms.domain.entity.PriceEntity;
import es.dtr.job.interview.inditex.ms.domain.entity.ProductEntity;
import es.dtr.job.interview.inditex.ms.domain.service.brand.BrandDto;
import es.dtr.job.interview.inditex.ms.domain.service.product.ProductDto;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ExtendWith(MockitoExtension.class)
class PriceServiceTest implements CrudServiceTest<PriceDto, PriceEntity, Long> {

    // Class to test
    @InjectMocks
    private PriceService service;

    // Mocks
    @Mock
    private PriceRepository repository;

    @Mock
    private PriceMapper mapper;

    @Mock
    private QueryDslUtils queryDslUtils;

    @Test
    void test_findBy_whenNoData_thenEmptyResponse() {
        // Mocks
        Mockito.when(queryDslUtils.prepareFilters(Mockito.anyList())).thenReturn(new ArrayList<>());
        Mockito.when(getCrudRepository().findAll(Mockito.any(), Mockito.anyList(), Mockito.any())).thenReturn(new ArrayList<>());

        // Test execution
        final List<PriceDto> elements = getCrudService().findBy(new ArrayList<>());

        // Response validation
        Assertions.assertTrue(CollectionUtils.isEmpty(elements));
    }

    @Test
    void test_findBy_whenData_thenReturnElements() {
        // Mocks
        Mockito.when(queryDslUtils.prepareFilters(Mockito.anyList())).thenReturn(new ArrayList<>());
        Mockito.when(getCrudRepository().findAll(Mockito.any(), Mockito.anyList(), Mockito.any())).thenReturn(getElementsEntity());
        Mockito.when(getCrudMapper().entityToDto(getElementsEntity())).thenReturn(getElementsDto());

        // Test execution
        final List<PriceDto> elements = getCrudService().findBy(new ArrayList<>());

        // Response validation
        Assertions.assertTrue(CollectionUtils.isNotEmpty(elements));
        Assertions.assertArrayEquals(getElementsDto().toArray(), elements.toArray());
    }

    @Test
    void test_getWith_whenNoData_thenThrowException() {
        // Test data
        final Long brandId = 1L;
        final Long productId = 35455L;

        // Mocks
        Mockito.when(queryDslUtils.prepareFilters(Mockito.anyList())).thenReturn(new ArrayList<>());
        Mockito.when(getCrudRepository().findAll(Mockito.any(), Mockito.anyList(), Mockito.any())).thenReturn(new ArrayList<>());

        // Test execution
        final GenericException thrown = Assertions.assertThrows(CrudService.NOT_FOUND.getClass(), () -> service.getWith(brandId, productId, TestData.NOW));

        // Response validation
        Assertions.assertTrue(Objects.nonNull(thrown));
        Assertions.assertEquals(CrudService.NOT_FOUND.getError().getHttpCode(), thrown.getError().getHttpCode());
        Assertions.assertEquals(CrudService.NOT_FOUND.getError().getMessage(), thrown.getError().getMessage());
    }

    @Test
    void test_getWith_whenData_thenReturnElements() {
        // Test data
        final Long brandId = 1L;
        final Long productId = 35455L;

        // Mocks
        Mockito.when(queryDslUtils.prepareFilters(Mockito.anyList())).thenReturn(new ArrayList<>());
        Mockito.when(getCrudRepository().findAll(Mockito.any(), Mockito.anyList(), Mockito.any())).thenReturn(getElementsEntity());
        Mockito.when(getCrudMapper().entityToDto(getElementsEntity())).thenReturn(getElementsDto());

        // Test execution
        final PriceDto elements = service.getWith(brandId, productId, TestData.NOW);

        // Response validation
        Assertions.assertTrue(Objects.nonNull(elements));
        Assertions.assertEquals(getElementsDto().get(0), elements);
    }

    @Override
    public CrudService<PriceDto, PriceEntity, Long> getCrudService() {
        return this.service;
    }

    @Override
    public CrudRepository<PriceEntity, Long> getCrudRepository() {
        return this.repository;
    }

    @Override
    public CrudMapper<PriceEntity, PriceDto> getCrudMapper() {
        return this.mapper;
    }

    @Override
    public List<PriceEntity> getElementsEntity() {
        return List.of(
                PriceEntity.builder()
                        .priceList(1L)
                        .brand(BrandEntity.builder().build())
                        .product(ProductEntity.builder().build())
                        .startDate(TestData.NOW)
                        .endDate(TestData.NOW)
                        .priority(NumberUtils.INTEGER_ONE)
                        .price(BigDecimal.ONE)
                        .currency(Currencies.EUR)
                        .createdOn(TestData.NOW)
                        .lastUpdatedOn(TestData.NOW)
                        .build(),
                PriceEntity.builder()
                        .priceList(2L)
                        .brand(BrandEntity.builder().build())
                        .product(ProductEntity.builder().build())
                        .startDate(TestData.NOW)
                        .endDate(TestData.NOW)
                        .priority(NumberUtils.INTEGER_TWO)
                        .price(BigDecimal.TEN)
                        .currency(Currencies.EUR)
                        .createdOn(TestData.NOW)
                        .lastUpdatedOn(TestData.NOW)
                        .build()
        );
    }

    @Override
    public List<PriceDto> getElementsDto() {
        return List.of(
                PriceDto.builder()
                        .priceList(1L)
                        .brand(BrandDto.builder().build())
                        .product(ProductDto.builder().build())
                        .startDate(TestData.NOW)
                        .endDate(TestData.NOW)
                        .priority(NumberUtils.INTEGER_ONE)
                        .price(BigDecimal.ONE)
                        .currency(Currencies.EUR)
                        .createdOn(TestData.NOW)
                        .lastUpdatedOn(TestData.NOW)
                        .build(),
                PriceDto.builder()
                        .priceList(2L)
                        .brand(BrandDto.builder().build())
                        .product(ProductDto.builder().build())
                        .startDate(TestData.NOW)
                        .endDate(TestData.NOW)
                        .priority(NumberUtils.INTEGER_TWO)
                        .price(BigDecimal.TEN)
                        .currency(Currencies.EUR)
                        .createdOn(TestData.NOW)
                        .lastUpdatedOn(TestData.NOW)
                        .build()
        );
    }

    @Override
    public Long getRequestId() {
        return 1L;
    }

    @Override
    public PriceEntity getElementEntity() {
        return PriceEntity.builder()
                .priceList(getRequestId())
                .brand(BrandEntity.builder().build())
                .product(ProductEntity.builder().build())
                .startDate(TestData.NOW)
                .endDate(TestData.NOW)
                .priority(NumberUtils.INTEGER_ONE)
                .price(BigDecimal.ONE)
                .currency(Currencies.EUR)
                .createdOn(TestData.NOW)
                .lastUpdatedOn(TestData.NOW)
                .build();
    }

    @Override
    public PriceDto getElementDto() {
        final PriceEntity entity = getElementEntity();
        return PriceDto.builder()
                .priceList(entity.getPriceList())
                .brand(BrandDto.builder().build())
                .product(ProductDto.builder().build())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .priority(entity.getPriority())
                .price(entity.getPrice())
                .currency(entity.getCurrency())
                .createdOn(entity.getCreatedOn())
                .lastUpdatedOn(entity.getLastUpdatedOn())
                .build();
    }
}
