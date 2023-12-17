package es.dtr.job.interview.commons.data.type.password;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;

/**
 * Conversor que aplicará en la lectura y escritura en base de datos para que en
 * lugar de guardarse la contraseña en texto plano, se almacene hasheada.
 * <p>
 * Para que funcione, hay que anotar el campo con:
 * {@link javax.persistence.Convert}
 * <p>
 * Ejemplo: @Convert(converter = PasswordDatabaseConverter.class)
 */
@Converter
@RequiredArgsConstructor
public class PasswordDatabaseConverter implements AttributeConverter<String, String> {

	// Dependencies
	private final ApplicationContext context;

	// Dependencia cíclica, realizamos una instanciación lazy para que no de error
	private PasswordEncoder passwordEncoder;

	private void instancePasswordEncoder() {
		if (Objects.isNull(passwordEncoder)) {
			this.passwordEncoder = context.getBean(PasswordEncoder.class);
		}
	}

	@Override
	public String convertToDatabaseColumn(final String source) {
		// Si no se ha instanciado aún el PasswordEncoder solicitamos su instanciación
		instancePasswordEncoder();

		return Objects.nonNull(source) ? passwordEncoder.encode(source) : null;
	}

	@Override
	public String convertToEntityAttribute(final String source) {
		return Objects.nonNull(source) ? source : null;
	}
}