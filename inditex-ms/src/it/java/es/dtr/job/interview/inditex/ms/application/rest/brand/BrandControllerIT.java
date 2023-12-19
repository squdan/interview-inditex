package es.dtr.job.interview.inditex.ms.application.rest.brand;

import es.dtr.job.interview.commons.TestData;
import es.dtr.job.interview.commons.hexagonal.domain.service.querydsl.QueryDslFilter;
import es.dtr.job.interview.commons.hexagonal.domain.service.querydsl.QueryDslOperators;
import es.dtr.job.interview.commons.it.controller.CrudControllerIT;
import es.dtr.job.interview.inditex.ms.application.rest.ControllerBaseIT;
import es.dtr.job.interview.inditex.ms.domain.entity.BrandEntity;
import lombok.Getter;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@Getter
@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class BrandControllerIT extends ControllerBaseIT implements CrudControllerIT<BrandDto, BrandEntity, Long> {

    // Configuration
    private final String basePath = BrandControllerInterface.BASE_PATH;

    @Override
    public Long getRequestId() {
        return 1L;
    }

    @Override
    public Long getRequestIdNotExists() {
        return 100L;
    }

    @Override
    public BrandDto getElementCreate() {
        return BrandDto.builder().name(TestData.TEST_STRING).build();
    }

    @Override
    public BrandDto getElementUpdate() {
        return BrandDto.builder().name(TestData.TEST_STRING).build();
    }

    @Override
    public List<ResultMatcher> getFindByNoFiltersTestMatchers() {
        return List.of(
                MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)),
                MockMvcResultMatchers.jsonPath("$[0].id", Matchers.comparesEqualTo(1)),
                MockMvcResultMatchers.jsonPath("$[0].name", Matchers.equalTo("ZARA"))
        );
    }

    @Override
    public List<ResultMatcher> getByIdTestMatchers() {
        return List.of(
                MockMvcResultMatchers.jsonPath("$.id", Matchers.comparesEqualTo(1)),
                MockMvcResultMatchers.jsonPath("$.name", Matchers.equalTo("ZARA"))
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
                .key("name")
                .operator(QueryDslOperators.EQUALS)
                .value("ZARA")
                .build().toString();
    }

    @Override
    public List<ResultMatcher> getFindByTestMatchers() {
        return List.of(
                MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)),
                MockMvcResultMatchers.jsonPath("$[0].id", Matchers.comparesEqualTo(1)),
                MockMvcResultMatchers.jsonPath("$[0].name", Matchers.equalTo("ZARA"))
        );
    }

}
