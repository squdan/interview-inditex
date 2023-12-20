package es.dtr.job.interview.commons.hexagonal.application.rest.crud;

import org.springframework.hateoas.Link;

import java.util.List;

/**
 * This interface will generate CRUD REST DELETE services for selected resource.
 * <p>
 * Note: This generated CRUD SERVICES just require user authentication, not a specific user ROLE, if you require
 * to limit users access by role, define manually your service and use @PreAuthorize("hasRole('XXX')").
 *
 * @param <T>  DTO.
 * @param <K>  Entity.
 * @param <ID> ID type.
 */
public interface CrudController<T extends CrudElementDto<T, ID>, K, ID> extends
        CrudGetController<T, K, ID>,
        CrudFindController<T, K, ID>,
        CrudCreateController<T, K, ID>,
        CrudUpdateController<T, K, ID>,
        CrudDeleteController<T, K, ID> {

    Class<? extends CrudController<T, K, ID>> getCrudController();

    default Link[] getHateoas(final ID id) {
        List<Link> result = List.of(
                getHateoasGet(id),
                getHateoasFind(),
                getHateoasCreate(),
                getHateoasUpdate(id),
                getHateoasDelete(id)
        );
        return result.toArray(new Link[]{});
    }
}
