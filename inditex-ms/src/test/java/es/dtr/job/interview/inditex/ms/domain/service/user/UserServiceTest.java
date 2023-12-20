package es.dtr.job.interview.inditex.ms.domain.service.user;

import es.dtr.job.interview.commons.TestData;
import es.dtr.job.interview.commons.error.GenericException;
import es.dtr.job.interview.commons.hexagonal.domain.service.crud.CrudService;
import es.dtr.job.interview.commons.test.service.CrudServiceTest;
import es.dtr.job.interview.inditex.ms.SecurityTestUtils;
import es.dtr.job.interview.inditex.ms.domain.entity.UserEntity;
import es.dtr.job.interview.inditex.ms.domain.repository.UserDomainRepository;
import lombok.Getter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@ExtendWith(MockitoExtension.class)
class UserServiceTest implements CrudServiceTest<UserEntity, UUID> {

    // Class to test
    @InjectMocks
    private UserService crudService;

    // Mocks
    @Mock
    private UserDomainRepository crudRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @Override
    public void test_create_whenOk_thenReturnId() {
        // Mocks
        Mockito.when(crudRepository.create(getElementEntity())).thenReturn(getElementEntity());

        // Authentication Mock
        SecurityTestUtils.authenticateAsAdmin();

        // Test execution
        final UserEntity element = crudService.create(getElementEntity());

        // Response validation
        Assertions.assertTrue(Objects.nonNull(element));
        Assertions.assertEquals(getElementEntity(), element);
    }

    @Test
    void test_updatePassword_whenNoExistsUser_thenThrowException() {
        // Mocks
        Mockito.doThrow(CrudService.NOT_FOUND).when(getCrudRepository()).get(getRequestId());

        // Test execution
        final GenericException thrown = Assertions.assertThrows(
                CrudService.NOT_FOUND.getClass(),
                () -> crudService.updatePassword(getRequestId(), PasswordUpdateDto.builder().oldPassword(TestData.TEST_STRING).newPassword("newPassword").build()));

        // Response validation
        Assertions.assertTrue(Objects.nonNull(thrown));
        Assertions.assertEquals(CrudService.NOT_FOUND.getError().getHttpCode(), thrown.getError().getHttpCode());
        Assertions.assertEquals(CrudService.NOT_FOUND.getError().getMessage(), thrown.getError().getMessage());
    }

    @Test
    void test_updatePassword_whenExistUser_thenOk() {
        // Mocks
        Mockito.when(getCrudRepository().get(getRequestId())).thenReturn(getElementEntity());
        Mockito.when(passwordEncoder.matches(Mockito.anyString(), Mockito.anyString())).thenReturn(true);

        // Test execution
        crudService.updatePassword(getRequestId(), PasswordUpdateDto.builder().oldPassword(TestData.TEST_STRING).newPassword("newPassword").build());
    }

    @Override
    public List<UserEntity> getElementsEntity() {
        return List.of(
                UserEntity.builder().
                        id(UUID.fromString("29969c37-19b7-4c9b-907c-46c7eda2a1d4")).
                        name(TestData.TEST_STRING).
                        createdOn(TestData.NOW).
                        lastUpdatedOn(TestData.NOW)
                        .build(),
                UserEntity.builder().
                        id(UUID.fromString("53015578-092d-4229-8fe4-101cddaec42f"))
                        .name(TestData.TEST_STRING)
                        .createdOn(TestData.NOW)
                        .lastUpdatedOn(TestData.NOW)
                        .build()
        );
    }

    @Override
    public UUID getRequestId() {
        return UUID.fromString("29969c37-19b7-4c9b-907c-46c7eda2a1d4");
    }

    @Override
    public UserEntity getElementEntity() {
        return UserEntity.builder()
                .id(getRequestId())
                .username(TestData.TEST_STRING)
                .password(TestData.TEST_STRING)
                .name(TestData.TEST_STRING)
                .createdOn(TestData.NOW)
                .lastUpdatedOn(TestData.NOW)
                .build();
    }

}
