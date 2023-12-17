package es.dtr.job.interview.inditex.ms.domain.service.product;

import es.dtr.job.interview.commons.TestData;
import es.dtr.job.interview.commons.api.querydsl.QueryDslUtils;
import es.dtr.job.interview.commons.data.CrudRepository;
import es.dtr.job.interview.commons.service.crud.CrudMapper;
import es.dtr.job.interview.commons.service.crud.CrudService;
import es.dtr.job.interview.commons.test.service.CrudServiceTest;
import es.dtr.job.interview.inditex.ms.adapter.out.database.hibernate.ProductRepository;
import es.dtr.job.interview.inditex.ms.domain.entity.ProductEntity;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest implements CrudServiceTest<ProductDto, ProductEntity, Long> {

    // Class to test
    @InjectMocks
    private ProductService service;

    // Mocks
    @Mock
    private ProductRepository repository;

    @Mock
    private ProductMapper mapper;

    @Mock
    private QueryDslUtils queryDslUtils;

    @Test
    void test_findBy_whenNoData_thenEmptyResponse() {
        // Mocks
        Mockito.when(queryDslUtils.prepareFilters(Mockito.anyList())).thenReturn(new ArrayList<>());
        Mockito.when(getCrudRepository().findAll(Mockito.any(), Mockito.anyList(), Mockito.any())).thenReturn(new ArrayList<>());

        // Test execution
        final List<ProductDto> elements = getCrudService().findBy(new ArrayList<>());

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
        final List<ProductDto> elements = getCrudService().findBy(new ArrayList<>());

        // Response validation
        Assertions.assertTrue(CollectionUtils.isNotEmpty(elements));
        Assertions.assertArrayEquals(getElementsDto().toArray(), elements.toArray());
    }

    @Override
    public CrudService<ProductDto, ProductEntity, Long> getCrudService() {
        return this.service;
    }

    @Override
    public CrudRepository<ProductEntity, Long> getCrudRepository() {
        return this.repository;
    }

    @Override
    public CrudMapper<ProductEntity, ProductDto> getCrudMapper() {
        return this.mapper;
    }

    @Override
    public List<ProductEntity> getElementsEntity() {
        return List.of(
                ProductEntity.builder().id(1L).name(TestData.TEST_STRING).createdOn(TestData.NOW).lastUpdatedOn(TestData.NOW).build(),
                ProductEntity.builder().id(2L).name(TestData.TEST_STRING).createdOn(TestData.NOW).lastUpdatedOn(TestData.NOW).build()
        );
    }

    @Override
    public List<ProductDto> getElementsDto() {
        return List.of(
                ProductDto.builder().id(1L).name(TestData.TEST_STRING).createdOn(TestData.NOW).lastUpdatedOn(TestData.NOW).build(),
                ProductDto.builder().id(2L).name(TestData.TEST_STRING).createdOn(TestData.NOW).lastUpdatedOn(TestData.NOW).build()
        );
    }

    @Override
    public Long getRequestId() {
        return 1L;
    }

    @Override
    public ProductEntity getElementEntity() {
        return ProductEntity.builder()
                .id(getRequestId())
                .name(TestData.TEST_STRING)
                .createdOn(TestData.NOW)
                .lastUpdatedOn(TestData.NOW)
                .build();
    }

    @Override
    public ProductDto getElementDto() {
        final ProductEntity entity = getElementEntity();
        return ProductDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .createdOn(entity.getCreatedOn())
                .lastUpdatedOn(entity.getLastUpdatedOn())
                .build();
    }

}
