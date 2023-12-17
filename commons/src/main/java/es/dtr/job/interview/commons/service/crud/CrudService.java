package es.dtr.job.interview.commons.service.crud;

import es.dtr.job.interview.commons.service.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;

/**
 * Interface to provide default CRUD services implementations.
 *
 * @param <T>  DTO.
 * @param <K>  Entity.
 * @param <ID> Entity id.
 */
@Validated
public interface CrudService<T, K, ID> extends
        CrudGetService<T, K, ID>,
        CrudFindService<T, K, ID>,
        CrudCreateService<T, K, ID>,
        CrudUpdateService<T, K, ID>,
        CrudDeleteService<T, K, ID> {

    ServiceException NOT_FOUND = new ServiceException("Element not found.", HttpStatus.NOT_FOUND);
    ServiceException NOT_IMPLEMENTED = new ServiceException("Not implemented.", HttpStatus.INTERNAL_SERVER_ERROR);
}
