package es.dtr.job.interview.commons.data.type.phone;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Este objeto se utilizará en las entidades para que hibernate conozca qué
 * campo es el que tiene que almacenar en base de datos.
 * <p>
 * Posteriormente, esta clase se podrá castear a {@link PhoneNumber} que
 * contiene la información del número de teléfono y pais desglosada y validada.
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
