package es.dtr.job.interview.commons.test.service;

import es.dtr.job.interview.commons.hexagonal.domain.repository.CrudDomainRepository;
import es.dtr.job.interview.commons.hexagonal.domain.service.crud.CrudService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Objects;

public interface CrudCreateServiceTest<T, ID> {

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
    default void test_create_whenOk_thenReturnId() {
        // Mocks
        Mockito.when(getCrudRepository().create(getElementEntity())).thenReturn(getRequestId());

        // Test execution
        final ID elementId = getCrudService().create(getElementEntity());

        // Response validation
        Assertions.assertTrue(Objects.nonNull(elementId));
        Assertions.assertEquals(getRequestId(), elementId);
    }
}
