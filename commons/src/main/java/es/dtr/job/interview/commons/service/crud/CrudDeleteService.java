package es.dtr.job.interview.commons.service.crud;

import es.dtr.job.interview.commons.data.CrudRepository;
import es.dtr.job.interview.commons.service.ServiceException;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;

/**
 * Interface to provide default CRUD services implementations.
 *
 * @param <T>  DTO.
 * @param <K>  Entity.
 * @param <ID> Entity id.
 */
@Validated
public interface CrudDeleteService<T, K, ID> {

    // Constants
    ServiceException NOT_FOUND = new ServiceException("Element not found.", HttpStatus.NOT_FOUND);

    // Dependencies
    CrudRepository<K, ID> getRepository();


    default void delete(@NotNull final ID id) {
        if (!getRepository().existsById(id)) {
            throw NOT_FOUND;
        } else {
            getRepository().deleteById(id);
        }
    }
}
