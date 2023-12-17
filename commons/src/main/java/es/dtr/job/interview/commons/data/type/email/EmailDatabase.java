package es.dtr.job.interview.commons.data.type.email;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Este objeto se utilizará en las entidades para que hibernate conozca qué
 * campo es el que tiene que almacenar en base de datos.
 * <p>
 * Posteriormente, esta clase se podrá castear a {@link Email} que contiene la
 * información del email desglosada y validada.
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
