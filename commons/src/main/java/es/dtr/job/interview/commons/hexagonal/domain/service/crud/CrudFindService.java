package es.dtr.job.interview.commons.hexagonal.domain.service.crud;

import es.dtr.job.interview.commons.hexagonal.domain.repository.CrudDomainRepository;
import es.dtr.job.interview.commons.hexagonal.domain.service.querydsl.QueryDslFilter;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Interface to provide default CRUD FIND BY services implementations.
 *
 * @param <T>  Entity.
 * @param <ID> Entity id.
 */
public interface CrudFindService<T, ID> {

    // Dependencies
    CrudDomainRepository<T, ID> getRepository();

    default List<T> findBy(final List<QueryDslFilter> filters, final Pageable pageable) {
        return getRepository().findBy(filters, pageable);
    }
}
