package es.dtr.job.interview.commons.service.crud;

import es.dtr.job.interview.commons.data.CrudRepository;
import es.dtr.job.interview.commons.service.ServiceException;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Interface to provide default CRUD GET services implementations.
 *
 * @param <T>  DTO.
 * @param <K>  Entity.
 * @param <ID> Entity id.
 */
@Validated
public interface CrudGetService<T, K, ID> {

    // Constants
    ServiceException NOT_FOUND = new ServiceException("Element not found.", HttpStatus.NOT_FOUND);

    // Dependencies
    CrudRepository<K, ID> getRepository();

    CrudMapper<K, T> getMapper();

    default List<T> getAll() {
        final List<K> elements = getRepository().findAll();
        return getMapper().entityToDto(elements);
    }

    default T get(@NotNull final ID id) {
        final Optional<K> mayElement = getRepository().findById(id);

        if (mayElement.isEmpty()) {
            throw NOT_FOUND;
        } else {
            return getMapper().entityToDto(mayElement.get());
        }
    }
}
