package es.dtr.job.interview.commons.it.controller;

import es.dtr.job.interview.commons.hexagonal.application.rest.crud.CrudGetController;
import es.dtr.job.interview.commons.hexagonal.domain.service.crud.CrudService;
import org.apache.commons.collections4.CollectionUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

public interface CrudGetControllerIT<T, K, ID> {

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
     * Request ID not existing in database.
     */
    ID getRequestIdNotExists();

    /**
     * ResultMatcher to validate operation GET results.
     */
    List<ResultMatcher> getByIdTestMatchers();

    @Test
    @WithMockUser
    default void test_get_whenNoExists_then404() throws Exception {
        // Test execution
        final ResultActions restResponse = getMockMvc().perform(
                MockMvcRequestBuilders
                        .get(getBasePath() + CrudGetController.ENDPOINT_GET, getRequestIdNotExists())
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
        // Test execution
        final ResultActions restResponse = getMockMvc().perform(
                MockMvcRequestBuilders
                        .get(getBasePath() + CrudGetController.ENDPOINT_GET, getRequestId())
        );

        // Response validation
        restResponse
                .andExpect(MockMvcResultMatchers.status().isOk());

        if (CollectionUtils.isNotEmpty(getByIdTestMatchers())) {
            for (ResultMatcher matcher : getByIdTestMatchers()) {
                restResponse.andExpect(matcher);
            }
        }
    }
}
