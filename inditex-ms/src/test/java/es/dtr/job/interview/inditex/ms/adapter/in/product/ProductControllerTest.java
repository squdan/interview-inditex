package es.dtr.job.interview.inditex.ms.adapter.in.product;

import es.dtr.job.interview.commons.TestData;
import es.dtr.job.interview.commons.service.crud.CrudService;
import es.dtr.job.interview.commons.test.controller.CrudControllerTest;
import es.dtr.job.interview.inditex.ms.adapter.in.ControllerBaseTest;
import es.dtr.job.interview.inditex.ms.domain.entity.ProductEntity;
import es.dtr.job.interview.inditex.ms.domain.service.product.ProductDto;
import es.dtr.job.interview.inditex.ms.domain.service.product.ProductService;
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
@WebMvcTest(controllers = ProductController.class)
@AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest extends ControllerBaseTest implements CrudControllerTest<ProductDto, ProductEntity, Long> {

    // Mocks
    @MockBean
    private ProductService crudService;

    // CrudControllerTest - Requirements
    @Override
    public CrudService<ProductDto, ProductEntity, Long> getCrudService() {
        return this.crudService;
    }

    @Override
    public String getBasePath() {
        return ProductControllerInterface.BASE_PATH;
    }

    @Override
    public Long getRequestId() {
        return 35455L;
    }

    @Override
    public ProductDto getElement() {
        return ProductDto.builder().id(getRequestId()).name(TestData.TEST_STRING).createdOn(TestData.NOW).lastUpdatedOn(TestData.NOW).build();
    }

    @Override
    public List<ProductDto> getElements() {
        return List.of(
                ProductDto.builder().id(getRequestId()).name(TestData.TEST_STRING).createdOn(TestData.NOW).lastUpdatedOn(TestData.NOW).build(),
                ProductDto.builder().id(getRequestId()).name(TestData.TEST_STRING).createdOn(TestData.NOW).lastUpdatedOn(TestData.NOW).build()
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
