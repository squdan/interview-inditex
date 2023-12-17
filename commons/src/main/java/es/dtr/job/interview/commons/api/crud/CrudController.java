package es.dtr.job.interview.commons.api.crud;

/**
 * This interface will generate CRUD REST DELETE services for selected resource.
 * <p>
 * Note: This generated CRUD SERVICES just require user authentication, not a specific user ROLE, if you require
 * to limit users access by role, define manually your service and use @PreAuthorize("hasRole('XXX')").
 *
 * @param <T> DTO.
 * @param <K> Entity.
 * @param <ID> ID type.
 */
public interface CrudController<T, K, ID> extends
        CrudGetController<T, K, ID>,
        CrudFindController<T, K, ID>,
        CrudCreateController<T, K, ID>,
        CrudUpdateController<T, K, ID>,
        CrudDeleteController<T, K, ID> {

}
