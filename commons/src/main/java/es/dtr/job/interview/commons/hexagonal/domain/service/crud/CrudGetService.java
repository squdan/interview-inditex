package es.dtr.job.interview.commons.hexagonal.domain.service.crud;

import es.dtr.job.interview.commons.hexagonal.domain.repository.CrudDomainRepository;

/**
 * Interface to provide default CRUD GET services implementations.
 *
 * @param <T>  Entity.
 * @param <ID> Entity id.
 */
public interface CrudGetService<T, ID> {

    // Dependencies
    CrudDomainRepository<T, ID> getRepository();

    default T get(final ID id) {
        return getRepository().get(id);
    }
}
