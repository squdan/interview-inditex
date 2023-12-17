package es.dtr.job.interview.inditex.ms.domain.service.brand;

import es.dtr.job.interview.commons.TestData;
import es.dtr.job.interview.commons.api.querydsl.QueryDslUtils;
import es.dtr.job.interview.commons.data.CrudRepository;
import es.dtr.job.interview.commons.service.crud.CrudMapper;
import es.dtr.job.interview.commons.service.crud.CrudService;
import es.dtr.job.interview.commons.test.service.CrudServiceTest;
import es.dtr.job.interview.inditex.ms.adapter.out.database.hibernate.BrandRepository;
import es.dtr.job.interview.inditex.ms.domain.entity.BrandEntity;
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
class BrandServiceTest implements CrudServiceTest<BrandDto, BrandEntity, Long> {

    // Class to test
    @InjectMocks
    private BrandService service;

    // Mocks
    @Mock
    private BrandRepository repository;

    @Mock
    private BrandMapper mapper;

    @Mock
    private QueryDslUtils queryDslUtils;

    @Test
    void test_findBy_whenNoData_thenEmptyResponse() {
        // Mocks
        Mockito.when(queryDslUtils.prepareFilters(Mockito.anyList())).thenReturn(new ArrayList<>());
        Mockito.when(getCrudRepository().findAll(Mockito.any(), Mockito.anyList(), Mockito.any())).thenReturn(new ArrayList<>());

        // Test execution
        final List<BrandDto> elements = getCrudService().findBy(new ArrayList<>());

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
        final List<BrandDto> elements = getCrudService().findBy(new ArrayList<>());

        // Response validation
        Assertions.assertTrue(CollectionUtils.isNotEmpty(elements));
        Assertions.assertArrayEquals(getElementsDto().toArray(), elements.toArray());
    }

    @Override
    public CrudService<BrandDto, BrandEntity, Long> getCrudService() {
        return this.service;
    }

    @Override
    public CrudRepository<BrandEntity, Long> getCrudRepository() {
        return this.repository;
    }

    @Override
    public CrudMapper<BrandEntity, BrandDto> getCrudMapper() {
        return this.mapper;
    }

    @Override
    public List<BrandEntity> getElementsEntity() {
        return List.of(
                BrandEntity.builder().id(1L).name(TestData.TEST_STRING).createdOn(TestData.NOW).lastUpdatedOn(TestData.NOW).build(),
                BrandEntity.builder().id(2L).name(TestData.TEST_STRING).createdOn(TestData.NOW).lastUpdatedOn(TestData.NOW).build()
        );
    }

    @Override
    public List<BrandDto> getElementsDto() {
        return List.of(
                BrandDto.builder().id(1L).name(TestData.TEST_STRING).createdOn(TestData.NOW).lastUpdatedOn(TestData.NOW).build(),
                BrandDto.builder().id(2L).name(TestData.TEST_STRING).createdOn(TestData.NOW).lastUpdatedOn(TestData.NOW).build()
        );
    }

    @Override
    public Long getRequestId() {
        return 1L;
    }

    @Override
    public BrandEntity getElementEntity() {
        return BrandEntity.builder().id(getRequestId()).name(TestData.TEST_STRING).createdOn(TestData.NOW).lastUpdatedOn(TestData.NOW).build();
    }

    @Override
    public BrandDto getElementDto() {
        final BrandEntity entity = getElementEntity();
        return BrandDto.builder().id(entity.getId()).name(entity.getName()).createdOn(entity.getCreatedOn()).lastUpdatedOn(entity.getLastUpdatedOn()).build();
    }

}
