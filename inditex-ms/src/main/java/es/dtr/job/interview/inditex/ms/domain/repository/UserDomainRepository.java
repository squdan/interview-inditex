package es.dtr.job.interview.inditex.ms.domain.repository;

import es.dtr.job.interview.commons.hexagonal.domain.repository.CrudDomainRepository;
import es.dtr.job.interview.inditex.ms.domain.entity.UserEntity;

import java.util.UUID;

public interface UserDomainRepository extends CrudDomainRepository<UserEntity, UUID> {

    UserEntity findByUsername(final String username);

}
