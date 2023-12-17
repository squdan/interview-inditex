package es.dtr.job.interview.commons.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import es.dtr.job.interview.commons.error.GenericException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Service
public class JwtService {

    // Properties
    private final Duration expiration;
    private final Algorithm hmac512;
    private final JWTVerifier verifier;

    public JwtService(@Value("${app.security.secret:defaultSecret}") final String secret,
                      @Value("${app.security.expiration:}") final Duration expiration) {

        if (Objects.isNull(expiration)) {
            this.expiration = Duration.of(20L, ChronoUnit.MINUTES);
        } else {
            this.expiration = expiration;
        }

        this.hmac512 = Algorithm.HMAC512(secret);
        this.verifier = JWT.require(this.hmac512).build();
    }

    public void validate(final String jwt) {
        try {
            verifier.verify(jwt);
        } catch (final JWTVerificationException e) {
            throw new GenericException(e, "Invalid JWT. Verification error.", HttpStatus.UNAUTHORIZED);
        }
    }

    public String getUsername(final String jwt) {
        try {
            return verifier.verify(jwt).getSubject();
        } catch (final JWTVerificationException e) {
            throw new GenericException(e, "Invalid JWT. Verification error.", HttpStatus.UNAUTHORIZED);
        }
    }

    public String generate(final UserDetails userDetails) {
        final Instant now = Instant.now();
        return JWT.create().withSubject(userDetails.getUsername()).withIssuer("app").withIssuedAt(now)
                .withExpiresAt(now.plusMillis(expiration.toMillis())).sign(this.hmac512);
    }

    public String generate(final AbstractAuthenticationToken authentication) {
        final Instant now = Instant.now();
        return JWT.create().withSubject(authentication.getName()).withIssuer("app").withIssuedAt(now)
                .withExpiresAt(now.plusMillis(expiration.toMillis())).sign(this.hmac512);
    }

}
