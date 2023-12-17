package es.dtr.job.interview.inditex.ms.adapter.in.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.dtr.job.interview.commons.error.GenericException;
import es.dtr.job.interview.inditex.ms.adapter.in.authentication.dto.AuthenticationRequest;
import es.dtr.job.interview.inditex.ms.adapter.in.authentication.dto.AuthenticationResponse;
import es.dtr.job.interview.inditex.ms.configuration.security.AuthenticationManagerCustom;
import es.dtr.job.interview.inditex.ms.configuration.security.SessionException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = LoginController.class)
@AutoConfigureMockMvc(addFilters = false)
class LoginControllerTest {

    // Data
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String JWT = "jwt";
    private static final GenericException NOT_FOUND = new SessionException("El usuario no existe.", HttpStatus.NOT_FOUND);

    // Dependencies
    @Autowired
    protected ObjectMapper objectMapper;

    // Mock rest
    @Autowired
    protected MockMvc mockMvc;

    // Mocks
    @MockBean
    private AuthenticationManagerCustom authenticationManagerCustom;

    @Test
    void test_login_whenNoExists_then404() throws Exception {
        // Test data
        final AuthenticationRequest authenticationRequest = AuthenticationRequest.builder().username(USERNAME).password(PASSWORD).build();

        // Mocks
        Mockito
                .when(authenticationManagerCustom.authenticate(USERNAME, PASSWORD))
                .thenThrow(NOT_FOUND);

        // Test execution
        final ResultActions restResponse = mockMvc.perform(
                MockMvcRequestBuilders
                        .post(LoginControllerInterface.BASE_PATH + LoginControllerInterface.ENDPOINT_LOGIN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authenticationRequest))
        );

        // Response validation
        restResponse
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpCode", Matchers.equalTo(HttpStatus.NOT_FOUND.name())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.equalTo(NOT_FOUND.getError().getMessage())));
    }

    @Test
    void test_login_whenExists_thenReturnJwt() throws Exception {
        // Test data
        final AuthenticationRequest authenticationRequest = AuthenticationRequest.builder().username(USERNAME).password(PASSWORD).build();
        final AuthenticationResponse authenticationResponse = AuthenticationResponse.builder().username(USERNAME).jwt(JWT).build();

        // Mocks
        Mockito
                .when(authenticationManagerCustom.authenticate(USERNAME, PASSWORD))
                .thenReturn(authenticationResponse);

        // Test execution
        final ResultActions restResponse = mockMvc.perform(
                MockMvcRequestBuilders
                        .post(LoginControllerInterface.BASE_PATH + LoginControllerInterface.ENDPOINT_LOGIN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authenticationRequest))
        );

        // Response validation
        restResponse
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", Matchers.equalTo(USERNAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.jwt", Matchers.equalTo(JWT)));
    }
}
