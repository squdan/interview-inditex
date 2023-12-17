package es.dtr.job.interview.inditex.ms.adapter.in.user;

import es.dtr.job.interview.commons.api.ApiConfiguration;
import es.dtr.job.interview.commons.api.crud.*;
import es.dtr.job.interview.inditex.ms.configuration.OpenApiConfiguration;
import es.dtr.job.interview.inditex.ms.domain.entity.UserEntity;
import es.dtr.job.interview.inditex.ms.domain.service.user.dto.PasswordUpdateDto;
import es.dtr.job.interview.inditex.ms.domain.service.user.dto.UserDto;
import es.dtr.job.interview.inditex.ms.domain.service.user.dto.UserRegisterRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Validated
@Tag(name = UserControllerInterface.API_RESOURCE)
@RequestMapping(UserControllerInterface.BASE_PATH)
public interface UserControllerInterface extends CrudGetController<UserDto, UserEntity, UUID>,
        CrudFindController<UserDto, UserEntity, UUID>,
        CrudUpdateController<UserDto, UserEntity, UUID>,
        CrudDeleteController<UserDto, UserEntity, UUID> {

    // Paths
    String API_RESOURCE = "users";
    String BASE_PATH = OpenApiConfiguration.API_PATH + "/" + API_RESOURCE;
    String ENDPOINT_UPDATE_PASSWORD = "/{id}";

    @Operation(summary = "Create a new user.", security = @SecurityRequirement(name = ApiConfiguration.API_SECURITY_NAME))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Element created successfully.", content = {
                    @Content(mediaType = "application/json", schema = @Schema())}),
            @ApiResponse(responseCode = "403", description = "Authentication required.", content = @Content)
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = CrudCreateController.ENDPOINT_CREATE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UUID> create(@NotNull @Valid @RequestBody UserRegisterRequest createRequest);

    @Operation(summary = "Update user pasword.", security = @SecurityRequirement(name = ApiConfiguration.API_SECURITY_NAME))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Password updated successfully.", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found.", content = @Content)})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = UserControllerInterface.ENDPOINT_UPDATE_PASSWORD, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> updatePassword(@NotNull @Valid @PathVariable UUID id,
                                        @NotNull @Valid @RequestBody PasswordUpdateDto passwordChangeRequest);
}
