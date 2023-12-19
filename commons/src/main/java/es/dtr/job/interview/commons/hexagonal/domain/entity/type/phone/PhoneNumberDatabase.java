package es.dtr.job.interview.commons.hexagonal.domain.entity.type.phone;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Phone type database wrapper.
 */
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneNumberDatabase {

    // Número de teléfono completo con prefijo que se guardará en BBDD (ejm:
    // +34666777888)
    @Size(max = 64)
    @Column(name = "phone_number")
    protected String fullPhoneNumber;

    @Override
    public String toString() {
        return this.fullPhoneNumber;
    }
}
