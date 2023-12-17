package es.dtr.job.interview.inditex.ms.adapter.in;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.dtr.job.interview.inditex.ms.TestDataIT;
import es.dtr.job.interview.inditex.ms.adapter.in.authentication.LoginController;
import es.dtr.job.interview.inditex.ms.adapter.in.authentication.dto.AuthenticationRequest;
import es.dtr.job.interview.inditex.ms.adapter.in.authentication.dto.AuthenticationResponse;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

public abstract class ControllerBaseIT {

    // Dependencies
    @Getter
    @Autowired
    protected ObjectMapper objectMapper;

    // Mock REST
    @Getter
    @Autowired
    protected MockMvc mockMvc;

    // Authentication
    @Autowired
    protected LoginController loginController;

    public AuthenticationResponse loginWithAdmin() {
        return this.login(AuthenticationRequest.builder().username(TestDataIT.USERNAME_ADMIN).password(TestDataIT.PASSWORD_OK).build());
    }

    public AuthenticationResponse login(final AuthenticationRequest authenticationRequest) {
        return loginController.login(authenticationRequest);
    }
}
