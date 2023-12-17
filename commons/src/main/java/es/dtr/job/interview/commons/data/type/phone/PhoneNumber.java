package es.dtr.job.interview.commons.data.type.phone;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import es.dtr.job.interview.commons.error.GenericException;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.Optional;

/**
 * Objeto que contiene información extendida del número de teléfono.
 * <p>
 * countryConfiguration: Enum con la información del pais al que pertenece el
 * número.
 * <p>
 * phoneNumber: Número de teléfono sin el prefijo (666777888).
 * <p>
 * fullPhoneNumber: Número de teléfono con el prefijo (+34666777888).
 */
@Slf4j
@Getter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PhoneNumber extends PhoneNumberDatabase {

    // Datos del número de teléfono que no se persistiran en BBDD
    private InternationalPhoneConfiguration countryConfiguration;
    private Long number;

    public PhoneNumber(final String fullPhoneNumber) {
        try {
            if (Objects.nonNull(fullPhoneNumber)) {
                final String fullPhoneNumberWithouSpaces = fullPhoneNumber.replaceAll("\\s+", StringUtils.EMPTY);

                // Utilizamos la librería de google para reconocer y validar el número de
                // teléfono
                final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
                final Phonenumber.PhoneNumber phone = phoneNumberUtil.parse(fullPhoneNumberWithouSpaces,
                        Phonenumber.PhoneNumber.CountryCodeSource.UNSPECIFIED.name());
                Assert.isTrue(phoneNumberUtil.isValidNumber(phone),
                        String.format("El número de teléfono %s no es válido.", fullPhoneNumberWithouSpaces));

                // Rellenamos la configuración del número de teléfono
                final int countryPrefix = phone.getCountryCode();
                final long phoneNumber = phone.getNationalNumber();

                final Optional<InternationalPhoneConfiguration> mayPhoneCountryConfig = InternationalPhoneConfiguration
                        .fromCountryPrefix(countryPrefix);
                Assert.isTrue(mayPhoneCountryConfig.isPresent(),
                        "No se pudo detectar la configuración del país para el número de teléfono.");

                // Validamos la configuración
                countryValidations(mayPhoneCountryConfig.get(), phoneNumber);

                // Validamos que el número de teléfono que construimos sea igual
                final String fullPhoneNumberBuild = buildFullPhoneNumberFromConfiguration(mayPhoneCountryConfig.get(),
                        phoneNumber);
                Assert.isTrue(fullPhoneNumberWithouSpaces.equals(fullPhoneNumberBuild),
                        String.format("Error en la construcción del número, no son iguales [%s <> %s].",
                                fullPhoneNumberWithouSpaces, fullPhoneNumberBuild));

                // Construimos el objeto
                this.countryConfiguration = mayPhoneCountryConfig.get();
                this.number = phoneNumber;
                this.fullPhoneNumber = fullPhoneNumberWithouSpaces;
            }
        } catch (final NumberParseException e) {
            log.error("Error al procesar el número de teléfono {}. Error: ", fullPhoneNumber, e);
            throw new GenericException(e, String.format("El número de teléfono [%s] no es válido.", fullPhoneNumber), HttpStatus.BAD_REQUEST);
        }
    }

    public PhoneNumber(final InternationalPhoneConfiguration countryConfiguration, final Long number) {
        countryValidations(countryConfiguration, number);
        this.countryConfiguration = countryConfiguration;
        this.number = number;
        this.fullPhoneNumber = buildFullPhoneNumberFromConfiguration(countryConfiguration, number);
    }

    private void countryValidations(final InternationalPhoneConfiguration countryConfiguration,
                                    final Long phoneNumber) {
        Assert.notNull(countryConfiguration,
                "No se pudo detectar la configuración del país para el número de teléfono.");
        Assert.notNull(phoneNumber, "No se pudo detectar el número de teléfono.");

        // Validación de la longitud del número
        final long phoneNumberLength = String.valueOf(phoneNumber).length();

        if (Objects.nonNull(countryConfiguration.getMinNumberDigits())) {
            Assert.isTrue(phoneNumberLength >= countryConfiguration.getMinNumberDigits(), "La longitud no es válida.");
        }

        if (Objects.nonNull(countryConfiguration.getMaxNumberDigits())) {
            Assert.isTrue(phoneNumberLength <= countryConfiguration.getMaxNumberDigits(), "La longitud no es válida.");
        }
    }

    private String buildFullPhoneNumberFromConfiguration(final InternationalPhoneConfiguration countryConfiguration,
                                                         final Long phoneNumber) {
        return "+" + countryConfiguration.getPrefix() + phoneNumber;
    }
}
