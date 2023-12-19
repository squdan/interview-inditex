package es.dtr.job.interview.inditex.ms.application.rest.user;

import es.dtr.job.interview.commons.hexagonal.application.rest.crud.CrudControllerMapper;
import es.dtr.job.interview.commons.hexagonal.domain.entity.type.EntityTypesMapper;
import es.dtr.job.interview.inditex.ms.application.rest.user.dto.UserDto;
import es.dtr.job.interview.inditex.ms.application.rest.user.dto.UserRegisterRequest;
import es.dtr.job.interview.inditex.ms.domain.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {EntityTypesMapper.class})
public interface UserControllerMapper extends CrudControllerMapper<UserDto, UserEntity> {

    UserEntity registerRequestToEntity(UserRegisterRequest source);
}
