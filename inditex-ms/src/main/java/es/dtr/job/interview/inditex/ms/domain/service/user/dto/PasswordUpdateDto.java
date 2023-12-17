package es.dtr.job.interview.inditex.ms.domain.service.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Modelo que contendrá la información necesaria para el cambio del contraseña
 * de un usuario.
 *
 * @author Daniel Torres Rojano
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasswordUpdateDto {

    private String oldPassword;

    private String newPassword;

}
