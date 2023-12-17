package es.dtr.job.interview.inditex.ms.adapter.in.user;

import es.dtr.job.interview.inditex.ms.domain.service.user.UserService;
import es.dtr.job.interview.inditex.ms.domain.service.user.dto.PasswordUpdateDto;
import es.dtr.job.interview.inditex.ms.domain.service.user.dto.UserRegisterRequest;
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

    @Override
    public ResponseEntity<UUID> create(final UserRegisterRequest createRequest) {
        final UUID createdId = crudService.create(createRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdId);
    }

    @Override
    public ResponseEntity<Void> updatePassword(final UUID id, final PasswordUpdateDto passwordChangeRequest) {
        crudService.updatePassword(id, passwordChangeRequest);
        return ResponseEntity.noContent().build();
    }
}
