package es.dtr.job.interview.commons.hexagonal.domain.service.crud;

import es.dtr.job.interview.commons.hexagonal.domain.repository.CrudDomainRepository;

/**
 * Interface to provide default CRUD CREATE services implementations.
 *
 * @param <T>  Entity.
 * @param <ID> Entity id.
 */
public interface CrudCreateService<T, ID> {
    // Dependencies
    CrudDomainRepository<T, ID> getRepository();

    default ID create(final T createRequest) {
        return getRepository().create(createRequest);
    }
}
