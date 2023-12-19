package es.dtr.job.interview.commons.test.service;

import es.dtr.job.interview.commons.hexagonal.domain.repository.CrudDomainRepository;
import es.dtr.job.interview.commons.hexagonal.domain.service.crud.CrudService;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

// TODO:
public interface CrudFindServiceTest<T, ID> {

    /**
     * CRUD Service to test.
     */
    CrudService<T, ID> getCrudService();

    /**
     * Mocked CRUD Repository.
     */
    CrudDomainRepository<T, ID> getCrudRepository();

    /**
     * Request ID example for service.
     * <p>
     * Examples:
     * - 1L
     * - UUID.randomUUID()
     */
    ID getRequestId();

    /**
     * Entity Instance.
     */
    T getElementEntity();

    /**
     * List of Entities Instance.
     */
    List<T> getElementsEntity();

    @Test
    default void test_find_whenNoData_thenEmptyResponse() {
        // Mocks
        Mockito.when(getCrudRepository().findBy(Mockito.any(), Mockito.any())).thenReturn(new ArrayList<>());

        // Test execution
        final List<T> elements = getCrudService().findBy(null, null);

        // Response validation
        Assertions.assertTrue(CollectionUtils.isEmpty(elements));
    }

    @Test
    default void test_find_whenData_thenReturnElements() {
        // Mocks
        Mockito.when(getCrudRepository().findBy(Mockito.any(), Mockito.any())).thenReturn(getElementsEntity());

        // Test execution
        final List<T> elements = getCrudService().findBy(null, null);

        // Response validation
        Assertions.assertTrue(CollectionUtils.isNotEmpty(elements));
        Assertions.assertArrayEquals(getElementsEntity().toArray(), elements.toArray());
    }
}
