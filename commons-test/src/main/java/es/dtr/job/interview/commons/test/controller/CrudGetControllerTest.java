package es.dtr.job.interview.commons.test.controller;

import es.dtr.job.interview.commons.api.crud.CrudGetController;
import es.dtr.job.interview.commons.service.crud.CrudService;
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

import java.util.ArrayList;
import java.util.List;

public interface CrudGetControllerTest<T, K, ID> {

    /**
     * Mocked CRUD Service.
     */
    MockMvc getMockMvc();

    /**
     * Mocked CRUD Service.
     */
    CrudService<T, K, ID> getCrudService();

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
    T getElement();

    /**
     * List of DTO elements to return in mocked service.
     */
    List<T> getElements();

    /**
     * List of ResultMatchers to validate REST service response of List elements.
     */
    List<ResultMatcher> getElementsTestMatchers();

    /**
     * ResultMatcher to validate REST service response of single element.
     */
    List<ResultMatcher> getElementTestMatchers();

    @Test
    @WithMockUser
    default void test_getAll_whenNoData_thenEmptyResponse() throws Exception {
        // Mocks
        Mockito.when(getCrudService().getAll()).thenReturn(new ArrayList<>());

        // Test execution
        final ResultActions restResponse = getMockMvc().perform(
                MockMvcRequestBuilders
                        .get(getBasePath() + CrudGetController.ENDPOINT_GET_ALL)
        );

        // Response validation
        restResponse
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    @WithMockUser
    default void test_getAll_whenData_thenResponseWithData() throws Exception {
        // Mocks
        Mockito.when(getCrudService().getAll()).thenReturn(getElements());

        // Test execution
        final ResultActions restResponse = getMockMvc().perform(
                MockMvcRequestBuilders
                        .get(getBasePath() + CrudGetController.ENDPOINT_GET_ALL)
        );

        // Response validation
        restResponse
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(getElements().size())));

        if (CollectionUtils.isNotEmpty(getElementsTestMatchers())) {
            for (ResultMatcher matcher : getElementsTestMatchers()) {
                restResponse.andExpect(matcher);
            }
        }
    }

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
        Mockito.when(getCrudService().get(Mockito.any())).thenReturn(getElement());

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
