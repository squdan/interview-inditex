package es.dtr.job.interview.commons.test.controller;

import es.dtr.job.interview.commons.TestData;
import es.dtr.job.interview.commons.api.crud.CrudFindController;
import es.dtr.job.interview.commons.service.crud.CrudService;
import org.apache.commons.collections4.CollectionUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

public interface CrudFindControllerTest<T, K, ID> {

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
     * List of DTO elements to return in mocked service.
     */
    List<T> getElements();

    /**
     * List of ResultMatchers to validate REST service response of List elements.
     */
    List<ResultMatcher> getElementsTestMatchers();

    @Test
    @WithMockUser
    default void test_findBy_whenNoMatch_thenEmptyResponse() throws Exception {
        // Mocks
        Mockito.when(getCrudService().findBy(Mockito.anyList())).thenReturn(new ArrayList<>());

        // Test execution
        final ResultActions restResponse = getMockMvc().perform(
                MockMvcRequestBuilders
                        .get(getBasePath() + CrudFindController.ENDPOINT_FIND_BY)
                        .param(CrudFindController.QUERY_FILTER_PARAM, "fieldUnknown=Error")
        );

        // Response validation
        restResponse
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    @WithMockUser
    default void test_findBy_whenData_thenResponseWithData() throws Exception {
        // Mocks
        Mockito.when(getCrudService().findBy(TestData.TEST_FILTERS_LIST_STRING)).thenReturn(getElements());

        // Test execution
        final ResultActions restResponse = getMockMvc().perform(
                MockMvcRequestBuilders
                        .get(getBasePath() + CrudFindController.ENDPOINT_FIND_BY)
                        .param(CrudFindController.QUERY_FILTER_PARAM, TestData.TEST_FILTERS_STRING)
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
}
