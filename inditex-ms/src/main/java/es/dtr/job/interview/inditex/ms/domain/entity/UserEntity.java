package es.dtr.job.interview.inditex.ms.domain.entity;

import es.dtr.job.interview.commons.hexagonal.domain.entity.type.Roles;
import io.micrometer.common.util.StringUtils;
import lombok.*;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Entity to represent users registered into the application.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    private UUID id;
    private String username;
    private Roles role;
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private String password;
    private Instant createdOn;
    private Instant lastUpdatedOn;

}
