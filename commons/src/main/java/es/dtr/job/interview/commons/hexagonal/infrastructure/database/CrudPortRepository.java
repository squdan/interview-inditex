package es.dtr.job.interview.commons.hexagonal.infrastructure.database;

import es.dtr.job.interview.commons.hexagonal.domain.repository.CrudDomainRepository;
import es.dtr.job.interview.commons.hexagonal.domain.service.querydsl.QueryDslFilter;
import jakarta.persistence.Id;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface CrudPortRepository<T, K, ID> extends CrudDomainRepository<T, ID> {

    // Dependencies
    CrudInfraRepository<K, ID> getCrudInfraRepository();

    CrudRepositoryMapper<T, K> getRepositoryMapper();

    @Override
    default T get(final ID id) {
        final Optional<K> infraEntity = getCrudInfraRepository().findById(id);

        if (infraEntity.isEmpty()) {
            throw NOT_FOUND;
        } else {
            return getRepositoryMapper().entityInfraToEntityDomain(infraEntity.get());
        }
    }

    @Override
    default List<T> findBy(final List<QueryDslFilter> filters, final Pageable pageable) {
        List<K> result;

        if (CollectionUtils.isEmpty(filters)) {
            final Page<K> page = getCrudInfraRepository().findAll(pageable);
            result = page.getContent();
        } else {
            result = getCrudInfraRepository().findAll(filters, pageable);
        }

        return getRepositoryMapper().entityInfraToEntityDomain(result);
    }

    @Override
    default T create(final T createRequest) {
        final K infraEntity = getRepositoryMapper().entityDomainToEntityInfra(createRequest);
        final K elementCreated = getCrudInfraRepository().save(infraEntity);
        return getRepositoryMapper().entityInfraToEntityDomain(elementCreated);
    }

    @Override
    default T update(final ID id, final T updateRequest) {
        final Optional<K> existingEntity = getCrudInfraRepository().findById(id);

        if (existingEntity.isEmpty()) {
            throw NOT_FOUND;
        } else {
            K elementToUpdate = getRepositoryMapper().entityDomainToEntityInfra(updateRequest);

            if (elementToUpdate instanceof MergeableEntity) {
                elementToUpdate = ((MergeableEntity<K>) existingEntity.get()).merge(elementToUpdate);
            }

            // Search id from element to update
            try {
                final Field fieldId = getIdFieldFromEntity(elementToUpdate);
                fieldId.setAccessible(true);
                fieldId.set(elementToUpdate, id);
                getCrudInfraRepository().save(elementToUpdate);
                return getRepositoryMapper().entityInfraToEntityDomain(elementToUpdate);
            } catch (final IllegalAccessException e) {
                throw new RepositoryInfraException(e, "Error accessing to field ID.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

    @Override
    default void delete(final ID id) {
        if (!getCrudInfraRepository().existsById(id)) {
            throw NOT_FOUND;
        } else {
            getCrudInfraRepository().deleteById(id);
        }
    }

    private Field getIdFieldFromEntity(final K entity) {
        Field result;

        final Optional<Field> mayFieldId = Arrays.stream(entity.getClass().getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(Id.class))
                .findFirst();

        if (mayFieldId.isEmpty()) {
            throw new RepositoryInfraException(
                    "Element ID to update couldn't be found, it's needed to annotate entity ID field with @Id.",
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        } else {
            result = mayFieldId.get();
        }

        return result;
    }
}
