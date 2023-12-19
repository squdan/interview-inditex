package es.dtr.job.interview.inditex.ms.application.rest.authentication;

import es.dtr.job.interview.inditex.ms.infrastructure.configuration.security.AuthenticationResponse;
import es.dtr.job.interview.inditex.ms.infrastructure.configuration.security.AuthenticationManagerCustom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class LoginController implements LoginControllerInterface {

    // Dependencies
    private final AuthenticationManagerCustom authenticationManager;

    public AuthenticationResponse login(final AuthenticationRequest body) {
        log.info("Login user [{}].", body.getUsername());
        return authenticationManager.authenticate(body.getUsername(), body.getPassword());
    }
}
