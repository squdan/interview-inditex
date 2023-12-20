package es.dtr.job.interview.inditex.ms.application.rest.user;

import es.dtr.job.interview.inditex.ms.application.rest.user.dto.UserDto;
import es.dtr.job.interview.inditex.ms.application.rest.user.dto.UserRegisterRequest;
import es.dtr.job.interview.inditex.ms.domain.entity.UserEntity;
import es.dtr.job.interview.inditex.ms.domain.service.user.PasswordUpdateDto;
import es.dtr.job.interview.inditex.ms.domain.service.user.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Getter
@RestController
@RequiredArgsConstructor
public class UserController implements UserControllerInterface {

    // Dependencies
    private final Class<UserController> crudController = UserController.class;
    private final UserService crudService;
    private final UserControllerMapper mapper;

    @Override
    public ResponseEntity<EntityModel<UserDto>> create(final UserRegisterRequest createRequest) {
        final UserEntity domainCreateRequest = mapper.registerRequestToEntity(createRequest);
        final UserEntity createdEntity = crudService.create(domainCreateRequest);
        final UserDto result = mapper.domainEntityToDto(createdEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(EntityModel.of(result, getHateoas(result.getId())));
    }

    @Override
    public ResponseEntity<EntityModel<UserDto>> updatePassword(final UUID id, final PasswordUpdateDto passwordChangeRequest) {
        final UserEntity userUpdated = crudService.updatePassword(id, passwordChangeRequest);
        final UserDto result = mapper.domainEntityToDto(userUpdated);
        return ResponseEntity.ok(EntityModel.of(result, getHateoas(id)));
    }

}
