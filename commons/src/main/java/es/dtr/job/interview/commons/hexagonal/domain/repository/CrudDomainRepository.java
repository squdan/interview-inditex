package es.dtr.job.interview.commons.hexagonal.domain.repository;

import es.dtr.job.interview.commons.hexagonal.domain.service.querydsl.QueryDslFilter;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import java.util.List;

public interface CrudDomainRepository<T, ID> {

    // Constants
    RepositoryDomainException NOT_FOUND = new RepositoryDomainException("Element not found.", HttpStatus.NOT_FOUND);

    T get(ID id);

    List<T> findBy(List<QueryDslFilter> filters, Pageable pageable);

    ID create(T createRequest);

    void update(ID id, T updateRequest);

    void delete(ID id);

}
