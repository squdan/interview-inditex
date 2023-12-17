package es.dtr.job.interview.inditex.ms;

import es.dtr.job.interview.commons.TestData;
import es.dtr.job.interview.commons.data.Roles;
import es.dtr.job.interview.inditex.ms.configuration.security.UserDetailsCustom;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityTestUtils {

    public static void authenticateAsAdmin() {
        authenticate(Roles.ADMIN);
    }

    public static void authenticateAsUser() {
        authenticate(Roles.USER);
    }

    public static void authenticate(final Roles role) {
        final List<GrantedAuthority> userAuthorities = List.of(new SimpleGrantedAuthority(role.name()));
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        new UserDetailsCustom(
                                UUID.randomUUID(),
                                TestData.TEST_STRING,
                                TestData.TEST_STRING,
                                userAuthorities
                        ),
                        null,
                        userAuthorities
                )
        );
    }

}
