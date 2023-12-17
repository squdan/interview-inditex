package es.dtr.job.interview.commons.api.crud;

import es.dtr.job.interview.commons.api.ApiConfiguration;
import es.dtr.job.interview.commons.service.crud.CrudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
public interface CrudCreateController<T, K, ID> {

    // Constants
    String ENDPOINT_CREATE = "";

    // Dependencies
    CrudService<T, K, ID> getCrudService();

    @Operation(summary = "Create a new element.", security = @SecurityRequirement(name = ApiConfiguration.API_SECURITY_NAME))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Element created successfully.", content = {
                    @Content(mediaType = "application/json", schema = @Schema())}),
            @ApiResponse(responseCode = "403", description = "Authentication required.", content = @Content)
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = CrudCreateController.ENDPOINT_CREATE, produces = MediaType.APPLICATION_JSON_VALUE)
    default ResponseEntity<ID> create(@NotNull @Valid @RequestBody final T createRequest) {
        final ID createdId = getCrudService().create(createRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdId);
    }

}
