package es.dtr.job.interview.commons.it.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.dtr.job.interview.commons.api.crud.CrudCreateController;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public interface CrudCreateControllerIT<T, K, ID> {

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
     * DTO element information for CREATE operation.
     */
    T getElementCreate();

    @Test
    @WithMockUser
    default void test_create_whenOk_thenReturnId() throws Exception {
        // Test execution
        final ResultActions restResponse = getMockMvc().perform(
                MockMvcRequestBuilders
                        .post(getBasePath() + CrudCreateController.ENDPOINT_CREATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getObjectMapper().writeValueAsString(getElementCreate()))
        );

        // Response validation
        restResponse
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }
}
