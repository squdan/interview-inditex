package es.dtr.job.interview.commons.hexagonal.domain.entity.type.email;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Email type wrapper.
 */
@Getter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Email extends EmailDatabase {

    // Expresión regular para validar email en formato String, ejemplo:
    // test@nosolosoftware.es
    public static final String EMAIL_REGEX = "([\\w.+-]+)\\@(\\w+)\\.(\\w+)";
    public static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    // Datos del email que no se persistiran en BBDD (test@nosolosoftware.es)
    private String username; // test
    private String domain; // nosolosoftware.es
    private String organization; // nosolosoftware
    private String type; // es

    public Email(final String email) {
        if (Objects.nonNull(email)) {
            final Matcher matcher = EMAIL_PATTERN.matcher(email);
            Assert.isTrue(matcher.find(), String.format("El email recibido [%s] no es válido.", email));
            Assert.isTrue(matcher.groupCount() == 3, String.format(
                    "El email recibido [%s] no es válido, el número de partes del mismo reconocidas != 3.", email));

            this.username = matcher.group(1);
            this.organization = matcher.group(2);
            this.type = matcher.group(3);
            this.domain = this.organization + '.' + this.type;
            this.address = email;
        }
    }
}
