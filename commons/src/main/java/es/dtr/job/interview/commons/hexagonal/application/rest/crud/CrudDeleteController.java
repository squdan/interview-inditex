package es.dtr.job.interview.commons.hexagonal.application.rest.crud;

import es.dtr.job.interview.commons.hexagonal.application.rest.ApiConfiguration;
import es.dtr.job.interview.commons.hexagonal.domain.service.crud.CrudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
public interface CrudDeleteController<T, K, ID> {

    // Constants
    String ENDPOINT_DELETE = "/{id}";

    // Dependencies
    CrudService<K, ID> getCrudService();

    @Operation(summary = "Delete selected element.", security = @SecurityRequirement(name = ApiConfiguration.API_SECURITY_NAME))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Element deleted successfully.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Authentication required.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Element not found.", content = @Content)
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = CrudDeleteController.ENDPOINT_DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    default ResponseEntity<Void> delete(@PathVariable ID id) {
        getCrudService().delete(id);
        return ResponseEntity.noContent().build();
    }

}
