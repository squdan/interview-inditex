package es.dtr.job.interview.commons.hexagonal.application.rest.crud;

import es.dtr.job.interview.commons.hexagonal.application.rest.ApiConfiguration;
import es.dtr.job.interview.commons.hexagonal.domain.service.crud.CrudService;
import es.dtr.job.interview.commons.hexagonal.domain.service.querydsl.QueryDslFilter;
import es.dtr.job.interview.commons.hexagonal.domain.service.querydsl.QueryDslUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

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
public interface CrudFindController<T, K, ID> {

    // Constants
    String QUERY_FILTER_PARAM = "filters";
    String ENDPOINT_FIND_BY = "";

    // Dependencies
    QueryDslUtils QUERY_DSL_UTILS = new QueryDslUtils();

    CrudService<K, ID> getCrudService();

    CrudControllerMapper<T, K> getMapper();

    @Operation(summary = "Search elements using received filters.", security = @SecurityRequirement(name = ApiConfiguration.API_SECURITY_NAME))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Elements found.", content = {
                    @Content(mediaType = "application/json", array = @ArraySchema(arraySchema = @Schema()))}),
            @ApiResponse(responseCode = "403", description = "Authentication required.", content = @Content)
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = CrudFindController.ENDPOINT_FIND_BY, produces = MediaType.APPLICATION_JSON_VALUE)
    default ResponseEntity<List<T>> findBy(
            @Schema(
                    description = "Filters to apply into the search. Example: username=user, isNull(username)",
                    defaultValue = "role=ADMIN, contains(username, admi)"
            )
            @RequestParam(value = QUERY_FILTER_PARAM, required = false) final List<String> filters, final Pageable pageable) {
        final List<QueryDslFilter> queryDslFilters = QUERY_DSL_UTILS.prepareFilters(filters);
        final List<K> elements = getCrudService().findBy(queryDslFilters, pageable);
        final List<T> result = getMapper().domainEntityToDto(elements);
        return ResponseEntity.ok().body(result);
    }

}
