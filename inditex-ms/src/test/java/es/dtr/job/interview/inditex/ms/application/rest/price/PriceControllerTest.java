package es.dtr.job.interview.inditex.ms.application.rest.price;

import es.dtr.job.interview.commons.TestData;
import es.dtr.job.interview.commons.hexagonal.domain.entity.type.Currencies;
import es.dtr.job.interview.commons.hexagonal.domain.service.crud.CrudService;
import es.dtr.job.interview.commons.test.controller.CrudControllerTest;
import es.dtr.job.interview.inditex.ms.application.rest.ControllerBaseTest;
import es.dtr.job.interview.inditex.ms.application.rest.brand.BrandDto;
import es.dtr.job.interview.inditex.ms.application.rest.product.ProductDto;
import es.dtr.job.interview.inditex.ms.domain.entity.BrandEntity;
import es.dtr.job.interview.inditex.ms.domain.entity.PriceEntity;
import es.dtr.job.interview.inditex.ms.domain.entity.ProductEntity;
import es.dtr.job.interview.inditex.ms.domain.service.price.PriceService;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Getter
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PriceController.class)
@AutoConfigureMockMvc(addFilters = false)
class PriceControllerTest extends ControllerBaseTest implements CrudControllerTest<PriceDto, PriceEntity, Long> {

    // Configuration
    private final String basePath = PriceControllerInterface.BASE_PATH;

    // Mocks
    @MockBean
    private PriceService crudService;

    @MockBean
    private PriceControllerMapper crudMapper;

    @Test
    void test_getWith_whenNoExists_then404() throws Exception {
        // Test data
        final Long brandId = 1L;
        final Long productId = 35455L;
        final Instant date = TestData.NOW;

        // Mocks
        Mockito
                .when(crudService.getWith(brandId, productId, date))
                .thenThrow(CrudService.NOT_FOUND);

        // Test execution
        final ResultActions restResponse = mockMvc.perform(
                MockMvcRequestBuilders
                        .get(getBasePath() + PriceControllerInterface.ENDPOINT_GET_PRICE_BY, brandId, productId, date)
        );

        // Response validation
        restResponse
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpCode", Matchers.equalTo(HttpStatus.NOT_FOUND.name())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.equalTo(CrudService.NOT_FOUND.getError().getMessage())));
    }

    @Test
    void test_getWith_whenExists_thenReturnElement() throws Exception {
        // Test data
        final Long brandId = 1L;
        final Long productId = 35455L;
        final Instant date = TestData.NOW;

        // Mocks
        Mockito
                .when(crudService.getWith(brandId, productId, date))
                .thenReturn(this.getElementEntity());
        Mockito
                .when(crudMapper.domainEntityToDto(this.getElementEntity()))
                .thenReturn(this.getElementDto());

        // Test execution
        final ResultActions restResponse = mockMvc.perform(
                MockMvcRequestBuilders
                        .get(getBasePath() + PriceControllerInterface.ENDPOINT_GET_PRICE_BY, brandId, productId, date)
        );

        // Response validation
        restResponse
                .andExpect(MockMvcResultMatchers.status().isOk());

        if (CollectionUtils.isNotEmpty(getElementTestMatchers())) {
            for (ResultMatcher matcher : getElementTestMatchers()) {
                restResponse.andExpect(matcher);
            }
        }
    }

    @Override
    public Long getRequestId() {
        return 1L;
    }

    @Override
    public PriceDto getElementDto() {
        return PriceDto.builder()
                .priceList(getRequestId())
                .brand(BrandDto.builder().build())
                .product(ProductDto.builder().build())
                .startDate(TestData.NOW)
                .endDate(TestData.NOW)
                .priority(1)
                .price(BigDecimal.ONE)
                .currency(Currencies.EUR)
                .createdOn(TestData.NOW)
                .lastUpdatedOn(TestData.NOW)
                .build();
    }

    @Override
    public PriceEntity getElementEntity() {
        return PriceEntity.builder()
                .priceList(getRequestId())
                .brand(BrandEntity.builder().build())
                .product(ProductEntity.builder().build())
                .startDate(TestData.NOW)
                .endDate(TestData.NOW)
                .priority(1)
                .price(BigDecimal.ONE)
                .currency(Currencies.EUR)
                .createdOn(TestData.NOW)
                .lastUpdatedOn(TestData.NOW)
                .build();
    }

    @Override
    public List<PriceDto> getElementsDto() {
        return List.of(
                PriceDto.builder()
                        .priceList(getRequestId())
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
    public List<PriceEntity> getElementsEntity() {
        return List.of(
                PriceEntity.builder()
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
    public List<ResultMatcher> getElementsTestMatchers() {
        return List.of(
                MockMvcResultMatchers.jsonPath("$[0].priceList", Matchers.comparesEqualTo(getElementsDto().get(0).getPriceList().intValue())),
                MockMvcResultMatchers.jsonPath("$[0].priority", Matchers.equalTo(getElementsDto().get(0).getPriority())),
                MockMvcResultMatchers.jsonPath("$[1].priceList", Matchers.comparesEqualTo(getElementsDto().get(1).getPriceList().intValue())),
                MockMvcResultMatchers.jsonPath("$[1].priority", Matchers.equalTo(getElementsDto().get(1).getPriority()))
        );
    }

    @Override
    public List<ResultMatcher> getElementTestMatchers() {
        return List.of(
                MockMvcResultMatchers.jsonPath("$.priceList", Matchers.comparesEqualTo(this.getElementDto().getPriceList().intValue())),
                MockMvcResultMatchers.jsonPath("$.priority", Matchers.equalTo(this.getElementDto().getPriority()))
        );
    }
}
