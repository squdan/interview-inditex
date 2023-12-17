package es.dtr.job.interview.inditex.ms.domain.service.user;

import es.dtr.job.interview.commons.TestData;
import es.dtr.job.interview.commons.api.querydsl.QueryDslUtils;
import es.dtr.job.interview.commons.data.CrudRepository;
import es.dtr.job.interview.commons.error.GenericException;
import es.dtr.job.interview.commons.service.crud.CrudMapper;
import es.dtr.job.interview.commons.service.crud.CrudService;
import es.dtr.job.interview.commons.test.service.CrudServiceTest;
import es.dtr.job.interview.inditex.ms.SecurityTestUtils;
import es.dtr.job.interview.inditex.ms.adapter.out.database.hibernate.UserRepository;
import es.dtr.job.interview.inditex.ms.domain.entity.UserEntity;
import es.dtr.job.interview.inditex.ms.domain.service.user.dto.PasswordUpdateDto;
import es.dtr.job.interview.inditex.ms.domain.service.user.dto.UserDto;
import es.dtr.job.interview.inditex.ms.domain.service.user.dto.UserRegisterRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest implements CrudServiceTest<UserDto, UserEntity, UUID> {

    // Class to test
    @InjectMocks
    private UserService service;

    // Mocks
    @Mock
    private UserRepository repository;

    @Mock
    private UserMapper mapper;

    @Mock
    private QueryDslUtils queryDslUtils;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void test_findBy_whenNoData_thenEmptyResponse() {
        // Mocks
        Mockito.when(queryDslUtils.prepareFilters(Mockito.anyList())).thenReturn(new ArrayList<>());
        Mockito.when(getCrudRepository().findAll(Mockito.any(), Mockito.anyList(), Mockito.any())).thenReturn(new ArrayList<>());

        // Test execution
        final List<UserDto> elements = getCrudService().findBy(new ArrayList<>());

        // Response validation
        Assertions.assertTrue(CollectionUtils.isEmpty(elements));
    }

    @Test
    void test_findBy_whenData_thenReturnElements() {
        // Mocks
        Mockito.when(queryDslUtils.prepareFilters(Mockito.anyList())).thenReturn(new ArrayList<>());
        Mockito.when(getCrudRepository().findAll(Mockito.any(), Mockito.anyList(), Mockito.any())).thenReturn(getElementsEntity());
        Mockito.when(getCrudMapper().entityToDto(getElementsEntity())).thenReturn(getElementsDto());

        // Test execution
        final List<UserDto> elements = getCrudService().findBy(new ArrayList<>());

        // Response validation
        Assertions.assertTrue(CollectionUtils.isNotEmpty(elements));
        Assertions.assertArrayEquals(getElementsDto().toArray(), elements.toArray());
    }

    @Test
    @Override
    public void test_create_whenOk_thenReturnId() {
        // Mocks
        Mockito.when(mapper.createDtoToEntity(Mockito.any(UserRegisterRequest.class))).thenReturn(getElementEntity());
        Mockito.when(getCrudRepository().save(getElementEntity())).thenReturn(getElementEntity());

        // Authentication Mock
        SecurityTestUtils.authenticateAsAdmin();

        // Test execution
        final UUID elementId = service.create(UserRegisterRequest.builder().build());

        // Response validation
        Assertions.assertTrue(Objects.nonNull(elementId));
        Assertions.assertEquals(getRequestId(), elementId);
    }

    @Test
    @Override
    public void test_update_whenElementNotExists_thenThrowException() {
        // Mocks
        Mockito.when(getCrudRepository().findById(getRequestId())).thenReturn(Optional.empty());

        // Test execution
        final GenericException thrown = Assertions.assertThrows(CrudService.NOT_FOUND.getClass(), () -> getCrudService().update(getRequestId(), getElementDto()));

        // Response validation
        Assertions.assertTrue(Objects.nonNull(thrown));
        Assertions.assertEquals(CrudService.NOT_FOUND.getError().getHttpCode(), thrown.getError().getHttpCode());
        Assertions.assertEquals(CrudService.NOT_FOUND.getError().getMessage(), thrown.getError().getMessage());
    }

    @Test
    @Override
    public void test_update_whenElementExists_thenOk() {
        // Mocks
        Mockito.when(getCrudRepository().findById(getRequestId())).thenReturn(Optional.of(getElementEntity()));
        Mockito.when(getCrudMapper().dtoToEntity(getElementDto())).thenReturn(getElementEntity());
        Mockito.when(getCrudRepository().save(getElementEntity())).thenReturn(getElementEntity());

        // Test execution
        getCrudService().update(getRequestId(), getElementDto());
    }

    @Test
    void test_updatePassword_whenNoExistsUser_thenThrowException() {
        // Mocks
        Mockito.when(getCrudRepository().findById(getRequestId())).thenReturn(Optional.empty());

        // Test execution
        final GenericException thrown = Assertions.assertThrows(
                CrudService.NOT_FOUND.getClass(),
                () -> service.updatePassword(getRequestId(), PasswordUpdateDto.builder().oldPassword(TestData.TEST_STRING).newPassword("newPassword").build()));

        // Response validation
        Assertions.assertTrue(Objects.nonNull(thrown));
        Assertions.assertEquals(CrudService.NOT_FOUND.getError().getHttpCode(), thrown.getError().getHttpCode());
        Assertions.assertEquals(CrudService.NOT_FOUND.getError().getMessage(), thrown.getError().getMessage());
    }

    @Test
    void test_updatePassword_whenExistUser_thenOk() {
        // Mocks
        Mockito.when(getCrudRepository().findById(getRequestId())).thenReturn(Optional.of(getElementEntity()));
        Mockito.when(passwordEncoder.matches(Mockito.anyString(), Mockito.anyString())).thenReturn(true);

        // Test execution
        service.updatePassword(getRequestId(), PasswordUpdateDto.builder().oldPassword(TestData.TEST_STRING).newPassword("newPassword").build());
    }

    @Override
    public CrudService<UserDto, UserEntity, UUID> getCrudService() {
        return this.service;
    }

    @Override
    public CrudRepository<UserEntity, UUID> getCrudRepository() {
        return this.repository;
    }

    @Override
    public CrudMapper<UserEntity, UserDto> getCrudMapper() {
        return this.mapper;
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
    public List<UserDto> getElementsDto() {
        return List.of(
                UserDto.builder().
                        id(UUID.fromString("29969c37-19b7-4c9b-907c-46c7eda2a1d4")).
                        name(TestData.TEST_STRING).
                        createdOn(TestData.NOW).
                        lastUpdatedOn(TestData.NOW)
                        .build(),
                UserDto.builder().
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

    @Override
    public UserDto getElementDto() {
        final UserEntity entity = getElementEntity();
        return UserDto.builder()
                .id(entity.getId())
                .username(TestData.TEST_STRING)
                .name(entity.getName())
                .createdOn(entity.getCreatedOn())
                .lastUpdatedOn(entity.getLastUpdatedOn())
                .build();
    }

}
