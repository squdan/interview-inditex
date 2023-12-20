package es.dtr.job.interview.inditex.ms.application.rest.user;

import es.dtr.job.interview.commons.hexagonal.application.rest.ApiConfiguration;
import es.dtr.job.interview.commons.hexagonal.application.rest.crud.*;
import es.dtr.job.interview.inditex.ms.application.rest.user.dto.UserDto;
import es.dtr.job.interview.inditex.ms.application.rest.user.dto.UserRegisterRequest;
import es.dtr.job.interview.inditex.ms.domain.entity.UserEntity;
import es.dtr.job.interview.inditex.ms.domain.service.user.PasswordUpdateDto;
import es.dtr.job.interview.inditex.ms.infrastructure.configuration.OpenApiConfiguration;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Validated
@Tag(name = UserControllerInterface.API_RESOURCE)
@RequestMapping(UserControllerInterface.BASE_PATH)
public interface UserControllerInterface extends
        CrudGetController<UserDto, UserEntity, UUID>,
        CrudFindController<UserDto, UserEntity, UUID>,
        CrudUpdateController<UserDto, UserEntity, UUID>,
        CrudDeleteController<UserDto, UserEntity, UUID> {

    // Paths
    String API_RESOURCE = "users";
    String BASE_PATH = OpenApiConfiguration.API_PATH + "/" + API_RESOURCE;
    String ENDPOINT_UPDATE_PASSWORD = "/{id}";

    // Dependencies
    Class<? extends UserControllerInterface> getCrudController();

    default Link[] getHateoas(final UUID id) {
        List<Link> result = List.of(
                getHateoasGet(id),
                getHateoasFind(),
                WebMvcLinkBuilder
                        .linkTo(
                                WebMvcLinkBuilder
                                        .methodOn(getCrudController())
                                        .create(null))
                        .withRel("create"),
                getHateoasUpdate(id),
                WebMvcLinkBuilder
                        .linkTo(
                                WebMvcLinkBuilder
                                        .methodOn(getCrudController())
                                        .updatePassword(id, null))
                        .withRel("password-update"),
                getHateoasDelete(id)
        );
        return result.toArray(new Link[]{});
    }

    @Operation(summary = "Create a new user.", security = @SecurityRequirement(name = ApiConfiguration.API_SECURITY_NAME))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Element created successfully.", content = {
                    @Content(mediaType = "application/json", schema = @Schema())}),
            @ApiResponse(responseCode = "403", description = "Authentication required.", content = @Content)
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = CrudCreateController.ENDPOINT_CREATE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<EntityModel<UserDto>> create(@NotNull @Valid @RequestBody UserRegisterRequest createRequest);

    @Operation(summary = "Update user pasword.", security = @SecurityRequirement(name = ApiConfiguration.API_SECURITY_NAME))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Password updated successfully.", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found.", content = @Content)})
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(value = UserControllerInterface.ENDPOINT_UPDATE_PASSWORD, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<EntityModel<UserDto>> updatePassword(@NotNull @Valid @PathVariable UUID id,
                                                        @NotNull @Valid @RequestBody PasswordUpdateDto passwordChangeRequest);
}
