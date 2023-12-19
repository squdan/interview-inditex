package es.dtr.job.interview.commons.hexagonal.domain.entity.type.email;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Email type database wrapper.
 */
@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class EmailDatabase {

    // Email completo con que se guardará en BBDD (ejm: test@nosolosoftware.es)
    @Column(name = "email")
    @jakarta.validation.constraints.Email
    protected String address;

    @Override
    public String toString() {
        return this.address;
    }
}
