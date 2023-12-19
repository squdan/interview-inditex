package es.dtr.job.interview.commons.test.service;

import es.dtr.job.interview.commons.error.GenericException;
import es.dtr.job.interview.commons.hexagonal.domain.repository.CrudDomainRepository;
import es.dtr.job.interview.commons.hexagonal.domain.service.crud.CrudService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Objects;

public interface CrudGetServiceTest<T, ID> {

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

    @Test
    default void test_get_whenNoExists_thenThrowException() {
        // Mocks
        Mockito.when(getCrudRepository().get(getRequestId())).thenThrow(CrudService.NOT_FOUND);

        // Test execution
        final GenericException thrown = Assertions.assertThrows(CrudService.NOT_FOUND.getClass(), () -> getCrudService().get(getRequestId()));

        // Response validation
        Assertions.assertTrue(Objects.nonNull(thrown));
        Assertions.assertEquals(CrudService.NOT_FOUND.getError().getHttpCode(), thrown.getError().getHttpCode());
        Assertions.assertEquals(CrudService.NOT_FOUND.getError().getMessage(), thrown.getError().getMessage());
    }

    @Test
    default void test_get_whenExists_thenReturnElement() {
        // Mocks
        Mockito.when(getCrudRepository().get(getRequestId())).thenReturn(getElementEntity());

        // Test execution
        final T element = getCrudService().get(getRequestId());

        // Response validation
        Assertions.assertTrue(Objects.nonNull(element));
        Assertions.assertEquals(getElementEntity(), element);
    }
}
