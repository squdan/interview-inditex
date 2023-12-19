package es.dtr.job.interview.inditex.ms.infrastructure.database.user;

import es.dtr.job.interview.commons.hexagonal.infrastructure.database.CrudPortRepository;
import es.dtr.job.interview.inditex.ms.domain.entity.UserEntity;
import es.dtr.job.interview.inditex.ms.domain.repository.UserDomainRepository;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

@Getter
@Component
@RequiredArgsConstructor
public class UserAdapterRepository implements UserDomainRepository, CrudPortRepository<UserEntity, UserInfraEntity, UUID> {

    // Dependencies
    private final UserInfraRepository crudInfraRepository;
    private final UserRepositoryMapper repositoryMapper;

    @Override
    public UserEntity findByUsername(@NotBlank final String username) {
        final UserInfraEntity infraEntity = crudInfraRepository.findByUsername(username);

        if (Objects.isNull(infraEntity)) {
            throw NOT_FOUND;
        }

        return repositoryMapper.entityInfraToEntityDomain(infraEntity);
    }
}
