package es.dtr.job.interview.inditex.ms.infrastructure.configuration.security;

import es.dtr.job.interview.commons.hexagonal.infrastructure.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AuthenticationManagerCustom implements AuthenticationManager {

    // Dependencies
    private final ApplicationContext context;
    private final JwtService jwtUtils;
    private final UserDetailsService userDetailsService;

    // Dependencia cíclica, realizamos una instanciación lazy para que no de error
    private PasswordEncoder passwordEncoder;

    private void instancePasswordEncoder() {
        if (Objects.isNull(passwordEncoder)) {
            this.passwordEncoder = context.getBean(PasswordEncoder.class);
        }
    }

    public UserDetails validate(final String jwt) {
        // Extracts required information from JWT
        final String username = jwtUtils.getUsername(jwt);

        // Loads user information
        return userDetailsService.loadUserByUsername(username);
    }

    public AuthenticationResponse authenticate(final String username, final String password) {
        // Si no se ha instanciado aún el PasswordEncoder solicitamos su instanciación
        instancePasswordEncoder();

        // Loads User Information
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Comprobamos si la contraseña recibida coincide con la del usuario
        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            // Authenticates into the application
            AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                    userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generates the JWT
            final String jwt = jwtUtils.generate(userDetails);

            // Generates the response
            return AuthenticationResponse.builder().username(username).jwt(jwt).build();
        } else {
            throw new SessionException("Contraseña errónea.", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
        throw new SessionException("Authentication manager - authenticate not implemented.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
