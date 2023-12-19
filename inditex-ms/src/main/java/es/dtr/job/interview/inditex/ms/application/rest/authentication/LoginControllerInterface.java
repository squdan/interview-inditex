package es.dtr.job.interview.inditex.ms.application.rest.authentication;

import es.dtr.job.interview.inditex.ms.infrastructure.configuration.security.AuthenticationResponse;
import es.dtr.job.interview.inditex.ms.infrastructure.configuration.OpenApiConfiguration;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Validated
@Tag(name = LoginControllerInterface.API_RESOURCE)
@RequestMapping(LoginControllerInterface.BASE_PATH)
public interface LoginControllerInterface {

    // Paths
    String API_RESOURCE = "authentications";
    String BASE_PATH = OpenApiConfiguration.API_PATH + "/" + API_RESOURCE;
    String ENDPOINT_LOGIN = "";

    @Operation(summary = "Authenticates the user into the application with credentials.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the session information.", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = AuthenticationResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Wrong credentials.", content = @Content),
            @ApiResponse(responseCode = "500", description = "An internal error happened.", content = @Content)})
    @PostMapping(value = LoginControllerInterface.ENDPOINT_LOGIN, produces = {MediaType.APPLICATION_JSON_VALUE})
    AuthenticationResponse login(@Valid @RequestBody AuthenticationRequest body);

}
