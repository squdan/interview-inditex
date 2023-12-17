package es.dtr.job.interview.commons.data.type.phone;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * Configuración de los números de telefonos a nivel internacional.
 * <p>
 * Referencia para añadir nuevos: https://www.123viajando.com/prefijos-paises/
 */
@Getter
public enum InternationalPhoneConfiguration {
	SPAIN("España", 34, 9, 9);

	private final String country;
	private final int prefix;
	private final Integer minNumberDigits;
	private final Integer maxNumberDigits;

	InternationalPhoneConfiguration(String country, int prefix, Integer minNumberDigits, Integer maxNumberDigits) {
		this.country = country;
		this.prefix = prefix;
		this.minNumberDigits = minNumberDigits;
		this.maxNumberDigits = maxNumberDigits;
	}

	public static Optional<InternationalPhoneConfiguration> fromCountryPrefix(int prefix) {
		return Arrays.stream(InternationalPhoneConfiguration.values()).filter(config -> (config.prefix == prefix))
				.findFirst();
	}

	@Override
	public String toString() {
		return String.format("[+%s] %s", this.getPrefix(), this.getCountry());
	}
}
