package es.dtr.job.interview.commons.test.service;

import es.dtr.job.interview.commons.error.GenericException;
import es.dtr.job.interview.commons.hexagonal.domain.repository.CrudDomainRepository;
import es.dtr.job.interview.commons.hexagonal.domain.service.crud.CrudService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Objects;

public interface CrudUpdateServiceTest<T, ID> {

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
    default void test_update_whenElementNotExists_thenThrowException() {
        // Mocks
        Mockito.doThrow(CrudService.NOT_FOUND).when(getCrudRepository()).update(getRequestId(), getElementEntity());

        // Test execution
        final GenericException thrown = Assertions.assertThrows(CrudService.NOT_FOUND.getClass(), () -> getCrudService().update(getRequestId(), getElementEntity()));

        // Response validation
        Assertions.assertTrue(Objects.nonNull(thrown));
        Assertions.assertEquals(CrudService.NOT_FOUND.getError().getHttpCode(), thrown.getError().getHttpCode());
        Assertions.assertEquals(CrudService.NOT_FOUND.getError().getMessage(), thrown.getError().getMessage());
    }

    @Test
    default void test_update_whenElementExists_thenOk() {
        // Test execution
        getCrudService().update(getRequestId(), getElementEntity());
    }
}
