package es.dtr.job.interview.commons.test.controller;

import es.dtr.job.interview.commons.hexagonal.application.rest.crud.CrudControllerMapper;
import es.dtr.job.interview.commons.hexagonal.application.rest.crud.CrudGetController;
import es.dtr.job.interview.commons.hexagonal.domain.service.crud.CrudService;
import org.apache.commons.collections4.CollectionUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

public interface CrudGetControllerTest<T, K, ID> {

    /**
     * Mocked CRUD Service.
     */
    MockMvc getMockMvc();

    /**
     * Mocked CRUD Service.
     */
    CrudService<K, ID> getCrudService();

    /**
     * Mocked CRUD Mapper.
     */
    CrudControllerMapper<T, K> getCrudMapper();

    /**
     * Base controller path.
     */
    String getBasePath();

    /**
     * Request ID example for service.
     * <p>
     * Examples:
     * - 1L
     * - UUID.randomUUID()
     */
    ID getRequestId();

    /**
     * Single DTO element to return in mocked service.
     */
    T getElementDto();

    /**
     * Single DTO element to return in mocked service.
     */
    K getElementEntity();

    /**
     * List of DTO elements to return in mocked service.
     */
    List<T> getElementsDto();

    /**
     * ResultMatcher to validate REST service response of single element.
     */
    List<ResultMatcher> getElementTestMatchers();

    @Test
    @WithMockUser
    default void test_get_whenNoExists_then404() throws Exception {
        // Mocks
        Mockito.when(getCrudService().get(Mockito.any())).thenThrow(CrudService.NOT_FOUND);

        // Test execution
        final ResultActions restResponse = getMockMvc().perform(
                MockMvcRequestBuilders
                        .get(getBasePath() + CrudGetController.ENDPOINT_GET, getRequestId())
        );

        // Response validation
        restResponse
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpCode", Matchers.equalTo(HttpStatus.NOT_FOUND.name())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.equalTo(CrudService.NOT_FOUND.getError().getMessage())));
    }

    @Test
    @WithMockUser
    default void test_get_whenExists_thenResponseWithElement() throws Exception {
        // Mocks
        Mockito.when(getCrudService().get(Mockito.any())).thenReturn(this.getElementEntity());
        Mockito.when(getCrudMapper().domainEntityToDto(this.getElementEntity())).thenReturn(this.getElementDto());

        // Test execution
        final ResultActions restResponse = getMockMvc().perform(
                MockMvcRequestBuilders
                        .get(getBasePath() + CrudGetController.ENDPOINT_GET, getRequestId())
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
}
