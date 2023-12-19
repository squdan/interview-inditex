package es.dtr.job.interview.inditex.ms.infrastructure.database.user;

import es.dtr.job.interview.commons.hexagonal.domain.entity.type.Roles;
import es.dtr.job.interview.commons.hexagonal.domain.entity.type.email.Email;
import es.dtr.job.interview.commons.hexagonal.domain.entity.type.email.EmailDatabase;
import es.dtr.job.interview.commons.hexagonal.domain.entity.type.password.PasswordDatabaseConverter;
import es.dtr.job.interview.commons.hexagonal.domain.entity.type.phone.PhoneNumber;
import es.dtr.job.interview.commons.hexagonal.domain.entity.type.phone.PhoneNumberDatabase;
import es.dtr.job.interview.commons.hexagonal.infrastructure.database.MergeableEntity;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * Entity to represent users registered into the application.
 */
@Data
@Entity
@Builder
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class UserInfraEntity implements MergeableEntity<UserInfraEntity> {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Roles role;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastName;

    @Embedded
    @EqualsAndHashCode.Exclude
    private EmailDatabase email;

    @Embedded
    @EqualsAndHashCode.Exclude
    private PhoneNumberDatabase phone;

    @Column(nullable = false)
    @Convert(converter = PasswordDatabaseConverter.class)
    private String password;

    @EqualsAndHashCode.Include
    private String email() {
        return Objects.nonNull(this.email) ? email.toString() : null;
    }

    public Email getEmail() {
        // En este caso no podemos automatizar la conversión con "@Convert(converter =
        // EmailJPAConverter.class)"
        // porque el campo tiene que estar anotado con @Embedded para que QueryDsl
        // detecte el campo "address" de su interior
        // y con ello poder realizar filtrados de usuarios por su email.
        if (Objects.nonNull(this.email) && !(this.email instanceof Email)) {
            this.email = new Email(this.email.getAddress());
        }

        return Objects.nonNull(this.email) ? (Email) this.email : null;
    }

    public void setEmail(final String value) {
        this.email = new Email(value);
    }

    @EqualsAndHashCode.Include
    private String phoneNumber() {
        return Objects.nonNull(this.phone) ? phone.toString() : null;
    }

    public PhoneNumber getPhoneNumber() {
        // En este caso no podemos automatizar la conversión con "@Convert(converter =
        // EmailJPAConverter.class)"
        // porque el campo tiene que estar anotado con @Embedded para que QueryDsl
        // detecte el campo "address" de su interior
        // y con ello poder realizar filtrados de usuarios por su email.
        if (Objects.nonNull(this.phone) && !(this.phone instanceof PhoneNumber)) {
            this.phone = new PhoneNumber(this.phone.getFullPhoneNumber());
        }

        return Objects.nonNull(this.phone) ? (PhoneNumber) this.phone : null;
    }

    public void setPhoneNumber(final String value) {
        this.phone = new PhoneNumber(value);
    }

    /**
     * Creation date: managed by hibernate
     */
    @CreationTimestamp(source = SourceType.DB)
    private Instant createdOn;

    /**
     * Last update date: managed by hibernate
     */
    @UpdateTimestamp(source = SourceType.DB)
    private Instant lastUpdatedOn;

    public UserInfraEntity merge(final UserInfraEntity changes) {
        if (Objects.nonNull(changes.getRole())) {
            this.role = changes.getRole();
        }

        if (StringUtils.isNotBlank(changes.getUsername())) {
            this.username = changes.getUsername();
        }

        if (StringUtils.isNotBlank(changes.getPassword())) {
            this.password = changes.getPassword();
        }

        if (StringUtils.isNotBlank(changes.getName())) {
            this.name = changes.getName();
        }

        if (StringUtils.isNotBlank(changes.getLastName())) {
            this.lastName = changes.getLastName();
        }

        if (Objects.nonNull(changes.getEmail())) {
            this.email = changes.getEmail();
        }

        if (Objects.nonNull(changes.getPhone())) {
            this.phone = changes.getPhone();
        }

        return this;
    }
}
