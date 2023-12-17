package es.dtr.job.interview.inditex.ms.adapter.in.authentication;

import es.dtr.job.interview.inditex.ms.adapter.in.authentication.dto.AuthenticationRequest;
import es.dtr.job.interview.inditex.ms.adapter.in.authentication.dto.AuthenticationResponse;
import es.dtr.job.interview.inditex.ms.configuration.security.AuthenticationManagerCustom;
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
