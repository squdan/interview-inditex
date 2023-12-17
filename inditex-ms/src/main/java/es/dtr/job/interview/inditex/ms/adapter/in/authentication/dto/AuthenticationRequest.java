package es.dtr.job.interview.inditex.ms.adapter.in.authentication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AuthenticationRequest {

    @NotBlank(message = "El username no es válido.")
    @Schema(description = "Nombre de usuario del administrador.", defaultValue = "username")
    String username;

    @NotBlank(message = "La contraseña no es válida.")
    @Schema(description = "Contraseña de usuario del administrador.", defaultValue = "password")
    String password;
}
