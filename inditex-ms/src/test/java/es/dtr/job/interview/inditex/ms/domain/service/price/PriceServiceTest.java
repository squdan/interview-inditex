package es.dtr.job.interview.inditex.ms.domain.service.price;

import es.dtr.job.interview.commons.TestData;
import es.dtr.job.interview.commons.hexagonal.domain.entity.type.Currencies;
import es.dtr.job.interview.commons.test.service.CrudServiceTest;
import es.dtr.job.interview.inditex.ms.domain.entity.BrandEntity;
import es.dtr.job.interview.inditex.ms.domain.entity.PriceEntity;
import es.dtr.job.interview.inditex.ms.domain.entity.ProductEntity;
import es.dtr.job.interview.inditex.ms.domain.repository.PriceDomainRepository;
import lombok.Getter;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

@Getter
@ExtendWith(MockitoExtension.class)
class PriceServiceTest implements CrudServiceTest<PriceEntity, Long> {

    // Class to test
    @InjectMocks
    private PriceService crudService;

    // Mocks
    @Mock
    private PriceDomainRepository crudRepository;

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
}
