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
public interface CrudCreateService<T, K, ID> {

    // Constants
    ServiceException NOT_FOUND = new ServiceException("Element not found.", HttpStatus.NOT_FOUND);

    // Dependencies
    CrudRepository<K, ID> getRepository();

    CrudMapper<K, T> getMapper();

    default ID create(@NotNull @Valid final T createRequest) {
        ID result = null;

        // Create element into database
        final K elementToCreate = getMapper().dtoToEntity(createRequest);
        final K elementCreated = getRepository().save(elementToCreate);

        // Search id from created element
        try {
            final Field fieldId = getIdFieldFromEntity(elementToCreate);
            fieldId.setAccessible(true);
            result = (ID) fieldId.get(elementCreated);
        } catch (final IllegalAccessException e) {
            throw new ServiceException(e, "Error accessing to field ID.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return result;
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
