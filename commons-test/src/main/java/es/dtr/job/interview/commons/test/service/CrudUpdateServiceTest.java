package es.dtr.job.interview.commons.test.service;

import es.dtr.job.interview.commons.data.CrudRepository;
import es.dtr.job.interview.commons.error.GenericException;
import es.dtr.job.interview.commons.service.crud.CrudMapper;
import es.dtr.job.interview.commons.service.crud.CrudService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Objects;

public interface CrudUpdateServiceTest<T, K, ID> {

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
    default void test_update_whenElementNotExists_thenThrowException() {
        // Mocks
        Mockito.when(getCrudRepository().existsById(getRequestId())).thenReturn(false);

        // Test execution
        final GenericException thrown = Assertions.assertThrows(CrudService.NOT_FOUND.getClass(), () -> getCrudService().update(getRequestId(), getElementDto()));

        // Response validation
        Assertions.assertTrue(Objects.nonNull(thrown));
        Assertions.assertEquals(CrudService.NOT_FOUND.getError().getHttpCode(), thrown.getError().getHttpCode());
        Assertions.assertEquals(CrudService.NOT_FOUND.getError().getMessage(), thrown.getError().getMessage());
    }

    @Test
    default void test_update_whenElementExists_thenOk() {
        // Mocks
        Mockito.when(getCrudRepository().existsById(getRequestId())).thenReturn(true);
        Mockito.when(getCrudMapper().dtoToEntity(getElementDto())).thenReturn(getElementEntity());
        Mockito.when(getCrudRepository().save(getElementEntity())).thenReturn(getElementEntity());

        // Test execution
        getCrudService().update(getRequestId(), getElementDto());
    }
}
