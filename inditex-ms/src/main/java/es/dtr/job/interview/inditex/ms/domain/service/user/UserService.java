package es.dtr.job.interview.inditex.ms.domain.service.user;

import es.dtr.job.interview.commons.hexagonal.domain.entity.type.Roles;
import es.dtr.job.interview.commons.hexagonal.domain.service.ServiceException;
import es.dtr.job.interview.commons.hexagonal.domain.service.crud.CrudService;
import es.dtr.job.interview.inditex.ms.domain.entity.UserEntity;
import es.dtr.job.interview.inditex.ms.domain.repository.UserDomainRepository;
import es.dtr.job.interview.inditex.ms.infrastructure.configuration.security.SecurityUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public class UserService implements CrudService<UserEntity, UUID> {

    // Dependencies
    private final UserDomainRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserEntity create(final UserEntity createRequest) {
        // If logged user is not an admin, no admins users are allowed to be created
        if (!SecurityUtils.getLoggedUserRole().equals(Roles.ADMIN)) {
            createRequest.setRole(Roles.USER);
        }

        return getRepository().create(createRequest);
    }

    public UserEntity updatePassword(final UUID id, final PasswordUpdateDto passwordChangeRequest) {
        final UserEntity user = repository.get(id);

        // Check old password received is correct
        if (passwordEncoder.matches(passwordChangeRequest.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordChangeRequest.getNewPassword());
            repository.update(id, user);
            return user;
        } else {
            throw new ServiceException("Wrong password.", HttpStatus.BAD_REQUEST);
        }
    }

}
