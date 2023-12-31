package es.dtr.job.interview.commons.hexagonal.application.rest.crud;

import es.dtr.job.interview.commons.hexagonal.application.rest.ApiConfiguration;
import es.dtr.job.interview.commons.hexagonal.domain.service.crud.CrudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
public interface CrudUpdateController<T extends CrudElementDto<T, ID>, K, ID> {

    // Constants
    String ENDPOINT_UPDATE = "/{id}";

    // Dependencies
    Link[] getHateoas(ID id);

    Class<? extends CrudUpdateController<T, K, ID>> getCrudController();

    CrudService<K, ID> getCrudService();

    CrudControllerMapper<T, K> getMapper();

    @Operation(summary = "Update element information.", security = @SecurityRequirement(name = ApiConfiguration.API_SECURITY_NAME))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Information updated successfully.", content = @Content),
            @ApiResponse(responseCode = "403", description = "Authentication required.", content = @Content),
            @ApiResponse(responseCode = "404", description = "Element not found.", content = @Content)
    })
    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = CrudUpdateController.ENDPOINT_UPDATE, produces = MediaType.APPLICATION_JSON_VALUE)
    default ResponseEntity<EntityModel<T>> update(@NotNull @PathVariable final ID id,
                                                  @NotNull @Valid @RequestBody final T updateRequest) {
        final K domainUpdateRequest = getMapper().dtoToDomainEntity(updateRequest);
        final K domainUpdateResponse = getCrudService().update(id, domainUpdateRequest);
        final T updateDtoResponse = getMapper().domainEntityToDto(domainUpdateResponse);
        return ResponseEntity.ok(EntityModel.of(updateDtoResponse, getHateoas(updateDtoResponse.getId())));
    }

    default Link getHateoasUpdate(final ID id) {
        return WebMvcLinkBuilder
                .linkTo(
                        WebMvcLinkBuilder
                                .methodOn(getCrudController())
                                .update(id, null))
                .withRel("update");
    }
}
