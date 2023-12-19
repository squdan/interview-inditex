package es.dtr.job.interview.inditex.ms.application.rest.user.dto;

import es.dtr.job.interview.commons.hexagonal.domain.entity.type.Roles;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {

    @NotNull(message = "El rol del usuario no es válido.")
    @Schema(description = "Rol del usuario a crear.", defaultValue = "USER")
    private Roles role;

    @NotBlank(message = "El username del usuario no es válido.")
    @Schema(description = "Nombre de usuario.", defaultValue = "username")
    private String username;

    @NotBlank(message = "La contraseña del usuario no es válida.")
    @Schema(description = "Contraseña de usuario.", defaultValue = "password")
    private String password;

    @NotBlank(message = "El nombre de la persona a registrar no es válido.")
    @Schema(description = "Nombre de la persona.", defaultValue = "nombre")
    private String name;

    @NotBlank(message = "Los apellidos del usuario no son válidos.")
    @Schema(description = "Apellidos de la persona.", defaultValue = "apellido1 apellido2")
    private String lastName;

    @NotBlank(message = "El correo del usuario no es válido.")
    @Schema(description = "Correo electrónico.", defaultValue = "test@gmail.com")
    private String email;

    @NotBlank(message = "El número de teléfono del usuario no es válido.")
    @Schema(description = "Número de teléfono.", defaultValue = "+34666777888")
    private String phone;

}
