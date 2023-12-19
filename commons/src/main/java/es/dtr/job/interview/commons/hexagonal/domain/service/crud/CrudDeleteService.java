package es.dtr.job.interview.commons.hexagonal.domain.service.crud;

import es.dtr.job.interview.commons.hexagonal.domain.repository.CrudDomainRepository;

/**
 * Interface to provide default CRUD DELETE services implementations.
 *
 * @param <T>  Entity.
 * @param <ID> Entity id.
 */
public interface CrudDeleteService<T, ID> {

    // Dependencies
    CrudDomainRepository<T, ID> getRepository();

    default void delete(final ID id) {
        getRepository().delete(id);
    }
}
