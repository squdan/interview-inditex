package es.dtr.job.interview.commons.it.controller;

import es.dtr.job.interview.commons.hexagonal.application.rest.crud.CrudFindController;
import org.apache.commons.collections4.CollectionUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

public interface CrudFindControllerIT<T, K, ID> {

    /**
     * Mocked CRUD Service.
     */
    MockMvc getMockMvc();

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
     * Filters to apply in a search with correct key, but not existing value.
     * <p>
     * Exaample: name=notExistingValue
     */
    String getFindByCorrectFiltersButNoResults();

    /**
     * Filters to apply in a correct search.
     * <p>
     * Exaample: name=test
     */
    String getFindByCorrectFiltersWithResults();

    /**
     * ResultMatcher to validate operation FIND_BY results.
     */
    List<ResultMatcher> getFindByTestMatchers();

    /**
     * ResultMatcher to validate operation FIND_BY when no filters received and returns all results.
     */
    List<ResultMatcher> getFindByNoFiltersTestMatchers();

    @Test
    @WithMockUser
    default void test_findBy_whenFieldError_thenEmptyResponse() throws Exception {
        // Test execution
        final ResultActions restResponse = getMockMvc().perform(
                MockMvcRequestBuilders
                        .get(getBasePath() + CrudFindController.ENDPOINT_FIND_BY)
                        .param(CrudFindController.QUERY_FILTER_PARAM, "fieldUnknownX=Error")
        );

        // Response validation
        restResponse
                .andExpect(MockMvcResultMatchers.status().isInternalServerError());
    }

    @Test
    @WithMockUser
    default void test_findBy_whenNoMatch_thenEmptyResponse() throws Exception {
        // Test execution
        final ResultActions restResponse = getMockMvc().perform(
                MockMvcRequestBuilders
                        .get(getBasePath() + CrudFindController.ENDPOINT_FIND_BY)
                        .param(CrudFindController.QUERY_FILTER_PARAM, getFindByCorrectFiltersButNoResults())
        );

        // Response validation
        restResponse
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    @WithMockUser
    default void test_findBy_whenNoFilters_thenResponseWithData() throws Exception {
        // Test execution
        final ResultActions restResponse = getMockMvc().perform(
                MockMvcRequestBuilders
                        .get(getBasePath() + CrudFindController.ENDPOINT_FIND_BY)
        );

        // Response validation
        restResponse
                .andExpect(MockMvcResultMatchers.status().isOk());

        if (CollectionUtils.isNotEmpty(getFindByNoFiltersTestMatchers())) {
            for (ResultMatcher matcher : getFindByNoFiltersTestMatchers()) {
                restResponse.andExpect(matcher);
            }
        }
    }

    @Test
    @WithMockUser
    default void test_findBy_whenCorrectFilters_thenResponseWithData() throws Exception {
        // Test execution
        final ResultActions restResponse = getMockMvc().perform(
                MockMvcRequestBuilders
                        .get(getBasePath() + CrudFindController.ENDPOINT_FIND_BY)
                        .param(CrudFindController.QUERY_FILTER_PARAM, getFindByCorrectFiltersWithResults())
        );

        // Response validation
        restResponse
                .andExpect(MockMvcResultMatchers.status().isOk());

        if (CollectionUtils.isNotEmpty(getFindByTestMatchers())) {
            for (ResultMatcher matcher : getFindByTestMatchers()) {
                restResponse.andExpect(matcher);
            }
        }
    }
}
