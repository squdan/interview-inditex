package es.dtr.job.interview.commons.hexagonal.application.rest.crud;

import es.dtr.job.interview.commons.hexagonal.application.rest.ApiConfiguration;
import es.dtr.job.interview.commons.hexagonal.domain.service.crud.CrudService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
import org.springframework.web.bind.annotation.PostMapping;
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
public interface CrudCreateController<T extends CrudElementDto<T, ID>, K, ID> {

    // Constants
    String ENDPOINT_CREATE = "";

    // Dependencies
    Link[] getHateoas(ID id);

    Class<? extends CrudCreateController<T, K, ID>> getCrudController();

    CrudService<K, ID> getCrudService();

    CrudControllerMapper<T, K> getMapper();

    @Operation(summary = "Create a new element.", security = @SecurityRequirement(name = ApiConfiguration.API_SECURITY_NAME))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Element created successfully.", content = {
                    @Content(mediaType = "application/json", schema = @Schema())}),
            @ApiResponse(responseCode = "403", description = "Authentication required.", content = @Content)
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = CrudCreateController.ENDPOINT_CREATE, produces = MediaType.APPLICATION_JSON_VALUE)
    default ResponseEntity<EntityModel<T>> create(@NotNull @Valid @RequestBody final T createRequest) {
        final K createDomainRequest = getMapper().dtoToDomainEntity(createRequest);
        final K createdDomainResponse = getCrudService().create(createDomainRequest);
        final T createdDtoResponse = getMapper().domainEntityToDto(createdDomainResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(EntityModel.of(createdDtoResponse, getHateoas(createdDtoResponse.getId())));
    }

    default Link getHateoasCreate() {
        return WebMvcLinkBuilder
                .linkTo(
                        WebMvcLinkBuilder
                                .methodOn(getCrudController())
                                .create(null))
                .withRel("create");
    }
}
