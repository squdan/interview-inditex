package es.dtr.job.interview.inditex.ms.application.rest.user;

import es.dtr.job.interview.inditex.ms.application.rest.user.dto.UserRegisterRequest;
import es.dtr.job.interview.inditex.ms.domain.entity.UserEntity;
import es.dtr.job.interview.inditex.ms.domain.service.user.PasswordUpdateDto;
import es.dtr.job.interview.inditex.ms.domain.service.user.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Getter
@RestController
@RequiredArgsConstructor
public class UserController implements UserControllerInterface {

    // Dependencies
    private final UserService crudService;
    private final UserControllerMapper mapper;

    @Override
    public ResponseEntity<UUID> create(final UserRegisterRequest createRequest) {
        final UserEntity domainCreateRequest = mapper.registerRequestToEntity(createRequest);
        final UUID createdId = crudService.create(domainCreateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdId);
    }

    @Override
    public ResponseEntity<Void> updatePassword(final UUID id, final PasswordUpdateDto passwordChangeRequest) {
        crudService.updatePassword(id, passwordChangeRequest);
        return ResponseEntity.noContent().build();
    }
}
