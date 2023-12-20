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

    default T update(final ID id, final T updateRequest) {
        return getRepository().update(id, updateRequest);
    }
}
