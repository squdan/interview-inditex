package es.dtr.job.interview.inditex.ms.adapter.in.brand;

import es.dtr.job.interview.commons.TestData;
import es.dtr.job.interview.commons.service.crud.CrudService;
import es.dtr.job.interview.commons.test.controller.CrudControllerTest;
import es.dtr.job.interview.inditex.ms.adapter.in.ControllerBaseTest;
import es.dtr.job.interview.inditex.ms.domain.entity.BrandEntity;
import es.dtr.job.interview.inditex.ms.domain.service.brand.BrandDto;
import es.dtr.job.interview.inditex.ms.domain.service.brand.BrandService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = BrandController.class)
@AutoConfigureMockMvc(addFilters = false)
class BrandControllerTest extends ControllerBaseTest implements CrudControllerTest<BrandDto, BrandEntity, Long> {

    // Mocks
    @MockBean
    private BrandService crudService;

    // CrudControllerTest - Requirements
    @Override
    public CrudService<BrandDto, BrandEntity, Long> getCrudService() {
        return this.crudService;
    }

    @Override
    public String getBasePath() {
        return BrandControllerInterface.BASE_PATH;
    }

    @Override
    public Long getRequestId() {
        return 1L;
    }

    @Override
    public BrandDto getElement() {
        return BrandDto.builder().id(1L).name(TestData.TEST_STRING).createdOn(TestData.NOW).lastUpdatedOn(TestData.NOW).build();
    }

    @Override
    public List<BrandDto> getElements() {
        return List.of(
                BrandDto.builder().id(1L).name(TestData.TEST_STRING).createdOn(TestData.NOW).lastUpdatedOn(TestData.NOW).build(),
                BrandDto.builder().id(2L).name(TestData.TEST_STRING).createdOn(TestData.NOW).lastUpdatedOn(TestData.NOW).build()
        );
    }

    @Override
    public List<ResultMatcher> getElementsTestMatchers() {
        return List.of(
                MockMvcResultMatchers.jsonPath("$[0].id", Matchers.comparesEqualTo(getElements().get(0).getId().intValue())),
                MockMvcResultMatchers.jsonPath("$[0].name", Matchers.equalTo(getElements().get(0).getName())),
                MockMvcResultMatchers.jsonPath("$[1].id", Matchers.comparesEqualTo(getElements().get(1).getId().intValue())),
                MockMvcResultMatchers.jsonPath("$[1].name", Matchers.equalTo(getElements().get(1).getName()))
        );
    }

    @Override
    public List<ResultMatcher> getElementTestMatchers() {
        return List.of(
                MockMvcResultMatchers.jsonPath("$.id", Matchers.comparesEqualTo(getElement().getId().intValue())),
                MockMvcResultMatchers.jsonPath("$.name", Matchers.equalTo(getElement().getName()))
        );
    }
}
