package es.dtr.job.interview.commons.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.dtr.job.interview.commons.hexagonal.application.rest.crud.CrudControllerMapper;
import es.dtr.job.interview.commons.hexagonal.application.rest.crud.CrudCreateController;
import es.dtr.job.interview.commons.hexagonal.domain.service.crud.CrudService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public interface CrudCreateControllerTest<T, K, ID> {

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

    @Test
    @WithMockUser
    default void test_create_whenOk_thenReturnId() throws Exception {
        // Mocks
        Mockito.when(getCrudService().create(Mockito.any())).thenReturn(getElementEntity());
        Mockito.when(getCrudMapper().domainEntityToDto(getElementEntity())).thenReturn(getElementDto());

        // Test execution
        final ResultActions restResponse = getMockMvc().perform(
                MockMvcRequestBuilders
                        .post(getBasePath() + CrudCreateController.ENDPOINT_CREATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getObjectMapper().writeValueAsString(getElementDto()))
        );

        // Response validation
        restResponse
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.notNullValue()));
    }
}
