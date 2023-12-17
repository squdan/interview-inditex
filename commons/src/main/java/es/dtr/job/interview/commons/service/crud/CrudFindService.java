package es.dtr.job.interview.commons.service.crud;

import es.dtr.job.interview.commons.data.CrudRepository;
import es.dtr.job.interview.commons.service.ServiceException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * Interface to provide default CRUD services implementations.
 *
 * @param <T>  DTO.
 * @param <K>  Entity.
 * @param <ID> Entity id.
 */
@Validated
public interface CrudFindService<T, K, ID> {

    // Constants
    ServiceException NOT_FOUND = new ServiceException("Element not found.", HttpStatus.NOT_FOUND);
    ServiceException NOT_IMPLEMENTED = new ServiceException("Not implemented.", HttpStatus.INTERNAL_SERVER_ERROR);

    // Dependencies
    CrudRepository<K, ID> getRepository();

    CrudMapper<K, T> getMapper();

    default List<T> findBy(@NotNull @Valid final List<String> filters) {
        return findBy(filters, null);
    }

    default List<T> findBy(@NotNull @Valid final List<String> filters, @NotNull final Pageable pageable) {
        throw NOT_IMPLEMENTED;
    }
}
