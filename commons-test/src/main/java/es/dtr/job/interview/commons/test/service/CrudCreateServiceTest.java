package es.dtr.job.interview.commons.test.service;

import es.dtr.job.interview.commons.data.CrudRepository;
import es.dtr.job.interview.commons.service.crud.CrudMapper;
import es.dtr.job.interview.commons.service.crud.CrudService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Objects;

public interface CrudCreateServiceTest<T, K, ID> {

    /**
     * CRUD Service to test.
     */
    CrudService<T, K, ID> getCrudService();

    /**
     * Mocked CRUD Repository.
     */
    CrudRepository<K, ID> getCrudRepository();

    /**
     * Mocked CRUD Mapper.
     */
    CrudMapper<K, T> getCrudMapper();

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
    K getElementEntity();

    /**
     * DTO Instance.
     */
    T getElementDto();

    @Test
    default void test_create_whenOk_thenReturnId() {
        // Mocks
        Mockito.when(getCrudMapper().dtoToEntity(getElementDto())).thenReturn(getElementEntity());
        Mockito.when(getCrudRepository().save(getElementEntity())).thenReturn(getElementEntity());

        // Test execution
        final ID elementId = getCrudService().create(getElementDto());

        // Response validation
        Assertions.assertTrue(Objects.nonNull(elementId));
        Assertions.assertEquals(getRequestId(), elementId);
    }
}
