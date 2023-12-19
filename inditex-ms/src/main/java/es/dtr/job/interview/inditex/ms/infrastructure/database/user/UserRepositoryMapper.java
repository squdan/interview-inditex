package es.dtr.job.interview.inditex.ms.infrastructure.database.user;

import es.dtr.job.interview.commons.hexagonal.domain.entity.type.EntityTypesMapper;
import es.dtr.job.interview.commons.hexagonal.infrastructure.database.CrudRepositoryMapper;
import es.dtr.job.interview.inditex.ms.application.rest.price.PriceControllerMapper;
import es.dtr.job.interview.inditex.ms.domain.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {PriceControllerMapper.class, EntityTypesMapper.class})
public interface UserRepositoryMapper extends CrudRepositoryMapper<UserEntity, UserInfraEntity> {

}
