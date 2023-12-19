package es.dtr.job.interview.inditex.ms.application.rest.brand;

import es.dtr.job.interview.commons.TestData;
import es.dtr.job.interview.commons.test.controller.CrudControllerTest;
import es.dtr.job.interview.inditex.ms.application.rest.ControllerBaseTest;
import es.dtr.job.interview.inditex.ms.domain.entity.BrandEntity;
import es.dtr.job.interview.inditex.ms.domain.service.brand.BrandService;
import lombok.Getter;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@Getter
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = BrandController.class)
@AutoConfigureMockMvc(addFilters = false)
class BrandControllerTest extends ControllerBaseTest implements CrudControllerTest<BrandDto, BrandEntity, Long> {

    // Configuration
    private final String basePath = BrandControllerInterface.BASE_PATH;

    // Mocks
    @MockBean
    private BrandService crudService;

    @MockBean
    private BrandControllerMapper crudMapper;

    @Override
    public Long getRequestId() {
        return 1L;
    }

    @Override
    public BrandDto getElementDto() {
        return BrandDto.builder().id(1L).name(TestData.TEST_STRING).createdOn(TestData.NOW).lastUpdatedOn(TestData.NOW).build();
    }

    public BrandEntity getElementEntity() {
        return BrandEntity.builder().id(1L).name(TestData.TEST_STRING).createdOn(TestData.NOW).lastUpdatedOn(TestData.NOW).build();
    }

    @Override
    public List<BrandDto> getElementsDto() {
        return List.of(BrandDto.builder().id(1L).name(TestData.TEST_STRING).createdOn(TestData.NOW).lastUpdatedOn(TestData.NOW).build(), BrandDto.builder().id(2L).name(TestData.TEST_STRING).createdOn(TestData.NOW).lastUpdatedOn(TestData.NOW).build());
    }

    @Override
    public List<BrandEntity> getElementsEntity() {
        return List.of(BrandEntity.builder().id(1L).name(TestData.TEST_STRING).createdOn(TestData.NOW).lastUpdatedOn(TestData.NOW).build(), BrandEntity.builder().id(2L).name(TestData.TEST_STRING).createdOn(TestData.NOW).lastUpdatedOn(TestData.NOW).build());
    }

    @Override
    public List<ResultMatcher> getElementsTestMatchers() {
        return List.of(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.comparesEqualTo(getElementsDto().get(0).getId().intValue())), MockMvcResultMatchers.jsonPath("$[0].name", Matchers.equalTo(getElementsDto().get(0).getName())), MockMvcResultMatchers.jsonPath("$[1].id", Matchers.comparesEqualTo(getElementsDto().get(1).getId().intValue())), MockMvcResultMatchers.jsonPath("$[1].name", Matchers.equalTo(getElementsDto().get(1).getName())));
    }

    @Override
    public List<ResultMatcher> getElementTestMatchers() {
        return List.of(MockMvcResultMatchers.jsonPath("$.id", Matchers.comparesEqualTo(this.getElementDto().getId().intValue())), MockMvcResultMatchers.jsonPath("$.name", Matchers.equalTo(this.getElementDto().getName())));
    }
}
