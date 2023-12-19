package es.dtr.job.interview.commons.it.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.dtr.job.interview.commons.hexagonal.application.rest.crud.CrudUpdateController;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public interface CrudUpdateControllerIT<T, K, ID> {

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
     * DTO element information for UPDATE operation.
     */
    T getElementUpdate();

    @Test
    @WithMockUser
    default void test_update_whenElementExists_thenReturn204() throws Exception {
        // Test execution
        final ResultActions restResponse = getMockMvc().perform(
                MockMvcRequestBuilders
                        .put(getBasePath() + CrudUpdateController.ENDPOINT_UPDATE, getRequestId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getObjectMapper().writeValueAsString(getElementUpdate()))
        );

        // Response validation
        restResponse
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
