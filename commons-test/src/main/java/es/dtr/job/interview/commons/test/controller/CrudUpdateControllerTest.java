package es.dtr.job.interview.commons.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.dtr.job.interview.commons.hexagonal.application.rest.crud.CrudControllerMapper;
import es.dtr.job.interview.commons.hexagonal.application.rest.crud.CrudUpdateController;
import es.dtr.job.interview.commons.hexagonal.domain.service.crud.CrudService;
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
    default void test_update_whenElementNotExists_thenReturn404() throws Exception {
        // Mocks
        Mockito.doThrow(CrudService.NOT_FOUND).when(getCrudService()).update(Mockito.any(), Mockito.any());

        // Test execution
        final ResultActions restResponse = getMockMvc().perform(
                MockMvcRequestBuilders
                        .put(getBasePath() + CrudUpdateController.ENDPOINT_UPDATE, getRequestId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getObjectMapper().writeValueAsString(getElementDto()))
        );

        // Response validation
        restResponse
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @WithMockUser
    default void test_update_whenElementExists_thenReturn204() throws Exception {
        // Mocks
        Mockito.when(getCrudService().update(getRequestId(), getElementEntity())).thenReturn(getElementEntity());
        Mockito.when(getCrudMapper().dtoToDomainEntity(getElementDto())).thenReturn(getElementEntity());
        Mockito.when(getCrudMapper().domainEntityToDto(getElementEntity())).thenReturn(getElementDto());

        // Test execution
        final ResultActions restResponse = getMockMvc().perform(
                MockMvcRequestBuilders
                        .put(getBasePath() + CrudUpdateController.ENDPOINT_UPDATE, getRequestId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getObjectMapper().writeValueAsString(getElementDto()))
        );

        // Response validation
        restResponse
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
