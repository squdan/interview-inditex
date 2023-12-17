package es.dtr.job.interview.commons.api.crud;

import es.dtr.job.interview.commons.api.ApiConfiguration;
import es.dtr.job.interview.commons.service.crud.CrudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
public interface CrudUpdateController<T, K, ID> {

    // Constants
    String ENDPOINT_UPDATE = "/{id}";

    // Dependencies
    CrudService<T, K, ID> getCrudService();

    @Operation(summary = "Update element information.", security = @SecurityRequirement(name = ApiConfiguration.API_SECURITY_NAME))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Information updated successfully.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Authentication required.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Element not found.", content = @Content)
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping(value = CrudUpdateController.ENDPOINT_UPDATE, produces = MediaType.APPLICATION_JSON_VALUE)
    default ResponseEntity<Void> update(@NotNull @PathVariable final ID id,
                                        @NotNull @Valid @RequestBody final T updateRequest) {
        getCrudService().update(id, updateRequest);
        return ResponseEntity.noContent().build();
    }

}
