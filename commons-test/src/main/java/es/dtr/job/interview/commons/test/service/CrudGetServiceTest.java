package es.dtr.job.interview.commons.test.service;

import es.dtr.job.interview.commons.data.CrudRepository;
import es.dtr.job.interview.commons.error.GenericException;
import es.dtr.job.interview.commons.service.crud.CrudMapper;
import es.dtr.job.interview.commons.service.crud.CrudService;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface CrudGetServiceTest<T, K, ID> {

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

    /**
     * List of Entities Instance.
     */
    List<K> getElementsEntity();

    /**
     * List of DTOs Instance.
     */
    List<T> getElementsDto();

    @Test
    default void test_getAll_whenNoData_thenEmptyResponse() {
        // Mocks
        Mockito.when(getCrudRepository().findAll()).thenReturn(new ArrayList<>());

        // Test execution
        final List<T> elements = getCrudService().getAll();

        // Response validation
        Assertions.assertTrue(CollectionUtils.isEmpty(elements));
    }

    @Test
    default void test_getAll_whenData_thenReturnElements() {
        // Mocks
        Mockito.when(getCrudRepository().findAll()).thenReturn(getElementsEntity());
        Mockito.when(getCrudMapper().entityToDto(getElementsEntity())).thenReturn(getElementsDto());

        // Test execution
        final List<T> elements = getCrudService().getAll();

        // Response validation
        Assertions.assertTrue(CollectionUtils.isNotEmpty(elements));
        Assertions.assertArrayEquals(getElementsDto().toArray(), elements.toArray());
    }

    @Test
    default void test_get_whenNoExists_thenThrowException() {
        // Mocks
        Mockito.when(getCrudRepository().findById(getRequestId())).thenReturn(Optional.empty());

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
        Mockito.when(getCrudRepository().findById(getRequestId())).thenReturn(Optional.of(getElementEntity()));
        Mockito.when(getCrudMapper().entityToDto(getElementEntity())).thenReturn(getElementDto());

        // Test execution
        final T element = getCrudService().get(getRequestId());

        // Response validation
        Assertions.assertTrue(Objects.nonNull(element));
        Assertions.assertEquals(getElementDto(), element);
    }
}
