package es.dtr.job.interview.commons.hexagonal.domain.service.crud;

import es.dtr.job.interview.commons.hexagonal.domain.service.ServiceException;
import org.springframework.http.HttpStatus;

/**
 * Interface to provide default CRUD services implementations.
 *
 * @param <T>  Entity.
 * @param <ID> Entity id.
 */
public interface CrudService<T, ID> extends
        CrudGetService<T, ID>,
        CrudFindService<T, ID>,
        CrudCreateService<T, ID>,
        CrudUpdateService<T, ID>,
        CrudDeleteService<T, ID> {

    ServiceException NOT_FOUND = new ServiceException("Element not found.", HttpStatus.NOT_FOUND);
    ServiceException NOT_IMPLEMENTED = new ServiceException("Not implemented.", HttpStatus.INTERNAL_SERVER_ERROR);
}
