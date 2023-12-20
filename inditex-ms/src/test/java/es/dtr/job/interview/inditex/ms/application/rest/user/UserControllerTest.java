package es.dtr.job.interview.inditex.ms.application.rest.user;

import es.dtr.job.interview.commons.TestData;
import es.dtr.job.interview.commons.hexagonal.application.rest.crud.CrudCreateController;
import es.dtr.job.interview.commons.hexagonal.domain.entity.type.Roles;
import es.dtr.job.interview.commons.hexagonal.domain.service.crud.CrudService;
import es.dtr.job.interview.commons.test.controller.CrudDeleteControllerTest;
import es.dtr.job.interview.commons.test.controller.CrudFindControllerTest;
import es.dtr.job.interview.commons.test.controller.CrudGetControllerTest;
import es.dtr.job.interview.commons.test.controller.CrudUpdateControllerTest;
import es.dtr.job.interview.inditex.ms.application.rest.ControllerBaseTest;
import es.dtr.job.interview.inditex.ms.application.rest.user.dto.UserDto;
import es.dtr.job.interview.inditex.ms.application.rest.user.dto.UserRegisterRequest;
import es.dtr.job.interview.inditex.ms.domain.entity.UserEntity;
import es.dtr.job.interview.inditex.ms.domain.service.user.PasswordUpdateDto;
import es.dtr.job.interview.inditex.ms.domain.service.user.UserService;
import lombok.Getter;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.UUID;

@Getter
@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest extends ControllerBaseTest implements
        CrudGetControllerTest<UserDto, UserEntity, UUID>,
        CrudFindControllerTest<UserDto, UserEntity, UUID>,
        CrudUpdateControllerTest<UserDto, UserEntity, UUID>,
        CrudDeleteControllerTest<UserDto, UserEntity, UUID> {

    // Configuration
    private final String basePath = UserControllerInterface.BASE_PATH;

    // Mocks
    @MockBean
    private UserService crudService;

    @MockBean
    private UserControllerMapper crudMapper;

    @Test
    void test_createUser_whenOk_thenReturn201() throws Exception {
        // Test data
        final UserRegisterRequest registerRequest = UserRegisterRequest.builder()
                .role(Roles.USER)
                .name(TestData.TEST_STRING)
                .lastName(TestData.TEST_STRING)
                .username(TestData.TEST_STRING)
                .password(TestData.TEST_STRING)
                .email(TestData.TEST_EMAIL)
                .phone(TestData.TEST_PHONE)
                .build();

        // Mocks
        Mockito.when(crudService.create(getElementEntity())).thenReturn(getElementEntity());
        Mockito.when(getCrudMapper().registerRequestToEntity(registerRequest)).thenReturn(getElementEntity());
        Mockito.when(getCrudMapper().domainEntityToDto(getElementEntity())).thenReturn(getElementDto());

        // Test execution
        final ResultActions restResponse = mockMvc.perform(
                MockMvcRequestBuilders
                        .post(getBasePath() + CrudCreateController.ENDPOINT_CREATE)
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
        final PasswordUpdateDto passwordUpdateRequest = PasswordUpdateDto.builder().oldPassword(TestData.TEST_STRING).newPassword("newPassword").build();

        // Mocks
        Mockito.doThrow(CrudService.NOT_FOUND)
                .when(crudService).updatePassword(getRequestId(), passwordUpdateRequest);

        // Test execution
        final ResultActions restResponse = mockMvc.perform(
                MockMvcRequestBuilders
                        .patch(getBasePath() + UserControllerInterface.ENDPOINT_UPDATE_PASSWORD, getRequestId())
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
        final PasswordUpdateDto passwordUpdateRequest = PasswordUpdateDto.builder().oldPassword(TestData.TEST_STRING).newPassword("newPassword").build();

        // Mocks
        Mockito.when(getCrudService().updatePassword(getRequestId(), passwordUpdateRequest)).thenReturn(getElementEntity());
        Mockito.when(getCrudMapper().domainEntityToDto(getElementEntity())).thenReturn(getElementDto());

        // Test execution
        final ResultActions restResponse = mockMvc.perform(
                MockMvcRequestBuilders
                        .patch(getBasePath() + UserControllerInterface.ENDPOINT_UPDATE_PASSWORD, getRequestId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passwordUpdateRequest))
        );

        // Response validation
        restResponse
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Override
    public UUID getRequestId() {
        return UUID.fromString("e04ccd5b-6958-472d-9669-2e3f85b434af");
    }

    @Override
    public UserDto getElementDto() {
        return UserDto.builder()
                .id(getRequestId())
                .name(TestData.TEST_STRING)
                .createdOn(TestData.NOW)
                .lastUpdatedOn(TestData.NOW)
                .build();
    }

    @Override
    public UserEntity getElementEntity() {
        return UserEntity.builder()
                .id(getRequestId())
                .name(TestData.TEST_STRING)
                .createdOn(TestData.NOW)
                .lastUpdatedOn(TestData.NOW)
                .build();
    }

    @Override
    public List<UserDto> getElementsDto() {
        return List.of(
                UserDto.builder()
                        .id(getRequestId())
                        .name(TestData.TEST_STRING)
                        .createdOn(TestData.NOW)
                        .lastUpdatedOn(TestData.NOW)
                        .build(),
                UserDto.builder()
                        .id(UUID.fromString("53015578-092d-4229-8fe4-101cddaec42f"))
                        .name(TestData.TEST_STRING)
                        .createdOn(TestData.NOW)
                        .lastUpdatedOn(TestData.NOW)
                        .build()
        );
    }

    @Override
    public List<UserEntity> getElementsEntity() {
        return List.of(
                UserEntity.builder()
                        .id(getRequestId())
                        .name(TestData.TEST_STRING)
                        .createdOn(TestData.NOW)
                        .lastUpdatedOn(TestData.NOW)
                        .build(),
                UserEntity.builder()
                        .id(UUID.fromString("53015578-092d-4229-8fe4-101cddaec42f"))
                        .name(TestData.TEST_STRING)
                        .createdOn(TestData.NOW)
                        .lastUpdatedOn(TestData.NOW)
                        .build()
        );
    }

    @Override
    public List<ResultMatcher> getElementsTestMatchers() {
        return List.of(
                MockMvcResultMatchers.jsonPath("$[0].id", Matchers.equalTo(getElementsDto().get(0).getId().toString())),
                MockMvcResultMatchers.jsonPath("$[0].name", Matchers.equalTo(getElementsDto().get(0).getName())),
                MockMvcResultMatchers.jsonPath("$[1].id", Matchers.equalTo(getElementsDto().get(1).getId().toString())),
                MockMvcResultMatchers.jsonPath("$[1].name", Matchers.equalTo(getElementsDto().get(1).getName()))
        );
    }

    @Override
    public List<ResultMatcher> getElementTestMatchers() {
        return List.of(
                MockMvcResultMatchers.jsonPath("$.id", Matchers.equalTo(this.getElementDto().getId().toString())),
                MockMvcResultMatchers.jsonPath("$.name", Matchers.equalTo(this.getElementDto().getName()))
        );
    }

}
