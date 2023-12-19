package es.dtr.job.interview.inditex.ms.domain.service.brand;

import es.dtr.job.interview.commons.TestData;
import es.dtr.job.interview.commons.test.service.CrudServiceTest;
import es.dtr.job.interview.inditex.ms.domain.entity.BrandEntity;
import es.dtr.job.interview.inditex.ms.domain.repository.BrandDomainRepository;
import lombok.Getter;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@Getter
@ExtendWith(MockitoExtension.class)
class BrandServiceTest implements CrudServiceTest<BrandEntity, Long> {

    // Class to test
    @InjectMocks
    private BrandService crudService;

    // Mocks
    @Mock
    private BrandDomainRepository crudRepository;

    @Override
    public List<BrandEntity> getElementsEntity() {
        return List.of(
                BrandEntity.builder().id(1L).name(TestData.TEST_STRING).createdOn(TestData.NOW).lastUpdatedOn(TestData.NOW).build(),
                BrandEntity.builder().id(2L).name(TestData.TEST_STRING).createdOn(TestData.NOW).lastUpdatedOn(TestData.NOW).build()
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

}
