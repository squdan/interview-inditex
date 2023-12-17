package es.dtr.job.interview.inditex.ms.adapter.in.price;

import es.dtr.job.interview.commons.TestData;
import es.dtr.job.interview.commons.data.Currencies;
import es.dtr.job.interview.commons.it.controller.CrudControllerIT;
import es.dtr.job.interview.commons.util.DateTimeUtils;
import es.dtr.job.interview.inditex.ms.TestDataIT;
import es.dtr.job.interview.inditex.ms.adapter.in.ControllerBaseIT;
import es.dtr.job.interview.inditex.ms.domain.entity.PriceEntity;
import es.dtr.job.interview.inditex.ms.domain.service.brand.BrandDto;
import es.dtr.job.interview.inditex.ms.domain.service.price.PriceDto;
import es.dtr.job.interview.inditex.ms.domain.service.product.ProductDto;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class PriceControllerIT extends ControllerBaseIT implements CrudControllerIT<PriceDto, PriceEntity, Long> {

    @Test
    void test_getWith_withDay14_andTime10_thenReturnPrice1() throws Exception {
        // Test data
        final Long brandId = 1L;
        final Long productId = 35455L;
        final Instant date = DateTimeUtils.StringDateUtils.toInstantUtc("2020-06-14T10:00:00Z");

        // Test execution
        final ResultActions restResponse = getMockMvc().perform(
                MockMvcRequestBuilders
                        .get(
                                getBasePath() + PriceControllerInterface.ENDPOINT_GET_PRICE_BY,
                                productId, brandId, date
                        )
        );

        // Response validation
        restResponse
                .andExpect(MockMvcResultMatchers.status().isOk());

        validatePriceResponse(restResponse, TestDataIT.Prices.PRICE_1);
    }

    @Test
    void test_getWith_withDay14_andTime16_thenReturnPrice2() throws Exception {
        // Test data
        final Long brandId = 1L;
        final Long productId = 35455L;
        final Instant date = DateTimeUtils.StringDateUtils.toInstantUtc("2020-06-14T16:00:00Z");

        // Test execution
        final ResultActions restResponse = getMockMvc().perform(
                MockMvcRequestBuilders
                        .get(
                                getBasePath() + PriceControllerInterface.ENDPOINT_GET_PRICE_BY,
                                productId, brandId, date
                        )
        );

        // Response validation
        restResponse
                .andExpect(MockMvcResultMatchers.status().isOk());

        validatePriceResponse(restResponse, TestDataIT.Prices.PRICE_2);
    }

    @Test
    void test_getWith_withDay14_andTime21_thenReturnPrice1() throws Exception {
        // Test data
        final Long brandId = 1L;
        final Long productId = 35455L;
        final Instant date = DateTimeUtils.StringDateUtils.toInstantUtc("2020-06-14T21:00:00Z");

        // Test execution
        final ResultActions restResponse = getMockMvc().perform(
                MockMvcRequestBuilders
                        .get(
                                getBasePath() + PriceControllerInterface.ENDPOINT_GET_PRICE_BY,
                                productId, brandId, date
                        )
        );

        // Response validation
        restResponse
                .andExpect(MockMvcResultMatchers.status().isOk());

        validatePriceResponse(restResponse, TestDataIT.Prices.PRICE_1);
    }

    @Test
    void test_getWith_withDay15_andTime10_thenReturnPrice3() throws Exception {
        // Test data
        final Long brandId = 1L;
        final Long productId = 35455L;
        final Instant date = DateTimeUtils.StringDateUtils.toInstantUtc("2020-06-15T10:00:00Z");

        // Test execution
        final ResultActions restResponse = getMockMvc().perform(
                MockMvcRequestBuilders
                        .get(
                                getBasePath() + PriceControllerInterface.ENDPOINT_GET_PRICE_BY,
                                productId, brandId, date
                        )
        );

        // Response validation
        restResponse
                .andExpect(MockMvcResultMatchers.status().isOk());

        validatePriceResponse(restResponse, TestDataIT.Prices.PRICE_3);
    }

    @Test
    void test_getWith_withDay16_andTime21_thenReturnPrice4() throws Exception {
        // Test data
        final Long brandId = 1L;
        final Long productId = 35455L;
        final Instant date = DateTimeUtils.StringDateUtils.toInstantUtc("2020-06-16T21:00:00Z");

        // Test execution
        final ResultActions restResponse = getMockMvc().perform(
                MockMvcRequestBuilders
                        .get(
                                getBasePath() + PriceControllerInterface.ENDPOINT_GET_PRICE_BY,
                                productId, brandId, date
                        )
        );

        // Response validation
        restResponse
                .andExpect(MockMvcResultMatchers.status().isOk());

        validatePriceResponse(restResponse, TestDataIT.Prices.PRICE_4);
    }

    private void validatePriceResponse(final ResultActions restResponse, final PriceDto expectedPrice) throws Exception {
        restResponse
                .andExpect(MockMvcResultMatchers.jsonPath("$.priceList", Matchers.comparesEqualTo(expectedPrice.getPriceList().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.brand.id", Matchers.comparesEqualTo(expectedPrice.getBrand().getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.product.id", Matchers.comparesEqualTo(expectedPrice.getProduct().getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startDate", Matchers.equalTo(getExpectedDate(expectedPrice.getStartDate()))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endDate", Matchers.equalTo(getExpectedDate(expectedPrice.getEndDate()))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.priority", Matchers.equalTo(expectedPrice.getPriority())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price", Matchers.comparesEqualTo(expectedPrice.getPrice().doubleValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currency", Matchers.comparesEqualTo(expectedPrice.getCurrency().name())));
    }

    private String getExpectedDate(final Instant date) {
        return DateTimeUtils.InstantUtils.toString(date, ZoneOffset.UTC);
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
    public Long getRequestIdNotExists() {
        return 100L;
    }

    @Override
    public PriceDto getElementCreate() {
        return PriceDto.builder()
                .brand(BrandDto.builder().id(1L).build())
                .product(ProductDto.builder().id(35455L).build())
                .startDate(TestData.NOW)
                .endDate(TestData.NOW.plus(1, ChronoUnit.DAYS))
                .priority(100)
                .price(new BigDecimal("50.01"))
                .currency(Currencies.EUR)
                .build();
    }

    @Override
    public PriceDto getElementUpdate() {
        return PriceDto.builder()
                .brand(BrandDto.builder().id(1L).build())
                .product(ProductDto.builder().id(35455L).build())
                .startDate(TestData.NOW)
                .endDate(TestData.NOW.plus(1, ChronoUnit.DAYS))
                .priority(100)
                .price(new BigDecimal("50.01"))
                .currency(Currencies.EUR)
                .build();
    }

    @Override
    public List<ResultMatcher> getAllTestMatchers() {
        return List.of(
                MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(4)),
                MockMvcResultMatchers.jsonPath("$[0].priceList", Matchers.comparesEqualTo(1)),
                MockMvcResultMatchers.jsonPath("$[0].brand.id", Matchers.comparesEqualTo(1)),
                MockMvcResultMatchers.jsonPath("$[0].brand.name", Matchers.equalTo("ZARA")),
                MockMvcResultMatchers.jsonPath("$[0].product.id", Matchers.comparesEqualTo(35455)),
                MockMvcResultMatchers.jsonPath("$[0].product.name", Matchers.equalTo("Camiseta manga corta Iron Maiden")),
                MockMvcResultMatchers.jsonPath("$[0].startDate", Matchers.equalTo("2020-06-14T00:00:00Z")),
                MockMvcResultMatchers.jsonPath("$[0].endDate", Matchers.equalTo("2020-12-31T23:59:59Z")),
                MockMvcResultMatchers.jsonPath("$[0].priority", Matchers.equalTo(0)),
                MockMvcResultMatchers.jsonPath("$[0].price", Matchers.comparesEqualTo(Double.valueOf("35.50"))),
                MockMvcResultMatchers.jsonPath("$[0].currency", Matchers.comparesEqualTo(Currencies.EUR.name()))
        );
    }

    @Override
    public List<ResultMatcher> getByIdTestMatchers() {
        return List.of(
                MockMvcResultMatchers.jsonPath("$.priceList", Matchers.comparesEqualTo(1)),
                MockMvcResultMatchers.jsonPath("$.brand.id", Matchers.comparesEqualTo(1)),
                MockMvcResultMatchers.jsonPath("$.brand.name", Matchers.equalTo("ZARA")),
                MockMvcResultMatchers.jsonPath("$.product.id", Matchers.comparesEqualTo(35455)),
                MockMvcResultMatchers.jsonPath("$.product.name", Matchers.equalTo("Camiseta manga corta Iron Maiden")),
                MockMvcResultMatchers.jsonPath("$.startDate", Matchers.equalTo("2020-06-14T00:00:00Z")),
                MockMvcResultMatchers.jsonPath("$.endDate", Matchers.equalTo("2020-12-31T23:59:59Z")),
                MockMvcResultMatchers.jsonPath("$.priority", Matchers.equalTo(0)),
                MockMvcResultMatchers.jsonPath("$.price", Matchers.comparesEqualTo(Double.valueOf("35.50"))),
                MockMvcResultMatchers.jsonPath("$.currency", Matchers.comparesEqualTo(Currencies.EUR.name()))
        );
    }

    @Override
    public String getFindByCorrectFiltersButNoResults() {
        return "priority=100000";
    }

    @Override
    public String getFindByCorrectFiltersWithResults() {
        return "price=35.50";
    }

    @Override
    public List<ResultMatcher> getFindByTestMatchers() {
        return List.of(
                MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)),
                MockMvcResultMatchers.jsonPath("$[0].priceList", Matchers.comparesEqualTo(1)),
                MockMvcResultMatchers.jsonPath("$[0].brand.id", Matchers.comparesEqualTo(1)),
                MockMvcResultMatchers.jsonPath("$[0].brand.name", Matchers.equalTo("ZARA")),
                MockMvcResultMatchers.jsonPath("$[0].product.id", Matchers.comparesEqualTo(35455)),
                MockMvcResultMatchers.jsonPath("$[0].product.name", Matchers.equalTo("Camiseta manga corta Iron Maiden")),
                MockMvcResultMatchers.jsonPath("$[0].startDate", Matchers.equalTo("2020-06-14T00:00:00Z")),
                MockMvcResultMatchers.jsonPath("$[0].endDate", Matchers.equalTo("2020-12-31T23:59:59Z")),
                MockMvcResultMatchers.jsonPath("$[0].priority", Matchers.equalTo(0)),
                MockMvcResultMatchers.jsonPath("$[0].price", Matchers.comparesEqualTo(Double.valueOf("35.50"))),
                MockMvcResultMatchers.jsonPath("$[0].currency", Matchers.comparesEqualTo(Currencies.EUR.name()))
        );
    }

}
