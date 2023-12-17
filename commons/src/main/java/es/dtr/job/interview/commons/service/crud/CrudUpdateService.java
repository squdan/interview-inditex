package es.dtr.job.interview.commons.service.crud;

import es.dtr.job.interview.commons.data.CrudRepository;
import es.dtr.job.interview.commons.service.ServiceException;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Optional;

/**
 * Interface to provide default CRUD services implementations.
 *
 * @param <T>  DTO.
 * @param <K>  Entity.
 * @param <ID> Entity id.
 */
@Validated
public interface CrudUpdateService<T, K, ID> {

    // Constants
    ServiceException NOT_FOUND = new ServiceException("Element not found.", HttpStatus.NOT_FOUND);

    // Dependencies
    CrudRepository<K, ID> getRepository();

    CrudMapper<K, T> getMapper();

    default void update(@NotNull final ID id, @NotNull @Valid final T updateRequest) {
        if (!getRepository().existsById(id)) {
            throw NOT_FOUND;
        } else {
            final K elementToUpdate = getMapper().dtoToEntity(updateRequest);

            // Search id from element to update
            try {
                final Field fieldId = getIdFieldFromEntity(elementToUpdate);
                fieldId.setAccessible(true);
                fieldId.set(elementToUpdate, id);
                getRepository().save(elementToUpdate);
            } catch (final IllegalAccessException e) {
                throw new ServiceException(e, "Error accessing to field ID.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    private Field getIdFieldFromEntity(final K entity) {
        Field result;

        final Optional<Field> mayFieldId = Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Id.class))
                .findFirst();

        if (mayFieldId.isEmpty()) {
            throw new ServiceException(
                    "Element ID to update couldn't be found, it's needed to annotate entity ID field with @Id.",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        } else {
            result = mayFieldId.get();
        }

        return result;
    }
}
