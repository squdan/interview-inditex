package es.dtr.job.interview.commons.hexagonal.application.rest.crud;

import es.dtr.job.interview.commons.hexagonal.application.rest.ApiConfiguration;
import es.dtr.job.interview.commons.hexagonal.domain.service.crud.CrudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

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
public interface CrudGetController<T, K, ID> {

    // Constants
    String ENDPOINT_GET = "/{id}";

    // Dependencies
    CrudService<K, ID> getCrudService();

    CrudControllerMapper<T, K> getMapper();

    @Operation(summary = "Get element by ID.", security = @SecurityRequirement(name = ApiConfiguration.API_SECURITY_NAME))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Element found.", content = {
                    @Content(mediaType = "application/json", schema = @Schema())}),
            @ApiResponse(responseCode = "403", description = "Authentication required.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Element not found.", content = @Content)
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = CrudGetController.ENDPOINT_GET, produces = MediaType.APPLICATION_JSON_VALUE)
    default ResponseEntity<T> get(@NotNull @PathVariable final ID id) {
        final K element = getCrudService().get(id);
        final T result = getMapper().domainEntityToDto(element);
        return ResponseEntity.ok().body(result);
    }

}
