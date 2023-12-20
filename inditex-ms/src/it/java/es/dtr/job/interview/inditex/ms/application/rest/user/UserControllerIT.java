package es.dtr.job.interview.inditex.ms.application.rest.user;

import es.dtr.job.interview.commons.TestData;
import es.dtr.job.interview.commons.hexagonal.application.rest.crud.CrudCreateController;
import es.dtr.job.interview.commons.hexagonal.domain.entity.type.Roles;
import es.dtr.job.interview.commons.hexagonal.domain.service.crud.CrudService;
import es.dtr.job.interview.commons.hexagonal.domain.service.querydsl.QueryDslFilter;
import es.dtr.job.interview.commons.hexagonal.domain.service.querydsl.QueryDslOperators;
import es.dtr.job.interview.commons.it.controller.CrudDeleteControllerIT;
import es.dtr.job.interview.commons.it.controller.CrudFindControllerIT;
import es.dtr.job.interview.commons.it.controller.CrudGetControllerIT;
import es.dtr.job.interview.commons.it.controller.CrudUpdateControllerIT;
import es.dtr.job.interview.inditex.ms.TestDataIT;
import es.dtr.job.interview.inditex.ms.application.rest.ControllerBaseIT;
import es.dtr.job.interview.inditex.ms.application.rest.user.dto.UserDto;
import es.dtr.job.interview.inditex.ms.application.rest.user.dto.UserRegisterRequest;
import es.dtr.job.interview.inditex.ms.domain.entity.UserEntity;
import es.dtr.job.interview.inditex.ms.domain.service.user.PasswordUpdateDto;
import es.dtr.job.interview.inditex.ms.infrastructure.configuration.security.AuthenticationResponse;
import io.micrometer.common.util.StringUtils;
import lombok.Getter;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.UUID;

@Getter
@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class UserControllerIT extends ControllerBaseIT implements
        CrudGetControllerIT<UserDto, UserEntity, UUID>,
        CrudFindControllerIT<UserDto, UserEntity, UUID>,
        CrudUpdateControllerIT<UserDto, UserEntity, UUID>,
        CrudDeleteControllerIT<UserDto, UserEntity, UUID> {

    // Configuration
    private final String basePath = UserControllerInterface.BASE_PATH;

    @Test
    void test_create_whenOk_thenReturnId() throws Exception {
        // Login
        final AuthenticationResponse authResponse = loginWithAdmin();
        Assertions.assertNotNull(authResponse, "Error en la autenticación.");
        Assertions.assertTrue(StringUtils.isNotBlank(authResponse.getJwt()), "Error en la autenticación, no se obtuvo JWT.");

        // Test data
        final UserRegisterRequest registerRequest = UserRegisterRequest.builder()
                .role(Roles.ADMIN)
                .username(TestData.TEST_STRING)
                .password(TestData.TEST_STRING)
                .name(TestData.TEST_STRING)
                .lastName(TestData.TEST_STRING)
                .email(TestData.TEST_EMAIL)
                .phone(TestData.TEST_PHONE)
                .build();

        // Test execution
        final ResultActions restResponse = getMockMvc().perform(
                MockMvcRequestBuilders
                        .post(getBasePath() + CrudCreateController.ENDPOINT_CREATE)
                        .header(HttpHeaders.AUTHORIZATION, authResponse.getJwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getObjectMapper().writeValueAsString(registerRequest))
        );

        // Response validation
        restResponse
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }


    @Test
    void test_updatePassword_whenNoExists_then404() throws Exception {
        // Test data
        final PasswordUpdateDto passwordUpdateRequest = PasswordUpdateDto.builder().oldPassword(TestDataIT.PASSWORD_OK).newPassword(TestData.TEST_STRING).build();

        // Test execution
        final ResultActions restResponse = mockMvc.perform(
                MockMvcRequestBuilders
                        .patch(getBasePath() + UserControllerInterface.ENDPOINT_UPDATE_PASSWORD, getRequestIdNotExists())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passwordUpdateRequest))
        );

        // Response validation
        restResponse
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.httpCode", Matchers.equalTo(HttpStatus.NOT_FOUND.name())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.equalTo(CrudService.NOT_FOUND.getError().getMessage())));
    }

    @Test
    void test_updatePassword_whenExists_thenReturn204() throws Exception {
        // Test data
        final PasswordUpdateDto passwordUpdateRequest = PasswordUpdateDto.builder().oldPassword(TestDataIT.PASSWORD_OK).newPassword(TestData.TEST_STRING).build();

        // Test execution
        final ResultActions restResponse = mockMvc.perform(
                MockMvcRequestBuilders
                        .patch(getBasePath() + UserControllerInterface.ENDPOINT_UPDATE_PASSWORD, getRequestId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passwordUpdateRequest))
        );

        // Response validation
        restResponse
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.notNullValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.equalTo(getRequestId().toString())));
    }

    @Override
    public UUID getRequestId() {
        return UUID.fromString("24930b8e-ff6e-476a-8758-4bd3ff5ebd6b");
    }

    @Override
    public UUID getRequestIdNotExists() {
        return UUID.fromString("9b4e1221-29d0-458a-9c13-7032708d609d");
    }

    @Override
    public UserDto getElementUpdate() {
        return UserDto.builder()
                .name(TestData.TEST_STRING)
                .build();
    }

    @Override
    public List<ResultMatcher> getFindByNoFiltersTestMatchers() {
        return List.of(
                MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)),
                MockMvcResultMatchers.jsonPath("$[0].id", Matchers.equalTo("24930b8e-ff6e-476a-8758-4bd3ff5ebd6b")),
                MockMvcResultMatchers.jsonPath("$[0].username", Matchers.equalTo("admin")),
                MockMvcResultMatchers.jsonPath("$[1].id", Matchers.equalTo("26ad7565-ba11-4914-bf91-84557b8b8764")),
                MockMvcResultMatchers.jsonPath("$[1].username", Matchers.equalTo("user"))
        );
    }

    @Override
    public List<ResultMatcher> getByIdTestMatchers() {
        return List.of(
                MockMvcResultMatchers.jsonPath("$.id", Matchers.equalTo("24930b8e-ff6e-476a-8758-4bd3ff5ebd6b")),
                MockMvcResultMatchers.jsonPath("$.username", Matchers.equalTo("admin"))
        );
    }

    @Override
    public String getFindByCorrectFiltersButNoResults() {
        return QueryDslFilter.builder()
                .key("username")
                .operator(QueryDslOperators.EQUALS)
                .value("NotExists")
                .build().toString();
    }

    @Override
    public String getFindByCorrectFiltersWithResults() {
        return QueryDslFilter.builder()
                .key("username")
                .operator(QueryDslOperators.EQUALS)
                .value("admin")
                .build().toString();
    }

    @Override
    public List<ResultMatcher> getFindByTestMatchers() {
        return List.of(
                MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)),
                MockMvcResultMatchers.jsonPath("$[0].id", Matchers.equalTo("24930b8e-ff6e-476a-8758-4bd3ff5ebd6b")),
                MockMvcResultMatchers.jsonPath("$[0].username", Matchers.equalTo("admin"))
        );
    }

}
