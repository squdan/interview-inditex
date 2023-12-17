package es.dtr.job.interview.inditex.ms.adapter.in.authentication;

import es.dtr.job.interview.inditex.ms.TestDataIT;
import es.dtr.job.interview.inditex.ms.adapter.in.ControllerBaseIT;
import es.dtr.job.interview.inditex.ms.adapter.in.authentication.dto.AuthenticationRequest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
class LoginControllerIT extends ControllerBaseIT {

    @Test
    void test_login_whenNoExists_then404() throws Exception {
        // Test data
        final AuthenticationRequest authenticationRequest = AuthenticationRequest.builder().username(TestDataIT.USERNAME_NOT_EXISTS).password(TestDataIT.PASSWORD_OK).build();

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
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpCode", Matchers.equalTo(HttpStatus.NOT_FOUND.name())));
    }

    @Test
    void test_login_whenExistsButWrongPassword_thenReturn401() throws Exception {
        // Test data
        final AuthenticationRequest authenticationRequest = AuthenticationRequest.builder().username(TestDataIT.USERNAME_ADMIN).password(TestDataIT.PASSWORD_KO).build();

        // Test execution
        final ResultActions restResponse = mockMvc.perform(
                MockMvcRequestBuilders
                        .post(LoginControllerInterface.BASE_PATH + LoginControllerInterface.ENDPOINT_LOGIN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authenticationRequest))
        );

        // Response validation
        restResponse
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    void test_login_whenExists_thenReturnJwt() throws Exception {
        // Test data
        final AuthenticationRequest authenticationRequest = AuthenticationRequest.builder().username(TestDataIT.USERNAME_ADMIN).password(TestDataIT.PASSWORD_OK).build();

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
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", Matchers.equalTo(TestDataIT.USERNAME_ADMIN)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.jwt", Matchers.notNullValue()));
    }
}
