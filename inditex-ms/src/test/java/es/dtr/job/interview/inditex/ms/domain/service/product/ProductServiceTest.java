package es.dtr.job.interview.inditex.ms.domain.service.product;

import es.dtr.job.interview.commons.TestData;
import es.dtr.job.interview.commons.test.service.CrudServiceTest;
import es.dtr.job.interview.inditex.ms.domain.entity.ProductEntity;
import es.dtr.job.interview.inditex.ms.domain.repository.ProductDomainRepository;
import lombok.Getter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@Getter
@ExtendWith(MockitoExtension.class)
class ProductServiceTest implements CrudServiceTest<ProductEntity, Long> {

    // Class to test
    @InjectMocks
    private ProductService crudService;

    // Mocks
    @Mock
    private ProductDomainRepository crudRepository;

    @Override
    public List<ProductEntity> getElementsEntity() {
        return List.of(
                ProductEntity.builder().id(1L).name(TestData.TEST_STRING).createdOn(TestData.NOW).lastUpdatedOn(TestData.NOW).build(),
                ProductEntity.builder().id(2L).name(TestData.TEST_STRING).createdOn(TestData.NOW).lastUpdatedOn(TestData.NOW).build()
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

}
