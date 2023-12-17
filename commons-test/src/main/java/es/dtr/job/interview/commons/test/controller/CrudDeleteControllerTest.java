package es.dtr.job.interview.commons.test.controller;

import es.dtr.job.interview.commons.api.crud.CrudDeleteController;
import es.dtr.job.interview.commons.service.crud.CrudService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public interface CrudDeleteControllerTest<T, K, ID> {

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

    @Test
    @WithMockUser
    default void test_delete_whenElementNotExists_thenReturn404() throws Exception {
        // Mocks
        Mockito.doThrow(CrudService.NOT_FOUND).when(getCrudService()).delete(getRequestId());

        // Test execution
        final ResultActions restResponse = getMockMvc().perform(
                MockMvcRequestBuilders
                        .delete(getBasePath() + CrudDeleteController.ENDPOINT_DELETE, getRequestId())
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
