package es.dtr.job.interview.inditex.ms.adapter.in.product;

import es.dtr.job.interview.commons.TestData;
import es.dtr.job.interview.commons.api.querydsl.QueryDslFilter;
import es.dtr.job.interview.commons.api.querydsl.QueryDslOperators;
import es.dtr.job.interview.commons.it.controller.CrudControllerIT;
import es.dtr.job.interview.inditex.ms.adapter.in.ControllerBaseIT;
import es.dtr.job.interview.inditex.ms.domain.entity.ProductEntity;
import es.dtr.job.interview.inditex.ms.domain.service.product.ProductDto;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ProductControllerIT extends ControllerBaseIT implements CrudControllerIT<ProductDto, ProductEntity, Long> {

    @Override
    public String getBasePath() {
        return ProductControllerInterface.BASE_PATH;
    }

    @Override
    public Long getRequestId() {
        return 35455L;
    }

    @Override
    public Long getRequestIdNotExists() {
        return 100L;
    }

    @Override
    public ProductDto getElementCreate() {
        return ProductDto.builder().name(TestData.TEST_STRING).build();
    }

    @Override
    public ProductDto getElementUpdate() {
        return ProductDto.builder().name(TestData.TEST_STRING).build();
    }

    @Override
    public List<ResultMatcher> getAllTestMatchers() {
        return List.of(
                MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)),
                MockMvcResultMatchers.jsonPath("$[0].id", Matchers.comparesEqualTo(35455)),
                MockMvcResultMatchers.jsonPath("$[0].name", Matchers.equalTo("Camiseta manga corta Iron Maiden"))
        );
    }

    @Override
    public List<ResultMatcher> getByIdTestMatchers() {
        return List.of(
                MockMvcResultMatchers.jsonPath("$.id", Matchers.comparesEqualTo(35455)),
                MockMvcResultMatchers.jsonPath("$.name", Matchers.equalTo("Camiseta manga corta Iron Maiden"))
        );
    }

    @Override
    public String getFindByCorrectFiltersButNoResults() {
        return QueryDslFilter.builder()
                .key("name")
                .operator(QueryDslOperators.EQUALS)
                .value("NotExists")
                .build().toString();
    }

    @Override
    public String getFindByCorrectFiltersWithResults() {
        return QueryDslFilter.builder()
                .key("id")
                .operator(QueryDslOperators.EQUALS)
                .value(getRequestId())
                .build().toString();
    }

    @Override
    public List<ResultMatcher> getFindByTestMatchers() {
        return List.of(
                MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)),
                MockMvcResultMatchers.jsonPath("$[0].id", Matchers.comparesEqualTo(35455)),
                MockMvcResultMatchers.jsonPath("$[0].name", Matchers.equalTo("Camiseta manga corta Iron Maiden"))
        );
    }

}
