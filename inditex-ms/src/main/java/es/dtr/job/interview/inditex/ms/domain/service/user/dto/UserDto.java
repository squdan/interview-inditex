package es.dtr.job.interview.inditex.ms.domain.service.user.dto;

import es.dtr.job.interview.commons.data.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private UUID id;
    private String username;
    private Roles role;
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private Instant createdOn;
    private Instant lastUpdatedOn;

}
