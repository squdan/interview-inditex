package es.dtr.job.interview.commons.it.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.dtr.job.interview.commons.hexagonal.application.rest.crud.CrudDeleteController;
import org.junit.jupiter.api.Test;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public interface CrudDeleteControllerIT<T, K, ID> {

    /**
     * Jackson ObjectMapper instance to map request body to string.
     */
    ObjectMapper getObjectMapper();

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
     * DTO element information for UPDATE operation.
     */
    T getElementUpdate();

    @Test
    @WithMockUser
    default void test_delete_whenElementNotExists_thenReturn404() throws Exception {
        // Test execution
        final ResultActions restResponse = getMockMvc().perform(
                MockMvcRequestBuilders
                        .delete(getBasePath() + CrudDeleteController.ENDPOINT_DELETE, getRequestIdNotExists())
        );

        // Response validation
        restResponse
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser
    default void test_delete_whenElementExists_thenReturn204() throws Exception {
        // Test execution
        final ResultActions restResponse = getMockMvc().perform(
                MockMvcRequestBuilders
                        .delete(getBasePath() + CrudDeleteController.ENDPOINT_DELETE, getRequestId())
        );

        // Response validation
        restResponse
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
