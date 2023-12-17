package es.dtr.job.interview.inditex.ms.domain.service.user;

import es.dtr.job.interview.commons.api.querydsl.QueryDslFilter;
import es.dtr.job.interview.commons.api.querydsl.QueryDslUtils;
import es.dtr.job.interview.commons.data.Roles;
import es.dtr.job.interview.commons.service.ServiceException;
import es.dtr.job.interview.commons.service.crud.CrudGetService;
import es.dtr.job.interview.commons.service.crud.CrudService;
import es.dtr.job.interview.inditex.ms.adapter.out.database.hibernate.UserRepository;
import es.dtr.job.interview.inditex.ms.configuration.security.SecurityUtils;
import es.dtr.job.interview.inditex.ms.domain.entity.UserEntity;
import es.dtr.job.interview.inditex.ms.domain.service.user.dto.PasswordUpdateDto;
import es.dtr.job.interview.inditex.ms.domain.service.user.dto.UserDto;
import es.dtr.job.interview.inditex.ms.domain.service.user.dto.UserRegisterRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Getter
@Service
@RequiredArgsConstructor
public class UserService implements CrudService<UserDto, UserEntity, UUID> {

    // Dependencias
    private final UserMapper mapper;
    private final UserRepository repository;
    private final QueryDslUtils queryDslUtils;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> findBy(final List<String> filters) {
        return findBy(filters, null);
    }

    @Override
    public List<UserDto> findBy(final List<String> filters, final Pageable pageable) {
        List<UserDto> result = new ArrayList<>();

        // Prepare received queryDslFilters
        final List<QueryDslFilter> queryDslFilters = queryDslUtils.prepareFilters(filters);

        // Execute search
        final List<UserEntity> mayElements = repository.findAll(UserEntity.class, queryDslFilters, pageable);

        if (CollectionUtils.isNotEmpty(mayElements)) {
            result = mapper.entityToDto(mayElements);
        }

        return result;
    }

    @Override
    public UUID create(final UserDto createRequest) {
        // Create user must be customized, we cant use default method
        throw CrudService.NOT_IMPLEMENTED;
    }

    public UUID create(@NotNull @Valid final UserRegisterRequest createRequest) {
        final UserEntity elementToCreate = mapper.createDtoToEntity(createRequest);

        // If logged user is not an admin, no admins users are allowed to be created
        if (!SecurityUtils.getLoggedUserRole().equals(Roles.ADMIN)) {
            elementToCreate.setRole(Roles.USER);
        }

        return getRepository().save(elementToCreate).getId();
    }

    @Override
    public void update(final UUID id, final UserDto updateRequest) {
        final Optional<UserEntity> existingUser = repository.findById(id);

        if (existingUser.isEmpty()) {
            throw NOT_FOUND;
        } else {
            final UserEntity elementChanges = getMapper().dtoToEntity(updateRequest);
            existingUser.get().merge(elementChanges);
            repository.save(existingUser.get());
        }
    }

    public void updatePassword(@NotNull @Valid final UUID id,
                               @NotNull @Valid final PasswordUpdateDto passwordChangeRequest) {
        final Optional<UserEntity> mayUser = repository.findById(id);

        if (mayUser.isEmpty()) {
            throw CrudGetService.NOT_FOUND;
        } else {
            // Check old password received is correct
            if (passwordEncoder.matches(passwordChangeRequest.getOldPassword(), mayUser.get().getPassword())) {
                final UserEntity entityChanges = mayUser.get();
                entityChanges.setPassword(passwordChangeRequest.getNewPassword());
                repository.save(entityChanges);
            } else {
                throw new ServiceException("Wrong password.", HttpStatus.BAD_REQUEST);
            }
        }
    }

}
