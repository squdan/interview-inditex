package es.dtr.job.interview.commons.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.dtr.job.interview.commons.api.crud.CrudUpdateController;
import es.dtr.job.interview.commons.service.crud.CrudService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public interface CrudUpdateControllerTest<T, K, ID> {

    /**
     * Jackson ObjectMapper instance to map request body to string.
     */
    ObjectMapper getObjectMapper();

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

    @Test
    @WithMockUser
    default void test_update_whenElementNotExists_thenReturn404() throws Exception {
        // Mocks
        Mockito.doThrow(CrudService.NOT_FOUND).when(getCrudService()).update(Mockito.any(), Mockito.any());

        // Test execution
        final ResultActions restResponse = getMockMvc().perform(
                MockMvcRequestBuilders
                        .put(getBasePath() + CrudUpdateController.ENDPOINT_UPDATE, getRequestId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getObjectMapper().writeValueAsString(getElement()))
        );

        // Response validation
        restResponse
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser
    default void test_update_whenElementExists_thenReturn204() throws Exception {
        // Test execution
        final ResultActions restResponse = getMockMvc().perform(
                MockMvcRequestBuilders
                        .put(getBasePath() + CrudUpdateController.ENDPOINT_UPDATE, getRequestId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getObjectMapper().writeValueAsString(getElement()))
        );

        // Response validation
        restResponse
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
