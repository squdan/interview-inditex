package es.dtr.job.interview.commons.hexagonal.domain.service.crud;

import es.dtr.job.interview.commons.hexagonal.domain.repository.CrudDomainRepository;

/**
 * Interface to provide default CRUD UPDATE services implementations.
 *
 * @param <T>  Entity.
 * @param <ID> Entity id.
 */
public interface CrudUpdateService<T, ID> {

    // Dependencies
    CrudDomainRepository<T, ID> getRepository();

    default void update(final ID id, final T updateRequest) {
        getRepository().update(id, updateRequest);
    }
}
