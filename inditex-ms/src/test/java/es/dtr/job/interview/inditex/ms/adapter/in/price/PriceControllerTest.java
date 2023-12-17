package es.dtr.job.interview.inditex.ms.adapter.in.price;

import es.dtr.job.interview.commons.TestData;
import es.dtr.job.interview.commons.data.Currencies;
import es.dtr.job.interview.commons.service.crud.CrudService;
import es.dtr.job.interview.commons.test.controller.CrudControllerTest;
import es.dtr.job.interview.inditex.ms.adapter.in.ControllerBaseTest;
import es.dtr.job.interview.inditex.ms.domain.entity.PriceEntity;
import es.dtr.job.interview.inditex.ms.domain.service.brand.BrandDto;
import es.dtr.job.interview.inditex.ms.domain.service.price.PriceDto;
import es.dtr.job.interview.inditex.ms.domain.service.price.PriceService;
import es.dtr.job.interview.inditex.ms.domain.service.product.ProductDto;
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

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PriceController.class)
@AutoConfigureMockMvc(addFilters = false)
class PriceControllerTest extends ControllerBaseTest implements CrudControllerTest<PriceDto, PriceEntity, Long> {

    // Mocks
    @MockBean
    private PriceService crudService;

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
                .thenReturn(getElement());

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

    // CrudControllerTest - Requirements
    @Override
    public CrudService<PriceDto, PriceEntity, Long> getCrudService() {
        return this.crudService;
    }

    @Override
    public String getBasePath() {
        return PriceControllerInterface.BASE_PATH;
    }

    @Override
    public Long getRequestId() {
        return 1L;
    }

    @Override
    public PriceDto getElement() {
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
    public List<PriceDto> getElements() {
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
    public List<ResultMatcher> getElementsTestMatchers() {
        return List.of(
                MockMvcResultMatchers.jsonPath("$[0].priceList", Matchers.comparesEqualTo(getElements().get(0).getPriceList().intValue())),
                MockMvcResultMatchers.jsonPath("$[0].priority", Matchers.equalTo(getElements().get(0).getPriority())),
                MockMvcResultMatchers.jsonPath("$[1].priceList", Matchers.comparesEqualTo(getElements().get(1).getPriceList().intValue())),
                MockMvcResultMatchers.jsonPath("$[1].priority", Matchers.equalTo(getElements().get(1).getPriority()))
        );
    }

    @Override
    public List<ResultMatcher> getElementTestMatchers() {
        return List.of(
                MockMvcResultMatchers.jsonPath("$.priceList", Matchers.comparesEqualTo(getElement().getPriceList().intValue())),
                MockMvcResultMatchers.jsonPath("$.priority", Matchers.equalTo(getElement().getPriority()))
        );
    }
}
