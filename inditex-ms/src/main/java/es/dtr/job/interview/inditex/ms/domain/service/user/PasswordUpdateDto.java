package es.dtr.job.interview.inditex.ms.domain.service.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PasswordUpdateDto {

    private String oldPassword;

    private String newPassword;

}
